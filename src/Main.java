import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner input = new Scanner(System.in);
        HttpClient cliente = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String apiKey = "c92dca1df3437d3fbb886f93";




        while (true) {
            System.out.println("**************************************************************" +
                    "\n " +
                    "\n1.- Peso Chileno (CLP) a Dolar (USD)." +
                    "\n2.- Dolar (USD) a Peso Chileno (CLP)." +
                    "\n3.- Peso Chileno (CLP) a Peso Argentino (ARG)." +
                    "\n4.- Peso Argentino (ARG) a Peso Chileno (CLP)." +
                    "\n5.- Salir" +
                    "\n " +
                    "\n**************************************************************");

            String opcion = input.nextLine();
            switch (opcion) {
                case "1":
                    convertir(cliente, apiKey, "CLP", "USD");
                    break;
                case "2":
                    convertir(cliente, apiKey, "USD", "CLP");
                    break;
                case "3":
                    convertir(cliente, apiKey, "CLP", "ARS");
                    break;
                case "4":
                    convertir(cliente, apiKey, "ARS", "CLP");
                    break;
                case "5":
                    System.out.println("Saliendo...");
                    input.close();
                    return; // Exit the program
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    private static void convertir(HttpClient cliente, String apiKey, String base, String target) throws IOException, InterruptedException {
        String link = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + target;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        ExchangeRateResponse exchangeRateResponse = new Gson().fromJson(response.body(), ExchangeRateResponse.class);
        System.out.println("Un " + base + " vale: " + exchangeRateResponse.getConversion_rate() +" "+ target);
    }
    private static class ExchangeRateResponse {
        private String result;
        private double conversion_rate;
        public double getConversion_rate() {
            return conversion_rate;
        }
    }
}