package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import static dynamodb.movies.DynamoDbUtils.*;

class Movies04CreateItem {

    public static void main(String[] args) {
        String title = "The Big New Movie";
        int year = 2015;
        String info = "{\"plot\" : \"Nothing happens.\"}";

        PutItemRequest putRequest = createPutItemRequest(title, year, info);

        ddb.putItem(putRequest);

        displayItem(title, year);
    }
}