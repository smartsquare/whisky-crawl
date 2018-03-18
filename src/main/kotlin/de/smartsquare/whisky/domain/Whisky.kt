package de.smartsquare.whisky.domain

import java.math.BigDecimal
import java.time.Instant

data class Whisky(val name: String,
                  val description: String,
                  val alcohol: Double,
                  val liter: Double,
                  val price: BigDecimal,
                  val scrapingDate: Instant)