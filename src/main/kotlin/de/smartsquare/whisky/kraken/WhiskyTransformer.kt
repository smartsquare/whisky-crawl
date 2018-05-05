package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.domain.Whisky
import java.math.BigDecimal
import java.time.Instant


object WhiskyTransformer {

    fun transform(name: String, distillery: String, age: Int, description: String, liter: String?, alcohol: String?, price: String, source: String): Whisky {

        val parsedAlcohol = alcohol?.trim()?.toLowerCase()?.removeSuffix("%")?.removeSuffix("% vol")?.trim()?.replace(",", ".")?.toDouble()
        val parsedLiter = liter?.trim()?.toLowerCase()?.removeSuffix("liter")?.trim()?.replace(",", ".")?.toDouble()

        val parsedPrice = normalizePriceToBigDecimal(price.trim())

        val whisky = Whisky(name.trim(), distillery.trim(), age, description.trim(), parsedAlcohol, parsedLiter, parsedPrice, Instant.now(), source)

        return whisky
    }

    private fun normalizePriceToBigDecimal(price: String): BigDecimal {
        return price
                .removeSuffix("€")
                .removeSuffix("EUR")
                .trim()
                .replace(".", "") // remove the . in 1.000,00
                .replace(",", ".") // replace the , with a . in 1000,00
                .toBigDecimal()
    }
}