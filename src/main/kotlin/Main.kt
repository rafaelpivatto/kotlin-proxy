import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

fun main() {
    // Load configuration from application.conf
    val applicationConfig = HoconApplicationConfig(ConfigFactory.load())

    val port = applicationConfig.propertyOrNull("ktor.deployment.port")?.getString()?.toInt() ?: 9080
    val baseUrl = applicationConfig.propertyOrNull("ktor.application.baseUrl")?.getString() ?: ""

    embeddedServer(Netty, port) {
        routing {
            post("/{path...}") {
                // Receive the SOAP request body from the client
                val requestXml = call.receiveText()

                // Extract the path from the URL
                val path = call.parameters.getAll("path")?.joinToString("/") ?: ""

                println("Received SOAP request for path: /$path\n$requestXml")

                // Build the complete URL for the external service
                val url = "$baseUrl/$path"

                // Set the media type for the SOAP request body
                val mediaType = "application/soap+xml; charset=utf-8".toMediaType()

                // Create the request body with the received SOAP request
                val requestBody = requestXml.toRequestBody(mediaType)

                // Build the HTTP request
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                // Call the external service with the received XML and get the response XML
                val responseXml = callExternalService(request)

                println("Respond SOAP response for path: /$path\n$responseXml")

                // Respond back to the client with the response XML from the external service
                call.respond(responseXml)
            }
        }
    }.start(wait = true)
}

/**
 * Calls the external service with the provided request and returns the response body.
 * Throws an exception if the response is not successful or the response body is null.
 */
fun callExternalService(request: Request): String {
    val client = OkHttpClient()
    val response = client.newCall(request).execute()

    if (!response.isSuccessful) {
        throw RuntimeException("Unexpected code ${response.code}")
    }

    return response.body?.string() ?: throw RuntimeException("Response body is null")
}
