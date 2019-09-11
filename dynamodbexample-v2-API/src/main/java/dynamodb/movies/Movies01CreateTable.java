package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.*;
import static dynamodb.movies.DynamoDbUtils.*;

public class Movies01CreateTable {

    public static void main(String[] args) {
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName(YEAR)
                                .attributeType(ScalarAttributeType.N)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName(TITLE)
                                .attributeType(ScalarAttributeType.S)
                                .build()
                )
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName(YEAR)
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName(TITLE)
                                .keyType(KeyType.RANGE)
                                .build()
                )
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L)
                        .writeCapacityUnits(10L)
                        .build())
                .tableName(MOVIES_TABLE)
                .build();

        CreateTableResponse response = DynamoDbUtils.ddb.createTable(request);
        System.out.format("Table %s created!", response.tableDescription().tableName());
    }
}