package de.smartsquare.whisky.kraken

import de.smartsquare.whisky.kraken.noop.NoOpKraken
import de.smartsquare.whisky.kraken.whiskyworld.WhiskyWorldKraken
import de.smartsquare.whisky.kraken.wishkyde.WhiskyDeKraken
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 *
 */
class KrakenFactoryTest {

    @Test
    fun should_create_correct_instance_of_WhiskyWorldKraken() {
        val kraken = KrakenFactory.getMonsterOfTheSea("WhiskyWorld")
        assertThat(kraken).isInstanceOf(WhiskyWorldKraken::class.java)
    }

    @Test
    fun should_create_correct_instance_of_WhiskyDeKraken() {
        val kraken = KrakenFactory.getMonsterOfTheSea("WhiskyDe")
        assertThat(kraken).isInstanceOf(WhiskyDeKraken::class.java)
    }

    @Test
    fun should_create_correct_instance_of_NoOpKraken_on_unkown() {
        val kraken = KrakenFactory.getMonsterOfTheSea("FooBaa")
        assertThat(kraken).isInstanceOf(NoOpKraken::class.java)
    }

}