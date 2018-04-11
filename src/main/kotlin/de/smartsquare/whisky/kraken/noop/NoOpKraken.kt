package de.smartsquare.whisky.kraken.noop

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.Kraken
import org.apache.logging.log4j.LogManager

/**
 *
 */
class NoOpKraken : Kraken {

    val log = LogManager.getLogger()

    override fun crawlWhiskyPage(): List<Whisky> {
        log.warn("NoOp Kraken Called!")
        return listOf()
    }

}