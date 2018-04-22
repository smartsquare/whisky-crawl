package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.domain.Whisky

class WhiskyKraken {

    fun collectWhiskyInformationFrom(whiskySiteIdentifier: String): List<Whisky> {
        val kraken = KrakenFactory.getMonsterOfTheSea(whiskySiteIdentifier)

        return kraken.crawlWhiskyPage()
    }
}


