package dynamodb.movies;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.io.File;
import java.util.Iterator;

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