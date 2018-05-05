package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import org.jsoup.nodes.Document

class WhiskyWorldParser(val transformer: WhiskyWorldTransformer = WhiskyWorldTransformer()) {

    fun readWhiskyFromProductDetailHtmlDocument(document: Document): Whisky {
        val product = document.getElementsByAttributeValue("itemtype", "http://schema.org/Product").first()
        return transformer.transform(product)
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

    fun readProductDetailLinks(doc: Document): List<String> {
        val productDetail = doc.select(".product-item a").map { link -> link.attr("href") }.toList()
        return productDetail
    }
}