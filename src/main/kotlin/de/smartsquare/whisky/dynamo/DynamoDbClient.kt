package de.smartsquare.whisky.dynamo

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
import de.smartsquare.whisky.domain.Whisky
import org.apache.logging.log4j.LogManager
import java.math.BigDecimal
import java.time.Instant

class DynamoDbClient(val dynamoDB: DynamoDB) {

    companion object Factory {
        fun create(): DynamoDB = DynamoDB(AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build())

        val tableName = "WhiskyPriceEvolution"
    }

    val log = LogManager.getLogger()

    fun deleteExistingTable() {
        try {
            val table = dynamoDB.getTable(tableName)

            table.delete()
            table.waitForDelete()
        } catch (e: ResourceNotFoundException) {
            log.debug("$tableName does not exist")
        }
    }

    fun createInitialTable() {
        log.info("Attempting to create table; please wait...")

        val table = dynamoDB.createTable(tableName,
                listOf(
                        KeySchemaElement("id_name", KeyType.HASH), // Partition key
                        KeySchemaElement("id_scrapingDate", KeyType.RANGE) // Sort Key
                ),

                listOf(
                        AttributeDefinition("id_name", ScalarAttributeType.S),
                        AttributeDefinition("id_scrapingDate", ScalarAttributeType.N)
                ),

                ProvisionedThroughput(10L, 10L))

        table.waitForActive()

        log.info("Success.  Table status: {}", table.description.tableStatus)
    }

    fun write(whisky: Whisky) {
        val table = dynamoDB.getTable(tableName)

        val name = whisky.name
        val scrappingMillis = whisky.scrapingDate.toEpochMilli()
        val description = whisky.description

        val key = "$name $description"

        table.putItem(Item()
                .withPrimaryKey("id_name", key, "id_scrapingDate", scrappingMillis)
                .withString("whiskyName", name)
                .withString("description", description)
                .apply { if (whisky.alcohol != null) withDouble("alcohol", whisky.alcohol) }
                .apply { if (whisky.liter != null) withDouble("liter", whisky.liter) }
                .withDouble("price", whisky.price.toDouble()))

        log.info("PutItem succeeded: $name ")
    }

    fun readItem(name: String): List<Whisky> {
        val table = dynamoDB.getTable(tableName)

        val valueMap: ValueMap = ValueMap()
                .withString(":name", name)
        // .withLong(":from", Long.MIN_VALUE)
        // .withLong(":to", Instant.now().plusSeconds(1000).toEpochMilli())

        val spec = QuerySpec()
                .withKeyConditionExpression("id_name = :name") //AND id_scrapingDate between :from and :to
                .withValueMap(valueMap)

        return table.query(spec).map { item ->
            Whisky(
                    item.getString("whiskyName"),
                    item.getString("description"),
                    item.getDouble("alcohol"),
                    item.getDouble("liter"),
                    BigDecimal.valueOf(item.getDouble("price")),
                    Instant.ofEpochMilli(item.getString("id_scrapingDate").toLong()),
                    item.getString("source")
            )
        }.toList()
    }
}