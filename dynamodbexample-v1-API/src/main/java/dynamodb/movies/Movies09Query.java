package dynamodb.movies;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import static dynamodb.movies.DynamoDbUtils.MOVIES_TABLE;

public class Movies09Query {

    public static void main(String[] args) {
        Table table = MOVIES_TABLE;

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#yr = :yyyy")
                .withNameMap(new NameMap().with("#yr", "year"))
                .withValueMap(new ValueMap().withNumber(":yyyy", 1985));

        try {
            System.out.println("Movies from 1985");

            for (Item item : table.query(querySpec)) {
                System.out.println(item.getNumber("year") + ": " + item.getString("title"));
            }
        } catch (Exception e) {
            System.err.println("Unable to query movies from 1985");
            System.err.println(e.getMessage());
        }

        querySpec = new QuerySpec()
                .withProjectionExpression("#yr, title, info.genres, info.actors[0]")
                .withKeyConditionExpression("#yr = :yyyy and title between :letter1 and :letter2")
                .withNameMap(new NameMap().with("#yr", "year"))
                .withValueMap(new ValueMap().withNumber(":yyyy", 1992).with(":letter1", "A").with(":letter2", "L"));

        try {
            System.out.println("Movies from 1992 - titles A-L, with genres and lead actor");

            for (Item item : table.query(querySpec)) {
                System.out.println(item.getNumber("year") + ": " + item.getString("title") + " " + item.getMap("info"));
            }
        } catch (Exception e) {
            System.err.println("Unable to query movies from 1992:");
            System.err.println(e.getMessage());
        }
    }
}