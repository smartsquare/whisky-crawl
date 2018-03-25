package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.Instant

/**
 *
 */
class Transformer {

    companion object {
        val log = LoggerFactory.getLogger(Transformer::class.java.simpleName)
    }

    fun transform(product: Element): Whisky {

        val name = product.select(".product-infobox h3").text()

        val description = product.select(".product-infobox .item-description").text()
        val contents = product.select(".product-infobox .item-inh").text()
        val priceElement = product.select(".item-bottom .price .uvp").text()

        return transform(name, description, contents, priceElement)
    }

    fun transform(name: String, description: String, contents: String, priceElement: String): Whisky {

        val split = contents.split("/")
        val liter = split.get(0).removeSuffix("Liter").trim().replace(",", ".").toDouble()
        val alcohol = split.get(1).removeSuffix("% Vol").trim().replace(",", ".").toDouble()

        val price = normalizePriceToBigDecimal(priceElement)

        val whisky = Whisky(name.trim(), description.trim(), alcohol, liter, price, Instant.now())

        log.info("{}", whisky)

        return whisky
    }

    private fun normalizePriceToBigDecimal(price: String): BigDecimal {
        return price
                .removeSuffix("€")
                .trim()
                .replace(".", "") // remove the . in 1.000,00
                .replace(",", ".") // replace the , with a . in 1000,00
                .toBigDecimal()
    }
}