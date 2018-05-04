package de.smartsquare.whisky

import de.smartsquare.whisky.domain.Whisky
import java.math.BigDecimal
import java.time.Instant

/**
 *
 */
class WhiskyTestData {
    companion object {
        val whisky1 = Whisky("Aberfeldy", 12, "12 Jahre", 40.0, 0.7, BigDecimal.valueOf(35.40), Instant.now(), "test")
        val whisky2 = Whisky("Aberlour", 10, "10 Jahre", 40.0, 0.7, BigDecimal.valueOf(36.50), Instant.now(), "test")
        val whisky3 = Whisky("Aberlour", 16, "16 Jahre Double Cask Matured", 40.0, 0.7, BigDecimal.valueOf(32.00), Instant.now(), "test")
    }
}