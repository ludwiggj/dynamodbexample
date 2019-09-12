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
//                System.out.format("%s (%s) => %s\n", itemMap.get(TITLE).s(), itemMap.get(YEAR).n(), itemMap.get(INFO).s());
                System.out.format("%s (%s) => %s\n", itemMap.get(TITLE).s(), itemMap.get(YEAR).n(), itemMap.get(INFO).m());
            }

            /*

            {
  year=AttributeValue(N=1985),
  title=AttributeValue(S=A Nightmare on Elm Street Part 2: Freddy's Revenge),
  info=AttributeValue(M=
    {
      actors=AttributeValue(L=[AttributeValue(S=Robert Englund), AttributeValue(S=Mark Patton), AttributeValue(S=Kim Myers)]),
      release_date=AttributeValue(S=1985-10-01T00:00:00Z),
      plot=AttributeValue(S=A teenage boy is haunted in his dreams by Freddy Krueger who is out to possess him to continue his murdering in the real world.),
      genres=AttributeValue(L=[AttributeValue(S=Horror)]),
      image_url=AttributeValue(S=http://ia.media-imdb.com/images/M/MV5BMjA4OTA4NTA4MV5BMl5BanBnXkFtZTYwNTI2MjU5._V1_SX400_.jpg),
      directors=AttributeValue(L=[AttributeValue(S=Jack Sholder)]),
      rating=AttributeValue(N=5.1),
      rank=AttributeValue(N=4008),
      running_time_secs=AttributeValue(N=5220)
    }
  )
}

             */

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}