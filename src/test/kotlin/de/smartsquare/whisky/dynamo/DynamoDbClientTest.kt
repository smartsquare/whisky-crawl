package de.smartsquare.whisky.dynamo

import de.smartsquare.whisky.domain.Whisky
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant

/**
 *
 */
@Ignore("Runs currently only with a local instance of DynamoDB")
class DynamoDbClientTest {

    @Before
    fun setUp() {
        val dynamo = DynamoDbClient(DynamoDbClient.create())
        dynamo.deleteExistingTable()
    }

    @Test
    fun should_create_inital_table() {

        val dynamo = DynamoDbClient(DynamoDbClient.create())
        dynamo.createInitialTable()

        val table = dynamo.dynamoDB.getTable(DynamoDbClient.tableName)

        assertThat(table).isNotNull
    }

    @Test
    fun should_write_whisky_object_to_dynamo() {
        val whisky = Whisky("Aberfeldy", "12 Jahre", 40.0, 0.7, BigDecimal.valueOf(35.40), Instant.now())

        val dynamo = DynamoDbClient(DynamoDbClient.create())
        dynamo.createInitialTable()

        dynamo.write(whisky)

        val item = dynamo.readItem("Aberfeldy")
        assertThat(item).isNotNull
    }

    @Test
    fun should_write_multiple_whisky_objects_to_dynamo() {
        val whisky1 = Whisky("Aberfeldy", "12 Jahre", 40.0, 0.7, BigDecimal.valueOf(35.40), Instant.now())
        val whisky2 = Whisky("Aberlour", "10 Jahre", 40.0, 0.7, BigDecimal.valueOf(36.50), Instant.now())
        val whisky3 = Whisky("Aberlour", "16 Jahre Double Cask Matured", 40.0, 0.7, BigDecimal.valueOf(32.00), Instant.now())

        val dynamo = DynamoDbClient(DynamoDbClient.create())
        dynamo.createInitialTable()

        dynamo.write(whisky1)
        dynamo.write(whisky2)
        dynamo.write(whisky3)

        val items = dynamo.readItem("Aberlour 16 Jahre Double Cask Matured")
        assertThat(items).isNotNull
        assertThat(items).hasSize(1)

    }
}