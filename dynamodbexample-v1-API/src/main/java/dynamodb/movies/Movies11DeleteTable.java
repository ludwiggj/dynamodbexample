package dynamodb.movies;

import static dynamodb.movies.DynamoDbUtils.MOVIES_TABLE;

public class Movies11DeleteTable {

    public static void main(String[] args) {

        try {
            System.out.println("Attempting to delete table; please wait...");
            MOVIES_TABLE.delete();
            MOVIES_TABLE.waitForDelete();
            System.out.print("Success.");
        } catch (Exception e) {
            System.err.println("Unable to delete table: ");
            System.err.println(e.getMessage());
        }
    }
}