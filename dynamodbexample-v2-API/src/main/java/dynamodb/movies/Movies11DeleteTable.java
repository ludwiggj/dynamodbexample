package dynamodb.movies;

/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableResponse;

import static common.Utils.MOVIES_TABLE_NAME;
import static dynamodb.movies.DynamoDbUtils.ddb;


public class Movies11DeleteTable {

    public static void main(String[] args) {
        DeleteTableRequest request = DeleteTableRequest.builder().tableName(MOVIES_TABLE_NAME).build();
        DeleteTableResponse response = ddb.deleteTable(request);
        System.out.format("Table %s deleted!", response.tableDescription().tableName());
    }
}