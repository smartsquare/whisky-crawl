package de.smartsquare.whisky.kraken.whiskyde

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.jsoup.JsoupWrapper
import de.smartsquare.whisky.kraken.Kraken
import de.smartsquare.whisky.util.parallelFlatMap

class WhiskyDeKraken(
        val parser: WhiskyDeParser = WhiskyDeParser(),
        val jsoup: JsoupWrapper = JsoupWrapper()
) : Kraken {

    val baseUrl = "https://www.whisky.de/shop/index.php?cl=search&_artperpage=30"

    override fun crawlWhiskyPage(): List<Whisky> {
        val doc = jsoup.readWebPage(baseUrl)
        val paginationLinks = parser.getPaginationLinks(doc)

        return paginationLinks
                .parallelFlatMap { subLink -> crawl(subLink) }
    }

    private fun crawl(detailLink: String): List<Whisky> {
        val doc = jsoup.readWebPage(detailLink)

        return parser.readWhiskyListFromHtmlDocument(doc)
    }
}
