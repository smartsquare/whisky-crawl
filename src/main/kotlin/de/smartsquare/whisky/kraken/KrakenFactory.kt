package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.kraken.noop.NoOpKraken
import de.smartsquare.whisky.kraken.whiskyde.WhiskyDeKraken
import de.smartsquare.whisky.kraken.whiskyworld.WhiskyWorldKraken
import org.apache.logging.log4j.LogManager

/**
 *
 */
class KrakenFactory {

    companion object {

        val log = LogManager.getLogger()

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