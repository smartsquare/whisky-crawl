package de.smartsquare.whisky

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import de.smartsquare.whisky.crawler.BasicCrawler
import de.smartsquare.whisky.crawler.BasicParser
import de.smartsquare.whisky.crawler.Transformer
import de.smartsquare.whisky.domain.Whisky
import org.json.JSONObject
import java.util.stream.Collectors

class CrawlHandler : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any?, ctx: Context?): Any {
        val crawler = BasicCrawler(BasicParser(Transformer()))

        val baseUrl = "https://www.whiskyworld.de/alle-whiskies"

        val paginationLinksFromBaseUrl = crawler.extractPaginationLinksFromBaseUrl(baseUrl)

        val allWhiskys: List<List<Whisky>> = paginationLinksFromBaseUrl
                .map { subLink -> crawler.crawl(subLink) }

        val whiskyList = allWhiskys.flatten()

        val headers = hashMapOf("Content-Type" to "application/json")
        return GatewayResponse(JSONObject().put("Output", JSONObject.valueToString(whiskyList)).toString(), headers, 200)
    }
}