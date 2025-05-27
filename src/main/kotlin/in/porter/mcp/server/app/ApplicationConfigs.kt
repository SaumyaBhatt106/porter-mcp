package `in`.porter.mcp.server.app

object ApplicationConfigs {

  object LocationIq {
    const val BASE_URL = "https://us1.locationiq.com"
    val API_KEY = System.getenv("LOCATION_IQ_API_KEY")
      ?: throw IllegalStateException("LOCATION_IQ_API_KEY environment variable is not set")
  }

  object Pfe {
    const val BASE_URL = "https://pfe-apigw-uat.porter.in"
    val API_KEY: String = System.getenv("PFE_API_KEY")
      ?: throw IllegalStateException("PFE_API_KEY environment variable is not set")
  }
}
