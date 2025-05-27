package `in`.porter.mcp.server.entities.responses

import kotlinx.serialization.Serializable

@Serializable
data class VehicleSize(
  val length: Dimension,
  val breadth: Dimension,
  val height: Dimension
)
