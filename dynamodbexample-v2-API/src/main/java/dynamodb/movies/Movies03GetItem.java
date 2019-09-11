package dynamodb.movies;

class Movies03GetItem {
    public static void main(String[] args) {
        DynamoDbUtils.displayItem("Prisoners" ,"2013");
        DynamoDbUtils.displayItem(DynamoDbUtils.BIGIEST_MOVIE_TITLE, DynamoDbUtils.BIGIEST_MOVIE_YEAR);
        DynamoDbUtils.displayItem("Planet of the Apes", "1970");
    }
}