package de.smartsquare.whisky

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.json.JSONObject

class CrawlHandler : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any?, ctx: Context?): Any {
        val headers = hashMapOf("Content-Type" to "application/json")
        return GatewayResponse(JSONObject().put("Output", "Hello World!").toString(), headers, 200)
    }
}