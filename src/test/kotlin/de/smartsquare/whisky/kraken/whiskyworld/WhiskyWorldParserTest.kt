package de.smartsquare.whisky.kraken.whiskyworld

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets

class WhiskyWorldParserTest {

    val parser = WhiskyWorldParser(Transformer())

    @Test
    fun should_parse_html_page() {
        // given
        val html = File("src/test/resources/html/webpage_all_sorts_p1.html")
        val document = Jsoup.parse(html, StandardCharsets.UTF_8.displayName())

        // when
        val whiskyList = parser.readWhiskyListFromHtmlDocument(document)

        // then
        assertThat(whiskyList).isNotNull
        assertThat(whiskyList).hasSize(36)
    }

    @Test
    fun should_extract_pagination_links() {
        // given
        val html = File("src/test/resources/html/webpage_all_sorts_p1.html")
        val document = Jsoup.parse(html, StandardCharsets.UTF_8.displayName())

        // when
        val paginationLinks = parser.getPaginationLinks(document)

        // then
        assertThat(paginationLinks).isNotEmpty
        assertThat(paginationLinks).hasSize(28)
        assertThat(paginationLinks).contains(html.absolutePath + "?page=1")
        assertThat(paginationLinks).contains(html.absolutePath + "?page=28")

    }
}