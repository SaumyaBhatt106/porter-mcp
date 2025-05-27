package `in`.porter.mcp.server.entities.responses

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
  val type: String,
  val eta: String? = null,
  val fare: Fare,
  val capacity: Capacity,
  val size: VehicleSize
)
