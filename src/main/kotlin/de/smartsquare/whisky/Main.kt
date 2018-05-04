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


    log.info("Crawling Whisky world")
    val whiskyWorldList = whiskyKraken.collectWhiskyInformationFrom("WhiskyWorld")
    writeToFile(whiskyWorldList, "whiskyworld.json")

    val start = System.currentTimeMillis()
    log.info("Crawling Whisky.de")

    val whiskyDeList = whiskyKraken.collectWhiskyInformationFrom("WhiskyDe")
    writeToFile(whiskyDeList, "whiskyde.json")
    log.info(System.currentTimeMillis() - start)

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
