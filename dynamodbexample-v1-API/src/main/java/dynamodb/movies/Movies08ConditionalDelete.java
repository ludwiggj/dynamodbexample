package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.MOVIES_TABLE;
import static dynamodb.movies.DynamoDbUtils.displayItem;

public class Movies08ConditionalDelete {

    public static void main(String[] args) {
        deleteMovie(5.0); // Conditional delete (we expect this to fail)
        deleteMovie(6.0); // This should pass
    }

    private static void deleteMovie(Number maxRating) {
        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        System.out.format("Attempting a conditional delete of movie: %s (%s)\n", BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(YEAR, BIG_MOVIE_YEAR, TITLE, BIG_MOVIE_TITLE))
                .withConditionExpression("info.rating <= :val")
                .withValueMap(new ValueMap().withNumber(":val", maxRating));

        try {
            MOVIES_TABLE.deleteItem(deleteItemSpec);
            System.out.format("DeleteItem succeeded");
        } catch (Exception e) {
            System.err.format("Unable to delete movie %s (%d)\n", BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
            System.err.println(e.getMessage());
        }
    }
}