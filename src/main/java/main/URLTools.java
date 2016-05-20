package main;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;

public class URLTools {
    public static String parse_content(String URL) {
        StringBuilder resp = new StringBuilder("");

        try {
            //Recurso HTML generado por el URL
            Document doc = Jsoup.connect(URL.trim()).get();

            resp.append("# lineas:    " + contar_lineas(doc)        + "\n");
            resp.append("Parrafos:    " + doc.select("p").size()    + "\n");
            resp.append("Imagenes:    " + doc.select("img").size()  + "\n");
            resp.append("Formularios: " + doc.select("form").size() + "\n");
        }
        catch (SSLHandshakeException e) {
            System.out.println("No se pudo crear SSL handshake...");
            System.out.println("Intenta con otro URL... HTTP por favor...");
        }
        catch (HttpStatusException e) {
            System.out.println("El recurso ha retornado con un estado erroneo... Por favor intenta con otra cosa");
        }
        catch (IOException e) {
            System.out.println("Ha ocurrido algun imprevisto... Por favor intentalo de nuevo");
        }

        return resp.toString();
    }

    private static long contar_lineas(Document doc) {
        //html generado -> caracters -> filtrar solo '\n' -> conteo + 1
        return doc.html().chars().filter(c -> c == '\n').count() + 1;
    }
}
