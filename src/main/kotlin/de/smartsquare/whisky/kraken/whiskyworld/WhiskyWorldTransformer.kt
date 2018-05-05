package de.smartsquare.whisky.kraken.whiskyworld

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.WhiskyTransformer
import org.apache.logging.log4j.LogManager
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
/**
 *
 */
class WhiskyWorldTransformer {

    val log = LogManager.getLogger()

    fun transform(product: Element): Whisky {

        val name = product.getElementsByAttributeValue("itemprop", "name").first().text()
        val description = product.select(".description").text()
        val price = product.select(".price").first().text()

        val productCharacteristics = product.select(".product-characteristics").first()
        val liter = productCharacteristics.getElementsContainingOwnText("Inhalt").first().nextElementSibling().text()
        val alcohol = productCharacteristics.getElementsContainingOwnText("Vol%").first().nextElementSibling().text()
        val ageElement = productCharacteristics.getElementsContainingOwnText("Alter")

        val age = parseAge(ageElement)

        val productProperties = product.select(".product-properties").first()

        val distillery = getDistillery(productProperties)

        return WhiskyTransformer.transform(name, distillery, age, description, liter, alcohol, price, "WhiskyWorld")
    }

    private fun getDistillery(productProperties: Element): String {
        val distillery = productProperties.getElementsContainingOwnText("Lebensmittel-Unternehmer")
        if (distillery != null && distillery.size > 0) {
            return distillery.first().nextElementSibling().text()
        }

        return ""
    }


    private fun parseAge(ageElement: Elements?): Int {
        if (ageElement != null && ageElement.size > 0) {
            val ageText = ageElement.first().nextElementSibling().text()
            return ageText.removeSuffix("Jahre").trim().toInt()
        }

        return 0
    }
}

