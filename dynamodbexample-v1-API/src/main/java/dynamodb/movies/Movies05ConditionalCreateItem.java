package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;

import java.util.HashMap;
import java.util.Map;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.*;

public class Movies05ConditionalCreateItem {

    public static void main(String[] args) {
        String title = BIGIEST_MOVIE_TITLE;
        int year = BIGIEST_MOVIE_YEAR;

        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("plot", "Something exciting happens.");
        infoMap.put("rating", 7.5);

        displayItem(title, year);

        Item item = new Item()
                .withPrimaryKey(YEAR, year, TITLE, title)
                .withMap(INFO, infoMap);

        PutItemSpec putItemSpec = new PutItemSpec()
                .withItem(item)
                .withConditionExpression("attribute_not_exists(#yr) and attribute_not_exists(title)")
                .withNameMap(new NameMap().with("#yr", "year"));

        try {
            MOVIES_TABLE.putItem(putItemSpec);
            System.out.format("Added movie: %s (%s)\n", title, year);
        } catch (Exception e) {
            System.err.format("Unable to add movie: %s (%s)\n", title, year);
            System.err.println(e.getMessage());
        }

        displayItem(title, year);
    }
}