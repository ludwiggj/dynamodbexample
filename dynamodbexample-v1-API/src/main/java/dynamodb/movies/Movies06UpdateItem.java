package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.Arrays;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.*;

public class Movies06UpdateItem {
    public static void main(String[] args) {
        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        System.out.format("Updating movie: %s (%s)\n", BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(YEAR, BIG_MOVIE_YEAR, TITLE, BIG_MOVIE_TITLE)
                .withUpdateExpression("set info.rating = :r, info.plot=:p, info.actors=:a")
                .withValueMap(new ValueMap()
                        .withNumber(":r", 5.5)
                        .withString(":p", "Everything happens all at once.")
                        .withList(":a", Arrays.asList("Larry", "Moe", "Curly"))
                )
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = MOVIES_TABLE.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
        } catch (Exception e) {
            System.err.println("Unable to update item: " + BIG_MOVIE_TITLE + " " + BIG_MOVIE_YEAR);
            System.err.println(e.getMessage());
        }

        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
    }
}