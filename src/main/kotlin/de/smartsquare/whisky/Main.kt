package de.smartsquare.whisky

import de.smartsquare.whisky.kraken.wishkyde.WhiskyKraken
import org.slf4j.LoggerFactory

/**
 * Main script to start crawling locally
 */

val log = LoggerFactory.getLogger("Main")

fun main(args: Array<String>) {
    val whiskyKraken = WhiskyKraken()

    val whiskyList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld")
    whiskyList.forEach { whisky -> log.info(whisky.toString()) }
}
