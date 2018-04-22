package de.smartsquare.whisky

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import de.smartsquare.whisky.dynamo.DynamoDbClient
import de.smartsquare.whisky.kraken.WhiskyKraken
import org.apache.logging.log4j.LogManager
import org.json.JSONObject

class CrawlHandler : RequestHandler<String, Any> {

    val log = LogManager.getLogger()

    override fun handleRequest(input: String?, ctx: Context?): Any {
        log.info("Starting Whisky Crawler")

        val headers = hashMapOf("Content-Type" to "application/json")
        if(input == null || input.isBlank()) {
            return GatewayResponse(
                    JSONObject().put("Output", "No crawler destination specified.").toString(),
                    headers,
                    400)
        }



        val whiskyKraken = WhiskyKraken()
        val dynamo = DynamoDbClient(DynamoDbClient.create())

        val whiskyList = whiskyKraken.collectWhiskyInformationFrom(input)
        whiskyList.forEach { whisky -> dynamo.write(whisky) }


        return GatewayResponse(
                JSONObject().put("Output", JSONObject.valueToString(whiskyList)).toString(),
                headers,
                200)
    }
}