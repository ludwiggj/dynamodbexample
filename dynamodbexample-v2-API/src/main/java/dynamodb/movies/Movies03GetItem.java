package dynamodb.movies;

import static common.Utils.*;

class Movies03GetItem {
    public static void main(String[] args) {
        DynamoDbUtils.displayItem("Prisoners" ,"2013");
        DynamoDbUtils.displayItem(BIGIEST_MOVIE_TITLE, BIGIEST_MOVIE_YEAR);
        DynamoDbUtils.displayItem("Planet of the Apes", "1970");
    }
}