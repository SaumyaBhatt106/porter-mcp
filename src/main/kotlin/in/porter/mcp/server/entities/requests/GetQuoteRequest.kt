package `in`.porter.mcp.server.entities.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetQuoteRequest(

  @SerialName("pickup_details")
  val pickupDetails: LatLng,

  @SerialName("drop_details")
  val dropDetails: LatLng,
  val customer: Customer
)
