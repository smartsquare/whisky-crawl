package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.jsoup.JsoupWrapper
import de.smartsquare.whisky.kraken.Kraken
import java.util.stream.Collectors

class WhiskyWorldKraken(val parser: WhiskyWorldParser = WhiskyWorldParser(),
                        val jsoup: JsoupWrapper = JsoupWrapper()) : Kraken {

    val baseUrl = "https://www.whiskyworld.de"
    val productPage = "/alle-whiskies"

    override fun crawlWhiskyPage(): List<Whisky> {

        val doc = jsoup.readWebPage(baseUrl + productPage)
        val paginationLinksFromBaseUrl = parser.getPaginationLinks(doc)

        val allProductDetailLinks: List<List<String>> = paginationLinksFromBaseUrl
                .parallelStream()
                .map { subLink -> crawlProductDetailPages(subLink) }
                .collect(Collectors.toList())

        val allWhiskys: List<Whisky> = allProductDetailLinks
                .flatten()
                .parallelStream()
                .map { link -> baseUrl + link }
                .map { link -> crawl(link) }
                .collect(Collectors.toList())

        return allWhiskys
    }

    private fun crawlProductDetailPages(subLink: String): List<String> {
        val doc = jsoup.readWebPage(subLink)
        return parser.readProductDetailLinks(doc)
    }

    private fun crawl(subLink: String): Whisky {
        val doc = jsoup.readWebPage(subLink)
        return parser.readWhiskyFromProductDetailHtmlDocument(doc)
    }


}
