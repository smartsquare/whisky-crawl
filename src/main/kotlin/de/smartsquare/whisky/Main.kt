package de.smartsquare.whisky

import de.smartsquare.whisky.kraken.WhiskyKraken
import org.apache.logging.log4j.LogManager

/**
 * Main script to start crawling locally
 */
val log = LogManager.getLogger()

fun main(args: Array<String>) {
    val whiskyKraken = WhiskyKraken()

    log.info("Crawling Whisky world")

    val whiskyWorldList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld")
    whiskyWorldList.forEach { whisky -> log.info(whisky.toString()) }

    log.info("Crawling Whisky.de")

    val whiskyDeList = whiskyKraken.collectWhiskyInformationFrom("WhiskyDe")
    whiskyDeList.forEach { whisky -> log.info(whisky.toString()) }
}
