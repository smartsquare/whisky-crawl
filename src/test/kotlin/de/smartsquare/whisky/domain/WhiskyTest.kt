package de.smartsquare.whisky.domain

import de.smartsquare.whisky.WhiskyTestData
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WhiskyTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun should_marked_as_same_whisky() {
        assertTrue(WhiskyTestData.whisky1.isSameAs(WhiskyTestData.whisky1))
    }

}