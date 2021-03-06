package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.Utils.*;

public class DynamoDbUtils {
    public static DynamoDbClient ddb;

    static {
        try {
            ddb = DynamoDbClient.builder().endpointOverride(new URI("http://localhost:8000")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static Map<String, AttributeValue> itemKey(String title, int year) {
        Map<String, AttributeValue> getAttributes = new HashMap<>();
        getAttributes.put("year", AttributeValue.builder().n(Integer.toString(year)).build());
        getAttributes.put("title", AttributeValue.builder().s(title).build());
        return getAttributes;
    }

    private static GetItemResponse getItem(String title, int year) {
        GetItemRequest getRequest = GetItemRequest.builder()
                .tableName(MOVIES_TABLE_NAME)
                .key(itemKey(title, year))
                .build();

        return ddb.getItem(getRequest);
    }

    public static void displayItem(String title, int year) {
        System.out.format("\nDisplaying item \"%s (%s)\"\n", title, year);

        Map<String, AttributeValue> returned_item = getItem(title, year).item();
        if (returned_item != null) {
            Set<String> keys = returned_item.keySet();
            // See https://github.com/aws/aws-sdk-java-v2/issues/865
            if (keys.isEmpty()) {
                System.out.format("No item found\n");
            } else {
                for (String key : keys) {
                    System.out.format("Attr -> %s: %s\n", key, returned_item.get(key).toString());
                }
            }
        } else {
            System.out.format("No item found\n");
        }
    }

    static PutItemRequest createPutItemRequest(String title, int year, String info) {
        return createPutItemRequestBuilder(title, year, info).build();
    }

    static PutItemRequest.Builder createPutItemRequestBuilder(String title, int year, String info) {
        System.out.format("Adding movie: %s (%s)\n", title, year);

        HashMap<String, AttributeValue> item_values = new HashMap<>();

        item_values.put(YEAR, AttributeValue.builder().n(Integer.toString(year)).build());
        item_values.put(TITLE, AttributeValue.builder().s(title).build());
        item_values.put(INFO, AttributeValue.builder().s(info).build());

        return PutItemRequest.builder()
                .tableName(MOVIES_TABLE_NAME)
                .item(item_values);
    }
}