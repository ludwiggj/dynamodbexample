package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.*;
import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.*;

public class Movies08DeleteItem {
    public static void main(String[] args) {
        String title = BIGIEST_MOVIE_TITLE;
        String year = BIGIEST_MOVIE_YEAR;

        System.out.println("Before:");
        displayItem(title, year);

        System.out.format("Deleting item \"%s (%s)\" from %s\n", title, year, MOVIES_TABLE);

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName(MOVIES_TABLE)
                .key(itemKey(title, year))
                .build();

        try {
            DeleteItemResponse response = ddb.deleteItem(deleteReq);
            System.out.format("Delete response code = %s\n", response.sdkHttpResponse().statusCode());
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("After:");
        displayItem(title, year);
    }
}