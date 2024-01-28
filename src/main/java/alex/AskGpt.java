package alex;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AskGpt {

    public static void main(String[] args) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String prompt;

        if (args.length > 0) {
            prompt = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a string to search for: ");
            prompt = scanner.nextLine();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        GptRequest gptRequest = new GptRequest("gpt-3.5-turbo-1106", prompt, 1);

        String input = objectMapper.writeValueAsString(gptRequest);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + dotenv.get("API_KEY"))
            .POST(HttpRequest.BodyPublishers.ofString(input))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            GptResponse gptResponse = objectMapper.readValue(response.body(), GptResponse.class);
            String answer = gptResponse.choices()[gptResponse.choices().length - 1].text();

            if (!answer.isEmpty()) {
                System.out.println(answer.replace("\n", "").trim());
            }
        } else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
    }

}
