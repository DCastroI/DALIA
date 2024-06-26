package dalia; 
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class DaliaPlugin {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Manejador para la página principal
        server.createContext("/", new PaginaPrincipal());
        
        // Manejador para la creación de clips
        server.createContext("/crear_clip", new ManejadorCrearClip());
        
        server.setExecutor(null); // Usar el ejecutor por defecto
        server.start();
        
        System.out.println("Servidor HTTP iniciado en el puerto 8000");
    }

}
