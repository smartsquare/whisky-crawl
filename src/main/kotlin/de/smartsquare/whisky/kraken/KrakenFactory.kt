package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.kraken.noop.NoOpKraken
import de.smartsquare.whisky.kraken.whiskyworld.WhiskyWorldKraken
import de.smartsquare.whisky.kraken.wishkyde.WhiskyDeKraken
import org.slf4j.LoggerFactory

/**
 *
 */
class KrakenFactory {

    companion object {

        val log = LoggerFactory.getLogger(this::class.java.simpleName)

        fun getMonsterOfTheSea(krakenName: String): Kraken {
            when (krakenName) {
                "WhiskyWorld" -> return WhiskyWorldKraken()
                "WhiskyDe" -> return WhiskyDeKraken()
                else -> {
                    log.warn("No Implementation found for [$krakenName], returning NoOp Kraken.")
                    return NoOpKraken()
                }
            }
        }
    }

}