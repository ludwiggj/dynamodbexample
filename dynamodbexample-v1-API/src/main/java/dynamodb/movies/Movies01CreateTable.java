package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

import static common.Utils.MOVIES_TABLE_NAME;
import static dynamodb.movies.DynamoDbUtils.ddb;

public class Movies01CreateTable {

    public static void main(String[] args) {
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = ddb.createTable(MOVIES_TABLE_NAME,
                    Arrays.asList(
                            new KeySchemaElement("year", KeyType.HASH),  // Partition key
                            new KeySchemaElement("title", KeyType.RANGE) // Sort key
                    ),
                    Arrays.asList(
                            new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)
                    ),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success. Table status: " + table.getDescription().getTableStatus());
        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }
}