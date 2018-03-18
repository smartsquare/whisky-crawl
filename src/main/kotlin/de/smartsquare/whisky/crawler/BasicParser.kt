package de.smartsquare.whisky.crawler

import de.smartsquare.whisky.domain.Whisky
import org.jsoup.nodes.Document

class BasicParser(val transformer: Transformer) {

    fun readWhiskyListFromHtmlDocument(document: Document): List<Whisky> {
        val products = document.select(".product-item a")
        return products.map { product -> transformer.transform(product) }.toList()
    }

    fun getPaginationLinks(document: Document): List<String> {
        val paginationLinks = document.select(".pagination a")

        val paginationLinkTemplate = paginationLinks
                .map { link -> link.attr("href") }
                .first()


        val highestPageNo = paginationLinks
                .map { link -> link.attr("href") }
                .map { link -> link.removeRange(0, link.indexOf("=") + 1) }
                .map { pageNo -> pageNo.toInt() }
                .sorted()
                .last()

        val location = document.location()
        val links = (1..highestPageNo)
                .map { pageNo -> paginationLinkTemplate.replaceBefore('?', location).replaceAfter('=', pageNo.toString()) }
                .toList()

        return links
    }

}
