package `in`.porter.mcp.server.service

import `in`.porter.mcp.server.usecases.getQuote
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.putJsonObject
import io.ktor.client.*

fun Server.addGetQuoteTool(httpClient: HttpClient) {
  this.addTool(
    name = "get_quote",
    description = """
            Get vehicle quotes from Porter service for transportation between two locations.
            Requires pickup and drop coordinates, plus customer details.
        """.trimIndent(),
    inputSchema = Tool.Input(
      properties = buildJsonObject {
        putJsonObject("pickup_lat") {
          put("type", JsonPrimitive("number"))
          put("description", JsonPrimitive("Latitude of pickup location"))
        }
        putJsonObject("pickup_lng") {
          put("type", JsonPrimitive("number"))
          put("description", JsonPrimitive("Longitude of pickup location"))
        }
        putJsonObject("drop_lat") {
          put("type", JsonPrimitive("number"))
          put("description", JsonPrimitive("Latitude of drop location"))
        }
        putJsonObject("drop_lng") {
          put("type", JsonPrimitive("number"))
          put("description", JsonPrimitive("Longitude of drop location"))
        }
        putJsonObject("customer_name") {
          put("type", JsonPrimitive("string"))
          put("description", JsonPrimitive("Customer name"))
        }
        putJsonObject("country_code") {
          put("type", JsonPrimitive("string"))
          put("description", JsonPrimitive("Phone country code (e.g., +91)"))
          put("default", JsonPrimitive("+91"))
        }
        putJsonObject("phone_number") {
          put("type", JsonPrimitive("string"))
          put("description", JsonPrimitive("Customer phone number"))
        }
      },
      required = listOf("pickup_lat", "pickup_lng", "drop_lat", "drop_lng", "customer_name", "phone_number")
    )
  ) { request ->
    val pickupLat = request.arguments["pickup_lat"]?.jsonPrimitive?.doubleOrNull
    val pickupLng = request.arguments["pickup_lng"]?.jsonPrimitive?.doubleOrNull
    val dropLat = request.arguments["drop_lat"]?.jsonPrimitive?.doubleOrNull
    val dropLng = request.arguments["drop_lng"]?.jsonPrimitive?.doubleOrNull
    val customerName = request.arguments["customer_name"]?.jsonPrimitive?.content
    val countryCode = request.arguments["country_code"]?.jsonPrimitive?.content ?: "+91"
    val phoneNumber = request.arguments["phone_number"]?.jsonPrimitive?.content

    // Validate required parameters
    if (pickupLat == null || pickupLng == null || dropLat == null || dropLng == null ||
      customerName == null || phoneNumber == null) {
      return@addTool CallToolResult(
        content = listOf(TextContent("Missing required parameters. Please provide pickup coordinates, drop coordinates, customer name, and phone number."))
      )
    }

    try {
      val quotes = httpClient.getQuote(
        pickupLat, pickupLng, dropLat, dropLng,
        customerName, countryCode, phoneNumber
      )

      CallToolResult(content = quotes.map { TextContent(it) })
    } catch (e: Exception) {
      CallToolResult(
        content = listOf(TextContent("Error getting Porter quotes: ${e.message}"))
      )
    }
  }
}
