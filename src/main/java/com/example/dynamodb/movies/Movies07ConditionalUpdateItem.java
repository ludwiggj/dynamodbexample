package com.example.dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import java.util.HashMap;
import java.util.Map;

import static com.example.dynamodb.movies.DynamoDbUtils.*;

public class Movies07ConditionalUpdateItem {

    public static void main(String[] args) {
        conditionalUpdate(BIGIEST_MOVIE_TITLE, BIGIEST_MOVIE_YEAR);
        conditionalUpdate("Prisoners", "2013");
    }

    private static void conditionalUpdate(String title, String year) {
        System.out.println("Before conditional update\n");
        displayItem(title, year);

        UpdateItemRequest updateItemRequest = createUpdateItemRequest(title, year);

        try {
            ddb.updateItem(updateItemRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("After update\n");
        displayItem(title, year);
    }

    private static UpdateItemRequest createUpdateItemRequest(String title, String year) {
        System.out.format("Updating movie: %s (%s)\n", title, year);

        HashMap<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":latestYear", AttributeValue.builder().n("2015").build());

        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#yr", "year");

        return UpdateItemRequest.builder()
                .tableName(MOVIES_TABLE)
                .key(itemKey(title, year))
                .updateExpression("ADD releasedAfter :latestYear")
                .conditionExpression("#yr > :latestYear")
                .expressionAttributeNames(attributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .build();
    }

    // TODO: Original version failed with:

    // The conditional request failed (Service: DynamoDb, Status Code: 400, Request ID: 685ecff3-d7f0-485a-9a34-f7342ad0bcb4)

//                .updateExpression("remove info.actors[0]")
//                .conditionExpression("size(info.actors) > :num")

    // It was based on https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.03.html#GettingStarted.Java.03.05

    // Assuming the original version (based on v1 of API) worked(!), it might be to do with how the JSON is being
    // represented in the updated version of the library code

    // Related links:

    // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ConditionExpressions.html
    // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.UpdateExpressions.html
}