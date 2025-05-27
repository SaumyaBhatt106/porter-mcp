package `in`.porter.mcp.server.entities

import kotlinx.serialization.Serializable

@Serializable
data class LatLon(
  val lat: Double,
  val lon: Double
)
