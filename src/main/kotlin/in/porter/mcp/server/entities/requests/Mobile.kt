package `in`.porter.mcp.server.entities.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mobile(
  @SerialName("country_code")
  val countryCode: String,
  val number: String
)
