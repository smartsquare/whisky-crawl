package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.jsoup.JsoupWrapper
import de.smartsquare.whisky.kraken.Kraken
import java.util.stream.Collectors

class WhiskyWorldKraken(val parser: WhiskyWorldParser = WhiskyWorldParser(),
                        val jsoup: JsoupWrapper = JsoupWrapper()) : Kraken {

    val baseUrl = "https://www.whiskyworld.de/alle-whiskies"

    override fun crawlWhiskyPage(): List<Whisky> {

        val doc = jsoup.readWebPage(baseUrl)
        val paginationLinksFromBaseUrl = parser.getPaginationLinks(doc)

        val allWhiskys: List<List<Whisky>> = paginationLinksFromBaseUrl
                .parallelStream()
                .map { subLink -> crawl(subLink) }
                .collect(Collectors.toList())

        return allWhiskys.flatten()
    }

    private fun crawl(subLink: String): List<Whisky> {
        val doc = jsoup.readWebPage(subLink)
        return parser.readWhiskyListFromHtmlDocument(doc)
    }


}
