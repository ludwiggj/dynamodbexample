package dynamodb.movies;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.HashMap;
import java.util.Map;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.ddb;

public class Movies10Scan {
    public static void main(String[] args) {
        filesReleasedIn1950s();
    }

    private static void filesReleasedIn1950s() {
        System.out.format("Querying %s\n", MOVIES_TABLE_NAME);

        Map<String, String> attrNameAlias = new HashMap<>();
        attrNameAlias.put("#yr", YEAR);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":start_yr", AttributeValue.builder().n("1950").build());
        attrValues.put(":end_yr", AttributeValue.builder().n("1959").build());

        ScanRequest scanReq = ScanRequest.builder()
                .tableName(MOVIES_TABLE_NAME)
                .projectionExpression("#yr, title, info")
                .filterExpression("#yr between :start_yr and :end_yr")
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();

        try {
            ScanResponse response = ddb.scan(scanReq);
            System.out.format("Number of films released in 1950s = %d\n", response.count());

            for (Map<String, AttributeValue> itemMap : response.items()) {
                System.out.format("%s (%s) => %s\n", itemMap.get(TITLE).s(), itemMap.get(YEAR).n(), itemMap.get(INFO).s());
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}