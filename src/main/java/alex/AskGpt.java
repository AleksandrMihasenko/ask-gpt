package alex;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AskGpt {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to search for: ");
        String searchString = scanner.nextLine();

        String input = """
            {
              "model": "gpt-3.5-turbo-1106",
              "prompt": "%s",
              "temperature": 0.7
            }
            """.formatted(searchString);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer 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")
            .POST(HttpRequest.BodyPublishers.ofString(input))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

}
