package br.com.alura.screenmatch.service;

import com.openai.OpenAI;
import com.openai.chat.*;
import com.openai.models.chat.ChatCompletionChoice;
import com.openai.models.chat.ChatMessage;
import java.util.List;

public class ConsultaChatGPT {

    private final OpenAI client;

    public ConsultaChatGPT(String apiKey) {
        client = new OpenAI(apiKey);
    }

    public String obterTraducao(String texto) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(ChatMessage.ofUser("Traduza para o português: " + texto)))
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        List<ChatCompletionChoice> choices = client.chatCompletions().create(request).getChoices();

        return choices.get(0).getMessage().getContent();
    }
}
