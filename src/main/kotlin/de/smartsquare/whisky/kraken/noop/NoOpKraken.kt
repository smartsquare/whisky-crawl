package de.smartsquare.whisky.kraken.noop

import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.Kraken
import org.slf4j.LoggerFactory

/**
 *
 */
class NoOpKraken : Kraken {

    companion object {
        val log = LoggerFactory.getLogger(NoOpKraken::class.java.simpleName)
    }

    override fun crawlWhiskyPage(): List<Whisky> {
        log.warn("NoOp Kraken Called!")
        return listOf()
    }

}