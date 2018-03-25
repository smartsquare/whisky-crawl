package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.WhiskyTestData
import de.smartsquare.whisky.jsoup.JsoupWrapper
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.nodes.Document
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 *
 */
@ExtendWith(MockKExtension::class)
class WhiskyWorldKrakenTest {

    val parser = mockk<WhiskyWorldParser>()
    val jsoup = mockk<JsoupWrapper>()
    val kraken = WhiskyWorldKraken(parser, jsoup)

    @Before
    fun setUp() {
        every { jsoup.readWebPage(any()) } returns Document("foo://baa")
        every { parser.getPaginationLinks(any()) } returns listOf("subLink1","subLink2")
        every { parser.readWhiskyListFromHtmlDocument(any()) } returns listOf(WhiskyTestData.whisky1, WhiskyTestData.whisky2)
    }

    @Test
    fun should_collect_pagination_links() {
        kraken.crawlWhiskyPage()
        verify(exactly = 1) { parser.getPaginationLinks(any()) }
    }

    @Test
    fun should_read_whisky_from_subLinks() {
        val whiskys = kraken.crawlWhiskyPage()

        assertThat(whiskys).hasSize(4) // returns 2 whiskys for each sublink
        verify(exactly = 1) { parser.getPaginationLinks(any()) }
        verify(exactly = 2) { parser.readWhiskyListFromHtmlDocument(any()) }
    }
}