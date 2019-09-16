package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.*;

class Movies04CreateItem {

    public static void main(String[] args) {
        String info = "{\"plot\" : \"Nothing happens.\"}";

        PutItemRequest putRequest = createPutItemRequest(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR, info);

        ddb.putItem(putRequest);

        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
    }
}