package `in`.porter.mcp.server.entities.requests

import kotlinx.serialization.Serializable

@Serializable
data class LatLng(
  val lat: Double,
  val lng: Double
)
