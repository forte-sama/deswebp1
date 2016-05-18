package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class URLTools {
    public static String parse_content(String URL) {
        String titulo = "";

        try {
            Document doc = Jsoup.connect(URL.trim()).get();
            titulo = doc.title();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return titulo;
    }
}
