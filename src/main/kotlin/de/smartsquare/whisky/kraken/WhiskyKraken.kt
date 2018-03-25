package de.smartsquare.whisky.kraken.wishkyde

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.KrakenFactory

class WhiskyKraken() {

    fun collectWhiskyInformationFrom(whiskySiteIdentifier: String): List<Whisky> {
        val kraken = KrakenFactory.getMonsterOfTheSea(whiskySiteIdentifier)
        return kraken.crawlWhiskyPage()
    }

}


