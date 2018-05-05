package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets

class WhiskyWorldParserTest {

    val parser = WhiskyWorldParser(WhiskyWorldTransformer())

    @Test
    fun should_parse_html_page() {
        // given
        val html = File("src/test/resources/html/webpage_product_detail.html")
        val document = Jsoup.parse(html, StandardCharsets.UTF_8.displayName())

        // when
        val whisky: Whisky = parser.readWhiskyFromProductDetailHtmlDocument(document)

        // then
        assertThat(whisky).isNotNull
        assertThat(whisky.name).isEqualTo("Aberlour 12 Jahre Non Chill-Filtered")
        assertThat(whisky.age).isEqualTo(12)
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