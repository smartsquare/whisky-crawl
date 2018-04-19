package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.util.parallelFlatMap

class WhiskyKraken {

    fun collectWhiskyInformationFrom(vararg whiskySiteIdentifiers: String): List<Whisky> {
        return whiskySiteIdentifiers.toList().parallelFlatMap {
            val kraken = KrakenFactory.getMonsterOfTheSea(it)

            kraken.crawlWhiskyPage()
        }
    }
}


