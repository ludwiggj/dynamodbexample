package com.example.dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import static com.example.dynamodb.movies.DynamoDbUtils.*;

class Movies04CreateItem {

    public static void main(String[] args) {
        String title = "The Big New Movie";
        String year = "2015";
        String info = "{\"plot\" : \"Nothing happens.\"}";

        PutItemRequest putRequest = createPutItemRequest(title, year, info);

        ddb.putItem(putRequest);

        displayItem(title, year);
    }
}