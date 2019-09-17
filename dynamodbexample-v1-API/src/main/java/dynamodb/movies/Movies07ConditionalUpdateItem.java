package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.MOVIES_TABLE;
import static dynamodb.movies.DynamoDbUtils.displayItem;

public class Movies07ConditionalUpdateItem {

    public static void main(String[] args) {
        removeFirstActor(3);
        removeFirstActor(2);
    }

    private static void removeFirstActor(int minNumberOfActors) {
        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        System.out.format("Attempting a conditional update of movie: %s (%s)\n", BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey(YEAR, BIG_MOVIE_YEAR, TITLE, BIG_MOVIE_TITLE))
                .withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) > :num")
                .withValueMap(new ValueMap().withNumber(":num", minNumberOfActors))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = MOVIES_TABLE.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
            displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
        } catch (Exception e) {
            System.err.format("Unable to update movie %s (%d)\n", BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
            System.err.println(e.getMessage());
        }
    }
}