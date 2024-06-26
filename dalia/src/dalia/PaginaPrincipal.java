package dalia;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class PaginaPrincipal implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    	//pág principa
        String response = "¡Bienvenido   a Dalia! Por favor, visita /crear_clip para crear un clip musical.";
        
        //respuesta ok
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
