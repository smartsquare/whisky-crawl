package de.smartsquare.whisky.kraken.whiskyworld

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransformerTest {

    val transformer = Transformer()

    @Test
    fun should_transform_element_to_whisky_object() {

        val name = "Aberfeldy"
        val description = "12 Jahre "
        val contents = "  0,70 Liter/ 40.00% Vol"
        val priceElement = "32,95 €"

        val whisky = transformer.transform(name, description, contents, priceElement)

        Assertions.assertThat(whisky.name).isEqualTo("Aberfeldy")
        Assertions.assertThat(whisky.description).isEqualTo("12 Jahre")
        Assertions.assertThat(whisky.liter).isEqualTo(0.70)
        Assertions.assertThat(whisky.alcohol).isEqualTo(40.00)
        Assertions.assertThat(whisky.price).isEqualTo(BigDecimal.valueOf(32.95))
    }

    @Test
    fun should_transform_four_digit_prices_correctly() {
        val name = "Glenlivet"
        val description = "Jahrgang 1948 in Holzbox mit Zertifikat"
        val contents = "  0,70 Liter/ 40.00% Vol"
        val priceElement = "2.895,00 €"

        val whisky = transformer.transform(name, description, contents, priceElement)

        Assertions.assertThat(whisky.name).isEqualTo("Glenlivet")
        Assertions.assertThat(whisky.description).isEqualTo("Jahrgang 1948 in Holzbox mit Zertifikat")
        Assertions.assertThat(whisky.liter).isEqualTo(0.70)
        Assertions.assertThat(whisky.alcohol).isEqualTo(40.00)
        Assertions.assertThat(whisky.price).isEqualTo(BigDecimal.valueOf(2895.00).setScale(2))
    }
}