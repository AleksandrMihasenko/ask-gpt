package alex;

public record GptResponse(
        String id,
        String objct,
        int created,
        String model,
        ChatGptResponseChoice[] choices,
        ChatGptResponseUsage usage
) {

}
