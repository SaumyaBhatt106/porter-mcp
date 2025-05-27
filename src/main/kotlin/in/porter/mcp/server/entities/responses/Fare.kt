package `in`.porter.mcp.server.entities.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fare(
  val currency: String,
  @SerialName("minor_amount")
  val minorAmount: Int
)
