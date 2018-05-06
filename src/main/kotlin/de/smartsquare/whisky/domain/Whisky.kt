package de.smartsquare.whisky.domain

import org.apache.commons.text.similarity.JaroWinklerDistance
import org.apache.logging.log4j.LogManager
import java.math.BigDecimal
import java.time.Instant

val log = LogManager.getLogger()

data class Whisky(val name: String,
                  val distillery: String,
                  val age: Int,
        //val vintage: Int,
                  val description: String,
                  val alcohol: Double?,
                  val liter: Double?,
                  val price: BigDecimal,
                  val scrapingDate: Instant,
                  val source: String) {

    fun isSameAs(other: Whisky): Boolean {

        if (age == other.age
                && distillery == other.distillery
                && alcohol == other.alcohol
                && liter == other.liter) {

            if (isNameInRange(other.name)) {
                return true
            }
        }

        return false
    }

    private fun isNameInRange(otherName: String): Boolean {
        val distance = JaroWinklerDistance().apply(this.name, otherName)

        if (distance >= 0.5 && distance < 0.7) {
            log.info("$distance - ${this.name} <--> $otherName")
        }

        return distance >= 0.7
    }

}