package alex;

public record ChatGptResponseChoice(
       String text,
       int index,
       Object logprobs,
       String finish_reason
) {
}
