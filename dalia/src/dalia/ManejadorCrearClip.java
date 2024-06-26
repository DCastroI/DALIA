package dalia;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ManejadorCrearClip implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configuración de CORS
        configureCORS(exchange);

        if ("POST".equals(exchange.getRequestMethod())) {
            // Obtener los parámetros de la solicitud
            String[] params = new String(exchange.getRequestBody().readAllBytes()).split("&");
            String genero = params[0].split("=")[1];
            String instrumento = params[1].split("=")[1];

            // Llamar a la API de MuseNet para generar el clip musical
            MuseNetApi musenet = new MuseNetApi();
            String clipUrl = musenet.generarClip(genero, instrumento);

            // Guardar el clip en clip.mp3
            byte[] clipData = new byte[0];
            try (InputStream in = new URL(clipUrl).openStream()) {
                clipData = in.readAllBytes();
            }
            Files.write(Paths.get("clip.mp3"), clipData);

            // Configurar la respuesta con la URL del clip en formato JSON
            String jsonResponse = new JSONObject().put("clip_url", "clip.mp3").toString();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            }
        } else {
            // Si la solicitud no es POST,  error
            String response = "Error: Esta ruta solo acepta solicitudes POST.";
            exchange.sendResponseHeaders(400, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    // Método para configurar CORS
    private void configureCORS(HttpExchange exchange) {
        // Permitir el origen  (http://localhost:3000)
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:3000");
        
        // Permitir los métodos GET y POST
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST");
        
        // Permitir los encabezados Content-Type y Authorization
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
