package de.smartsquare.whisky.domain

import java.math.BigDecimal
import java.time.Instant

data class Whisky(val name: String,
                  val distillery: String,
                  val age: Int,
        //val vintage: Int,
                  val description: String,
                  val alcohol: Double?,
                  val liter: Double?,
                  val price: BigDecimal,
                  val scrapingDate: Instant,
                  val source: String)