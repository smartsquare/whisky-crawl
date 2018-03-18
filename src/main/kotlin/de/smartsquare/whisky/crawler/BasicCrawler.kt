package de.smartsquare.whisky.crawler

import de.smartsquare.whisky.domain.Whisky
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

class BasicCrawler(val parser: BasicParser) {

    companion object {
        val log = LoggerFactory.getLogger(BasicCrawler::class.java.simpleName)
    }

    fun extractPaginationLinksFromBaseUrl(baseUrl: String): List<String> {
        val doc = Jsoup.connect(baseUrl).get()
        return parser.getPaginationLinks(doc)
    }

    fun crawl(url: String): List<Whisky> {
        log.info("Crawling URL: {}", url)
        val doc = Jsoup.connect(url).get()
        return parser.readWhiskyListFromHtmlDocument(doc)
    }

}


