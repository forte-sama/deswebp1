package main;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;

public class URLTools {
    public static String parse_content(String URL) {
        StringBuilder resp = new StringBuilder("");

        try {
            //Recurso HTML generado por el URL
            Document doc = Jsoup.connect(URL.trim()).get();

            resp.append("Numero de lineas: " + contar_lineas(doc)        + "\n");
            resp.append("Parrafos:         " + doc.select("p").size()    + "\n");
            resp.append("Imagenes:         " + doc.select("img").size()  + "\n");
            resp.append(parse_forms(doc.select("form")));
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

    private static String parse_forms(Elements forms) {

        StringBuilder resp = new StringBuilder("");

        for(int i=0; i<forms.size(); i++) {
            Element form = forms.get(i);
            resp.append("Formulario #" + i + ":\n");

            Elements inputs = form.select("input");

            for(Element input : inputs) {
                resp.append("\t" + input.attr("name") + " : " + input.attr("type") + "\n");
            }
        }

        return resp.toString();
    }
}
