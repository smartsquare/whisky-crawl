package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.AgeExtractor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class AgeExtractorTest {

    val extractor = AgeExtractor()


    @Test
    fun extract_name_from_description() {

        assertThat(extractor.parseAge("12 Jahre in Metallbox")).isEqualTo(12)
        assertThat(extractor.parseAge("16 Jahre Double Cask Matured")).isEqualTo(16)
        assertThat(extractor.parseAge("8 Jahre Non Chill-Filtered")).isEqualTo(8)
        assertThat(extractor.parseAge("Old No.7 Miniatur")).isEqualTo(0)
        assertThat(extractor.parseAge("Frontier Bourbon Whiskey 10 Jahre")).isEqualTo(10)
        assertThat(extractor.parseAge("Kentucky straight Bourbon 6 Jahre 1 Monat")).isEqualTo(6)
        assertThat(extractor.parseAge("Bourbon Kentucky Straight Bourbon Whiskey")).isEqualTo(0)
        assertThat(extractor.parseAge("Jahrgang 2003 Miniatur")).isEqualTo(0)
        assertThat(extractor.parseAge("Jahrgang 1988 28 Jahre The Ultimate Single Cask Abfüllung")).isEqualTo(28)
        assertThat(extractor.parseAge("Benrinnes 21J-1995/2016")).isEqualTo(21)
        assertThat(extractor.parseAge("Balmenach 8J-2008/2017")).isEqualTo(8)
        assertThat(extractor.parseAge("Kininvie 17J-1996")).isEqualTo(17)
        assertThat(extractor.parseAge("Inchmurrin Manzanilla 2009/2017")).isEqualTo(0)

    }
}