package com.example.dynamodb;

/*
Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
This file is licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License. A copy of
the License is located at
http://aws.amazon.com/apache2.0/
This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Put an item in a DynamoDB table.
 * <p>
 * Takes the name of the table, a name (primary key value) and a greeting
 * (associated with the key value).
 * <p>
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class PutItem {
    public static void main(String[] args) {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    PutItem <table> <name> [field=value ...]\n\n" +
                "Where:\n" +
                "    table    - the table to put the item in.\n" +
                "    name     - a name to add to the table. If the name already\n" +
                "               exists, its entry will be updated.\n" +
                "Additional fields can be added by appending them to the end of the\n" +
                "input.\n\n" +
                "Example:\n" +
                "    PutItem Cellists Pau Language=ca Born=1876\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String name = args[1];
        ArrayList<String[]> extra_fields = new ArrayList<>();

        // any additional args (fields to add to database)?
        for (int x = 2; x < args.length; x++) {
            String[] fields = args[x].split("=", 2);
            if (fields.length == 2) {
                extra_fields.add(fields);
            } else {
                System.out.format("Invalid argument: %s\n", args[x]);
                System.out.println(USAGE);
                System.exit(1);
            }
        }

        System.out.format("Adding \"%s\" to \"%s\"", name, table_name);
        if (extra_fields.size() > 0) {
            System.out.println("Additional fields:");
            for (String[] field : extra_fields) {
                System.out.format("  %s: %s\n", field[0], field[1]);
            }
        }

        HashMap<String, AttributeValue> item_values = new HashMap<>();

        item_values.put("Name", AttributeValue.builder().s(name).build());

        for (String[] field : extra_fields) {
            item_values.put(field[0], AttributeValue.builder().s(field[1]).build());
        }

        try {
            DynamoDbClient ddb = DynamoDbClient.builder().endpointOverride(new URI("http://localhost:8000")).build();

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(table_name)
                    .item(item_values)
                    .build();
            ddb.putItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }
}