package de.smartsquare.whisky

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import de.smartsquare.whisky.crawler.BasicCrawler
import de.smartsquare.whisky.crawler.BasicParser
import de.smartsquare.whisky.crawler.Transformer
import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.dynamo.DynamoDbClient
import org.json.JSONObject
import java.util.stream.Collectors

class CrawlHandler : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any?, ctx: Context?): Any {
        val crawler = BasicCrawler(BasicParser(Transformer()))
        var dynamo = DynamoDbClient(DynamoDbClient.create())

        val baseUrl = "https://www.whiskyworld.de/alle-whiskies"

        val paginationLinksFromBaseUrl = crawler.extractPaginationLinksFromBaseUrl(baseUrl)

        val allWhiskys: List<List<Whisky>> = paginationLinksFromBaseUrl
                .map { subLink -> crawler.crawl(subLink) }

        val whiskyList = allWhiskys.flatten()

        whiskyList.forEach { whisky -> dynamo.write(whisky) }

        val headers = hashMapOf("Content-Type" to "application/json")
        return GatewayResponse(JSONObject().put("Output", JSONObject.valueToString(whiskyList)).toString(), headers, 200)
    }
}