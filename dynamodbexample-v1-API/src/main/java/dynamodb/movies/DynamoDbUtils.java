package dynamodb.movies;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import static common.Utils.*;

public class DynamoDbUtils {
    static DynamoDB ddb;

    static {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")
                ).build();

        ddb = new DynamoDB(client);
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
//                .tableName(MOVIES_TABLE)
//                .key(itemKey(title, year))
//                .build();
//
//        return ddb.getItem(getRequest);
//    }
//
//    public static void displayItem(String title, String year) {
//        System.out.format("\nDisplaying item \"%s (%s)\"\n", title, year);
//
//        Map<String, AttributeValue> returned_item = getItem(title, year).item();
//        if (returned_item != null) {
//            Set<String> keys = returned_item.keySet();
//            // See https://github.com/aws/aws-sdk-java-v2/issues/865
//            if (keys.isEmpty()) {
//                System.out.format("No item found\n");
//            } else {
//                for (String key : keys) {
//                    System.out.format("Attr -> %s: %s\n", key, returned_item.get(key).toString());
//                }
//            }
//        } else {
//            System.out.format("No item found\n");
//        }
//    }
//
    static void insertItem(String title, int year, String info) {
        Table table = ddb.getTable(MOVIES_TABLE);
        try {
            table.putItem(
                    new Item()
                            .withPrimaryKey(YEAR, year, TITLE, title)
                            .withJSON(INFO, info)
            );
            System.out.format("Added movie: %s (%s)\n", title, year);
        } catch (Exception e) {
            System.err.format("Unable to add movie: %s (%s)\n", title, year);
            System.err.println(e.getMessage());
        }
    }
}