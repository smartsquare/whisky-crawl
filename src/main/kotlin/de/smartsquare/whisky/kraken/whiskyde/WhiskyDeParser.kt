package de.smartsquare.whisky.kraken.whiskyde

import de.smartsquare.whisky.domain.Whisky
import org.jsoup.nodes.Document

/**
 * @author Ruben Gees
 */
class WhiskyDeParser(val transformer: WhiskyDeTransformer = WhiskyDeTransformer()) {

    fun getPaginationLinks(document: Document): List<String> {
        val (pageAmount, pageLink) = document.select(".infinite-scroll").first().let {
            it.attr("data-infinite-scroll-max-page").toInt() to
                    it.attr("data-infinite-scroll-loading-url")
        }

        return (1..pageAmount).map { pageLink.replace("PAGEPLACEHOLDER", it.toString()) }
    }

    fun readWhiskyListFromHtmlDocument(document: Document): List<Whisky> {
        val products = document.select("div.article-panel")

        return products.map { transformer.transform(it) }.toList().filterNotNull()
    }
}