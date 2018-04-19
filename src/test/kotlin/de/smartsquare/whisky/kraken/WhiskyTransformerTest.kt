package de.smartsquare.whisky.kraken

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
 * @author Ruben Gees
 */
class WhiskyTransformerTest {

    @Test
    fun should_transform_element_to_whisky_object() {
        val name = "Aberfeldy"
        val description = "12 Jahre "
        val liter = "  0,70 Liter"
        val alcohol = " 40.00% Vol"
        val priceElement = "32,95 €"

        val whisky = WhiskyTransformer.transform(name, description, liter, alcohol, priceElement)

        assertThat(whisky.name).isEqualTo("Aberfeldy")
        assertThat(whisky.description).isEqualTo("12 Jahre")
        assertThat(whisky.liter).isEqualTo(0.70)
        assertThat(whisky.alcohol).isEqualTo(40.00)
        assertThat(whisky.price).isEqualTo(BigDecimal.valueOf(32.95))
    }

    @Test
    fun should_transform_four_digit_prices_correctly() {
        val name = "Glenlivet"
        val description = "Jahrgang 1948 in Holzbox mit Zertifikat"
        val liter = "  0,70 Liter"
        val alcohol = " 40.00% Vol"
        val priceElement = "2.895,00 €"

        val whisky = WhiskyTransformer.transform(name, description, liter, alcohol, priceElement)

        assertThat(whisky.name).isEqualTo("Glenlivet")
        assertThat(whisky.description).isEqualTo("Jahrgang 1948 in Holzbox mit Zertifikat")
        assertThat(whisky.liter).isEqualTo(0.70)
        assertThat(whisky.alcohol).isEqualTo(40.00)
        assertThat(whisky.price).isEqualTo(BigDecimal.valueOf(2895.00).setScale(2))
    }
}