package de.smartsquare.whisky

import de.smartsquare.whisky.kraken.wishkyde.WhiskyKraken
import org.apache.logging.log4j.LogManager

/**
 * Main script to start crawling locally
 */
val log = LogManager.getLogger()
fun main(args: Array<String>) {
    val whiskyKraken = WhiskyKraken()

    val whiskyList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld")
    whiskyList.forEach { whisky -> log.info(whisky.toString()) }
}
