package `in`.porter.mcp.server.entities.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetQuoteResponse(
  val vehicles: List<Vehicle>
)
