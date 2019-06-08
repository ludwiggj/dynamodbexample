package com.example.dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import java.util.HashMap;
import java.util.Map;

import static com.example.dynamodb.movies.DynamoDbUtils.*;

public class Movies05ConditionalCreateItem {
    public static void main(String[] args) {
        String info = "{\"rating\": 2.5, \"plot\": \"Something happens.\"}";

        PutItemRequest putRequest = createPutItemRequest(BIGIEST_MOVIE_TITLE, BIGIEST_MOVIE_YEAR, info);

        try {
            ddb.putItem(putRequest);

            displayItem(BIGIEST_MOVIE_TITLE, BIGIEST_MOVIE_YEAR);
        } catch (ConditionalCheckFailedException e) {
            e.printStackTrace(System.err);
            System.out.println("PutItem failed");
        }
    }

    // NOTE: Cannot treat year in the same way as title when building up the condition expression, as year is a
    // reserved keyword. Results on exception:
    //
    // Exception in thread "main" software.amazon.awssdk.services.dynamodb.model.DynamoDbException:
    // Invalid ConditionExpression: Attribute name is a reserved keyword; reserved keyword: year (Service: DynamoDb,
    // Status Code: 400, Request ID: 0b9a5908-7ab6-4215-bd05-229711ba0d1a)
    //
    // See: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ReservedWords.html
    private static PutItemRequest createPutItemRequest(String title, String year, String info) {
        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#yr", "year");

        return DynamoDbUtils.createPutItemRequestBuilder(title, year, info)
                .conditionExpression("attribute_not_exists(#yr) and attribute_not_exists(title)")
                .expressionAttributeNames(attributeNames)
                .build();
    }
}