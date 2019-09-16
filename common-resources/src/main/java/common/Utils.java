package common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.File;
import java.io.IOException;

public class Utils {
    public static final String MOVIES_TABLE_NAME = "Movies";
    public static final String MOVIE_DATA_FILE = "moviedata.json";
    public static final String MOVIE_DATA_SAMPLE_FILE = "moviedataSample.json";
    public static final String YEAR = "year";
    public static final String TITLE = "title";
    public static final String INFO = "info";

    public static final String BIG_MOVIE_TITLE = "The Big New Movie";
    public static final int BIG_MOVIE_YEAR = 2015;

    public static final String BIGIEST_MOVIE_TITLE = "The Newliest Bigiest Big New Movie";
    public static final int BIGIEST_MOVIE_YEAR = 2018;

    public static JsonParser parseJsonFile(String fileName) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return new JsonFactory().createParser(file);
    }
}