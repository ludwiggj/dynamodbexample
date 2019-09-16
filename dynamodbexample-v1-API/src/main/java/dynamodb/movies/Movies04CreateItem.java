package dynamodb.movies;

import java.util.HashMap;
import java.util.Map;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.displayItem;
import static dynamodb.movies.DynamoDbUtils.insertItem;

public class Movies04CreateItem {

    public static void main(String[] args) {
        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("plot", "Nothing happens at all.");
        infoMap.put("rating", 0);

        insertItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR, infoMap);

        displayItem(BIG_MOVIE_TITLE, BIG_MOVIE_YEAR);
    }
}