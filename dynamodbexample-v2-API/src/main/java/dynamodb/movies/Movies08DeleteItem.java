package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.*;

public class Movies08DeleteItem {
    public static void main(String[] args) {
        String title = DynamoDbUtils.BIGIEST_MOVIE_TITLE;
        String year = DynamoDbUtils.BIGIEST_MOVIE_YEAR;

        System.out.println("Before:");
        DynamoDbUtils.displayItem(title, year);

        System.out.format("Deleting item \"%s (%s)\" from %s\n", title, year, DynamoDbUtils.MOVIES_TABLE);

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName(DynamoDbUtils.MOVIES_TABLE)
                .key(DynamoDbUtils.itemKey(title, year))
                .build();

        try {
            DeleteItemResponse response = DynamoDbUtils.ddb.deleteItem(deleteReq);
            System.out.format("Delete response code = %s\n", response.sdkHttpResponse().statusCode());
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("After:");
        DynamoDbUtils.displayItem(title, year);
    }
}