package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;
import java.util.Map;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.ddb;

public class Movies09Query {
    public static void main(String[] args) {
        filmsReleasedIn1985();
        filesReleasedIn1992BeginningAtoL();
    }

    private static void filmsReleasedIn1985() {
        String year = "1985";

        System.out.format("Querying %s\n", MOVIES_TABLE);

        Map<String, String> attrNameAlias = new HashMap<>();
        attrNameAlias.put("#yr", YEAR);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":" + YEAR, AttributeValue.builder().n(year).build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName(MOVIES_TABLE)
                .keyConditionExpression("#yr" + " = :" + YEAR)
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();

        try {
            QueryResponse response = ddb.query(queryReq);
            System.out.format("Number of films released in %s = %d\n", year, response.count());

            for (Map<String, AttributeValue> itemMap : response.items()) {
                System.out.format("%s (%s) => %s\n", itemMap.get(TITLE).s(), itemMap.get(YEAR).n(), itemMap);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void filesReleasedIn1992BeginningAtoL() {
        String year = "1992";

        System.out.format("Querying %s\n", MOVIES_TABLE);

        Map<String, String> attrNameAlias = new HashMap<>();
        attrNameAlias.put("#yr", YEAR);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":" + YEAR, AttributeValue.builder().n(year).build());
        attrValues.put(":letter1", AttributeValue.builder().s("A").build());
        attrValues.put(":letter2", AttributeValue.builder().s("L").build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName(MOVIES_TABLE)
                // TODO - This doesn't work
                //.projectionExpression("#yr, title, info.genres, info.actors")
                .projectionExpression("#yr, title, info")
                .keyConditionExpression("#yr" + " = :" + YEAR + " and title between :letter1 and :letter2")
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();

        try {
            QueryResponse response = ddb.query(queryReq);
            System.out.format("Number of films released in %s = %d\n", year, response.count());

            for (Map<String, AttributeValue> itemMap : response.items()) {
                System.out.format("%s (%s) => %s\n", itemMap.get(TITLE).s(), itemMap.get(YEAR).n(), itemMap.get(INFO).s());
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}