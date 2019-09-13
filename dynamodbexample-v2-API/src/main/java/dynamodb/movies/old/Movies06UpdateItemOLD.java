package dynamodb.movies.old;

import dynamodb.movies.DynamoDbUtils;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static dynamodb.movies.DynamoDbUtils.displayItem;

public class Movies06UpdateItemOLD {

    private static final int year = 2018;
    private static final AttributeValue yearAttr = AttributeValue.builder().n(Integer.toString(year)).build();
    private static final String title = "The Newliest Bigiest Big New Movie";
    private static final AttributeValue titleAttr = AttributeValue.builder().s(title).build();

    public static void main(String[] args) {
        System.out.println("Before update");
        System.out.println();
        displayItem(title, year);

        UpdateItemRequest updateItemRequest = createUpdateItemRequest();

        DynamoDbUtils.ddb.updateItem(updateItemRequest);

        System.out.println("After update");
        System.out.println();
        displayItem(title, year);
    }

    private static UpdateItemRequest createUpdateItemRequest() {
        Map<String, AttributeValue> item_key = new HashMap<>();
        item_key.put("year", yearAttr);
        item_key.put("title", titleAttr);

        Map<String, AttributeValueUpdate> updated_values = new HashMap<>();

        updated_values.put("rating",
                AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().n("5.5").build())
                        .action(AttributeAction.PUT)
                        .build()
        );

        updated_values.put("info",
                AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s("{\"plot\" : \"Something interesting happens.\"}").build())
                        .action(AttributeAction.PUT)
                        .build()
        );

        updated_values.put("actors",
                AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().ss(Arrays.asList("Larry", "Moe", "Curly")).build())
                        .action(AttributeAction.ADD)
                        .build()
        );

        return UpdateItemRequest.builder()
                .tableName("Movies")
                .key(item_key)
                .attributeUpdates(updated_values)
                .build();
    }
}