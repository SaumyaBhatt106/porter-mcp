package `in`.porter.mcp.server.app

import `in`.porter.mcp.server.entities.requests.Customer
import `in`.porter.mcp.server.entities.requests.GetQuoteRequest
import `in`.porter.mcp.server.entities.requests.LatLng
import `in`.porter.mcp.server.entities.requests.Mobile
import `in`.porter.mcp.server.usecases.getLatLon
import `in`.porter.mcp.server.usecases.getQuote

suspend fun main() {
  val client = createHttpClient()
  val latLonRequest = "Kormangala, Bangalore, India"
  client.getLatLon(latLonRequest).also { println(it) }

  val getQuoteRequest = GetQuoteRequest(
    pickupDetails = LatLng(12.9716, 77.5946), // Example coordinates for pickup
    dropDetails = LatLng(12.2958, 76.6394), // Example coordinates for drop
    customer = Customer(
      name = "John Doe",
      mobile = Mobile(countryCode = "+91", number = "9876543210")
    )
  )
  client.getQuote(
    pickupLat = getQuoteRequest.pickupDetails.lat,
    pickupLng = getQuoteRequest.pickupDetails.lng,
    dropLat = getQuoteRequest.dropDetails.lat,
    dropLng = getQuoteRequest.dropDetails.lng,
    customerName = getQuoteRequest.customer.name,
    countryCode = getQuoteRequest.customer.mobile.countryCode,
    phoneNumber = getQuoteRequest.customer.mobile.number
  ).also { println(it) }
}
