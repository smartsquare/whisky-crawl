package de.smartsquare.whisky.kraken.whiskyde

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.WhiskyTransformer
import org.apache.logging.log4j.LogManager
import org.jsoup.nodes.Element

class WhiskyDeTransformer {

    val log = LogManager.getLogger()

    fun transform(product: Element): Whisky? {
        return try {
            val name = product.selectFirst(".article-title > a").text()
            val description = product.selectFirst(".article-description-short > div").text()
            val alcohol = product.select(".article-amount > span").getOrNull(1)?.text()
            val liter = product.select(".article-amount > span").getOrNull(0)?.text()
            val price = product.selectFirst(".article-price-default").text()

            WhiskyTransformer.transform(name, description, liter, alcohol, price, "WhiskyDe")
        } catch (e: NullPointerException) {
            null
        }
    }
}
