package `in`.porter.mcp.server.usecases

import `in`.porter.mcp.server.app.ApplicationConfigs
import `in`.porter.mcp.server.entities.requests.Customer
import `in`.porter.mcp.server.entities.requests.GetQuoteRequest
import `in`.porter.mcp.server.entities.requests.LatLng
import `in`.porter.mcp.server.entities.requests.Mobile
import `in`.porter.mcp.server.entities.responses.GetQuoteResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun HttpClient.getQuote(
  pickupLat: Double,
  pickupLng: Double,
  dropLat: Double,
  dropLng: Double,
  customerName: String,
  countryCode: String,
  phoneNumber: String
): List<String> {
  val url = "${ApplicationConfigs.Pfe.BASE_URL}/v1/get_quote"

  val requestBody = GetQuoteRequest(
    pickupDetails = LatLng(pickupLat, pickupLng),
    dropDetails = LatLng(dropLat, dropLng),
    customer = Customer(
      name = customerName,
      mobile = Mobile(countryCode = countryCode, number = phoneNumber)
    )
  )

  val response = this.request {
    this.contentType(ContentType.Application.Json)
    this.setBody(requestBody)

    this.url(url)
    this.method = HttpMethod.Post
    this.headers {
      append("X-API-KEY", ApplicationConfigs.Pfe.API_KEY)
    }
  }.body<GetQuoteResponse>()

  return response.vehicles.map { vehicle ->
    val fareInRupees = vehicle.fare.minorAmount / 100.0 // Convert minor amount to major currency
    """
            Vehicle: ${vehicle.type}
            Fare: ₹${String.format("%.2f", fareInRupees)} ${vehicle.fare.currency}
            Capacity: ${vehicle.capacity.value} ${vehicle.capacity.unit}
            Size: ${vehicle.size.length.value}${vehicle.size.length.unit} × ${vehicle.size.breadth.value}${vehicle.size.breadth.unit} × ${vehicle.size.height.value}${vehicle.size.height.unit}
            ${if (vehicle.eta != null) "ETA: ${vehicle.eta}" else "ETA: Not available"}
        """.trimIndent()
  }
}
