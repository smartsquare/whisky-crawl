package de.smartsquare.whisky

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import de.smartsquare.whisky.dynamo.DynamoDbClient
import de.smartsquare.whisky.kraken.WhiskyKraken
import org.apache.logging.log4j.LogManager
import org.json.JSONObject

class CrawlHandler : RequestHandler<Any, Any> {

    val log = LogManager.getLogger()

    override fun handleRequest(input: Any?, ctx: Context?): Any {
        log.info("Starting Whisky Crawler§")

        val whiskyKraken = WhiskyKraken()
        val dynamo = DynamoDbClient(DynamoDbClient.create())

        val whiskyList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld", "WhiskyDe")
        whiskyList.forEach { whisky -> dynamo.write(whisky) }

        val headers = hashMapOf("Content-Type" to "application/json")

        return GatewayResponse(
                JSONObject().put("Output", JSONObject.valueToString(whiskyList)).toString(),
                headers,
                200)
    }
}