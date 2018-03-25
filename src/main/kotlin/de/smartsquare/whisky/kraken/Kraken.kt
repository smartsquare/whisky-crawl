package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.domain.Whisky

/**
 *
 */
interface Kraken {

    fun crawlWhiskyPage(): List<Whisky>

}