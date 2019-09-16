package dynamodb.movies;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Utils;

import java.util.Iterator;

import static common.Utils.*;
import static dynamodb.movies.DynamoDbUtils.insertItem;

public class Movies02LoadData {

    public static void main(String[] args) throws Exception {
//        JsonParser parser = Utils.parseJsonFile(MOVIE_DATA_FILE);
        JsonParser parser = Utils.parseJsonFile(MOVIE_DATA_SAMPLE_FILE);
        JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iter = rootNode.iterator();

        ObjectNode currentNode;

        while (iter.hasNext()) {
            currentNode = (ObjectNode) iter.next();
            String title = currentNode.path(TITLE).asText();
            int year = currentNode.path(YEAR).asInt();
            String info = currentNode.path(INFO).toString();
            insertItem(title, year, info);
        }
        parser.close();
    }
}