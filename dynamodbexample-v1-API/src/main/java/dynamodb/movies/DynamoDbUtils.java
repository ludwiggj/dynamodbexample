package dynamodb.movies;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.Map;

import static common.Utils.*;

public class DynamoDbUtils {
    static final Table MOVIES_TABLE;
    static final DynamoDB ddb;

    static {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")
                ).build();

        ddb = new DynamoDB(client);

        MOVIES_TABLE = ddb.getTable(MOVIES_TABLE_NAME);
    }

    //    static Map<String, AttributeValue> itemKey(String title, String year) {
//        Map<String, AttributeValue> getAttributes = new HashMap<>();
//        getAttributes.put("year", AttributeValue.builder().n(year).build());
//        getAttributes.put("title", AttributeValue.builder().s(title).build());
//        return getAttributes;
//    }
//
//    private static GetItemResponse getItem(String title, String year) {
//        GetItemRequest getRequest = GetItemRequest.builder()
//                .tableName(MOVIES_TABLE_NAME)
//                .key(itemKey(title, year))
//                .build();
//
//        return ddb.getItem(getRequest);
//    }
//
    static void displayItem(String title, int year) {
        System.out.format("\nDisplaying item \"%s (%s)\"\n", title, year);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(YEAR, year, TITLE, title);

        try {
            Item outcome = MOVIES_TABLE.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);
        } catch (Exception e) {
            System.out.format("No item found\n");
            System.err.println(e.getMessage());
        }
    }

    static void insertItem(String title, int year, String info) {
        try {
            MOVIES_TABLE.putItem(new Item()
                    .withPrimaryKey(YEAR, year, TITLE, title)
                    .withJSON(INFO, info)
            );
            System.out.format("Added movie: %s (%s)\n", title, year);
        } catch (Exception e) {
            System.err.format("Unable to add movie: %s (%s)\n", title, year);
            System.err.println(e.getMessage());
        }
    }

    static void insertItem(String title, int year, Map<String, Object> info) {
        try {
            MOVIES_TABLE.putItem(new Item()
                    .withPrimaryKey(YEAR, year, TITLE, title)
                    .withMap(INFO, info)
            );
            System.out.format("Added movie: %s (%s)\n", title, year);
        } catch (Exception e) {
            System.err.format("Unable to add movie: %s (%s)\n", title, year);
            System.err.println(e.getMessage());
        }
    }
}