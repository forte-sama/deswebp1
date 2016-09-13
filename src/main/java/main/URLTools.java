package main;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.MalformedURLException;

public class URLTools {
    /*
        Parsea ciertas etiquedas del HTML generado por un URL
        Recibe:  String -> URL a parsear
        Retorna: String -> Texto con informacion del parseo realizado
    */
    public static String parse_content(String URL) {
        //mensaje a retornar
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
        catch (IllegalArgumentException e) {
            System.out.println("URL invalido... Formato URL : protocolo://direccion/ruta_recurso");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido algun imprevisto... Por favor intentalo de nuevo");
        }

        return resp.toString();
    }

    /*
        Cuenta lineas del HTML generado por un URL
        Recibe:  Document -> Objeto Document generado por URL
        Retorna: long     -> Numero de lineas
    */
    private static long contar_lineas(Document doc) {
        //html generado -> caracters -> filtrar solo '\n' -> conteo + 1
        return doc.html().chars().filter(c -> c == '\n').count() + 1;
    }

    /*
        Parsea elementos tipo input contenidos en cada formulario de la lista Elements
        Recibe:  Elements -> Lista de objetos con tag form de un documento
        Retorna: String   -> Mensaje con informacion de los inputs de los formularios encontrados
    */
    private static String parse_forms(Elements forms) {
        //mensaje a retornar
        StringBuilder resp = new StringBuilder("");

        //iterar sobre cada formulario (for tradicional para identificar con indice cada formulario)
        for(int i=0; i<forms.size(); i++) {
            Element form = forms.get(i);
            resp.append("Formulario #" + (i+1) + ":\n");

            //obtener elementos input de cada formulario
            Elements inputs = form.select("input");

            //construir mensaje con informacion de los inputs seleccionados
            for(Element input : inputs) {
                resp.append("\t" + input.attr("name") + " : " + input.attr("type") + "\n");
            }
        }

        return resp.toString();
    }
}
