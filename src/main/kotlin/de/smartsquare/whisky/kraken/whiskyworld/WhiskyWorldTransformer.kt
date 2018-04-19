package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.WhiskyTransformer
import org.jsoup.nodes.Element

/**
 *
 */
class WhiskyWorldTransformer {

    fun transform(product: Element): Whisky? {
        return try {
            val name = product.select(".product-infobox h3").text()
            val description = product.select(".product-infobox .item-description").text()
            val price = product.select(".item-bottom .price .uvp").text()

            val contents = product.select(".product-infobox .item-inh").text()

            val (liter, alcohol) = contents.split("/").let { it.first() to it.last() }

            WhiskyTransformer.transform(name, description, liter, alcohol, price)
        } catch (e: NullPointerException) {
            null
        }
    }
}