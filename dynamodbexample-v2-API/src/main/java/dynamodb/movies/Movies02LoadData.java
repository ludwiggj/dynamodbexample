package dynamodb.movies;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.*;

public class Movies02LoadData {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Movies02LoadData.class.getClassLoader();
//        File file = new File(classLoader.getResource(MOVIE_DATA_SAMPLE_FILE).getFile());
        File file = new File(classLoader.getResource(MOVIE_DATA_FILE).getFile());
        JsonParser parser = new JsonFactory().createParser(file);
        JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iterator = rootNode.iterator();

        ObjectNode currentNode;
        while (iterator.hasNext()) {
            currentNode = (ObjectNode) iterator.next();

            String title = currentNode.path(TITLE).toString();
            if (title.equals("\"Prisoners\"")) {
                int rating = currentNode.path("info").path("rating").asInt();
                String plot = currentNode.path("info").path("plot").toString();

                JsonNode actorsNode = currentNode.path("info").path("actors");
                List<String> actors = new ArrayList<>();
                for (JsonNode node : actorsNode) {
                    actors.add(node.asText());
                }

                System.out.println(rating);
                System.out.println(plot);
                for (String actor : actors) {
                    System.out.println(actor);
                }
            }


            PutItemRequest request = createPutItemRequest(
                    currentNode.path(TITLE).asText(),
                    currentNode.path(YEAR).asText(),
                    currentNode.path(INFO).toString()
            );
            ddb.putItem(request);
        }

        parser.close();
    }
}

/*
    {
        "year": 2013,
        "title": "Prisoners",
        "info": {
            "directors": ["Denis Villeneuve"],
            "release_date": "2013-08-30T00:00:00Z",
            "rating": 8.2,
            "genres": [
                "Crime",
                "Drama",
                "Thriller"
            ],
            "image_url": "http://ia.media-imdb.com/images/M/MV5BMTg0NTIzMjQ1NV5BMl5BanBnXkFtZTcwNDc3MzM5OQ@@._V1_SX400_.jpg",
            "plot": "When Keller Dover's daughter and her friend go missing, he takes matters into his own hands as the police pursue multiple leads and the pressure mounts. But just how far will this desperate father go to protect his family?",
            "rank": 3,
            "running_time_secs": 9180,
            "actors": [
                "Hugh Jackman",
                "Jake Gyllenhaal",
                "Viola Davis"
            ]
        }
    },
 */