package com.example.dynamodb.movies;

import static com.example.dynamodb.movies.DynamoDbUtils.*;

class Movies03GetItem {
    public static void main(String[] args) {
        displayItem("Prisoners" ,"2013");
        displayItem(BIGIEST_MOVIE_TITLE, BIGIEST_MOVIE_YEAR);
        displayItem("Planet of the Apes", "1970");
    }
}