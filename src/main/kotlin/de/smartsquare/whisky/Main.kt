package de.smartsquare.whisky

import com.fasterxml.jackson.databind.ObjectMapper
import de.smartsquare.whisky.domain.Whisky
import de.smartsquare.whisky.kraken.WhiskyKraken
import org.apache.logging.log4j.LogManager
import java.io.BufferedWriter
import java.io.File


/**
 * Main script to start crawling locally
 */
val log = LogManager.getLogger()

fun main(args: Array<String>) {
    val whiskyKraken = WhiskyKraken()

    val startWhiskyWorld = System.currentTimeMillis()
    log.info("Crawling Whisky world")
    val whiskyWorldList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld")
    writeToFile(whiskyWorldList, "whiskyworld.json")
    log.info("Whiskys {} in {}s", whiskyWorldList.size, (System.currentTimeMillis() - startWhiskyWorld) / 1000)

    val startWhiskyDe = System.currentTimeMillis()
    log.info("Crawling Whisky.de")

    val whiskyDeList = whiskyKraken.collectWhiskyInformationFrom("WhiskyDe")
    writeToFile(whiskyDeList, "whiskyde.json")
    log.info("Whiskys {} in {}s", whiskyDeList.size, (System.currentTimeMillis() - startWhiskyDe) / 1000)

    log.info("Finished")
}

private fun writeToFile(whiskyList: List<Whisky>, fileName: String) {
    val mapper = ObjectMapper()
    File(fileName).bufferedWriter().use { out ->
        whiskyList.forEach {
            out.writeLn(mapper.writeValueAsString(it))
        }
    }
}

fun BufferedWriter.writeLn(line: String) {
    this.write(line)
    this.newLine()
}
