package dalia;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MuseNetApi {
    private static final String API_URL = "https://api.openai.com/v1/musenet";
    private static final String API_KEY = "sk-proj-Yxz6weGo956ojENXvjZ0T3BlbkFJ5WeqeuFuyJlM21S1xf9a"; 

    public String generarClip(String genero, String instrumento) throws IOException {
    	
        URL url = new URL(API_URL);
        
        //abrir conex con musenet
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        //config solicitud http
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setDoOutput(true);

        //json con param
        JSONObject json = new JSONObject();
        json.put("genero", genero);
        json.put("instrumento", instrumento);
        json.put("duracion", 10); // Duración del clip en segundos
        
        
        //enviar el json como body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        	//leer respuesta api
        try (InputStream is = connection.getInputStream();
             Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject responseJson = new JSONObject(responseBody);
            return responseJson.getString("clip_url");
        }
    }
}
