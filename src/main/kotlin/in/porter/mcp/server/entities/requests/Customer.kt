package `in`.porter.mcp.server.entities.requests

import `in`.porter.mcp.server.entities.requests.Mobile
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
  val name: String,
  val mobile: Mobile
)
