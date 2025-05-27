package `in`.porter.mcp.server.entities.responses

import kotlinx.serialization.Serializable

@Serializable
data class Dimension(
  val value: Double,
  val unit: String
)
