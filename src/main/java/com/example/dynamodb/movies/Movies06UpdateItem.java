package com.example.dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.dynamodb.movies.DynamoDbUtils.*;

public class Movies06UpdateItem {

    public static void main(String[] args) {
        String title = BIGIEST_MOVIE_TITLE;
        String year = BIGIEST_MOVIE_YEAR;

        System.out.println("Before update");
        displayItem(title, year);

        UpdateItemRequest updateItemRequest = createUpdateItemRequest(title, year);

        ddb.updateItem(updateItemRequest);

        System.out.println("After update");
        displayItem(title, year);
    }

    private static UpdateItemRequest createUpdateItemRequest(String title, String year) {
        System.out.format("Updating movie: %s (%s)\n", title, year);

        Map<String, AttributeValueUpdate> updated_values = new HashMap<>();

        updated_values.put("info",
                AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(
                                "{" +
                                "\"rating\": 5.5, " +
                                "\"plot\": \"Something interesting and funny happens.\", " +
                                "\"actors\": [\"Larry\", \"Moe\", \"Curly\"]" +
                                "}"
                        ).build())
                        .action(AttributeAction.PUT)
                        .build()
        );

        return UpdateItemRequest.builder()
                .tableName(MOVIES_TABLE)
                .key(itemKey(title, year))
                .attributeUpdates(updated_values)
                .build();
    }
}