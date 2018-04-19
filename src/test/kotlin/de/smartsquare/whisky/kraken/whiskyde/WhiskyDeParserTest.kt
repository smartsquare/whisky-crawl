package de.smartsquare.whisky.kraken.whiskyde

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets

/**
 * @author Ruben Gees
 */
class WhiskyDeParserTest {

    val parser = WhiskyDeParser(WhiskyDeTransformer())
    val pagingBaseLink = "https://www.baafoo.de/shop/index.php?force_sid=4ef3ac6e79f7483f1ab5e31073b74526" +
            "&cl=search&searchorigin=1&pgNr=NR_PLACEHOLDER"

    @Test
    fun should_parse_html_page() {
        // given
        val html = File("src/test/resources/html/webpage_search.html")
        val document = Jsoup.parse(html, StandardCharsets.UTF_8.displayName())

        // when
        val whiskyList = parser.readWhiskyListFromHtmlDocument(document)

        // then
        assertThat(whiskyList).isNotNull
        assertThat(whiskyList).hasSize(30)
    }

    @Test
    fun should_extract_pagination_links() {
        // given
        val html = File("src/test/resources/html/webpage_search.html")
        val document = Jsoup.parse(html, StandardCharsets.UTF_8.displayName())

        // when
        val paginationLinks = parser.getPaginationLinks(document)

        // then
        assertThat(paginationLinks).isNotEmpty
        assertThat(paginationLinks).hasSize(71)
        assertThat(paginationLinks).contains(pagingBaseLink.replace("NR_PLACEHOLDER", "1"))
        assertThat(paginationLinks).contains(pagingBaseLink.replace("NR_PLACEHOLDER", "71"))
    }
}