package br.com.alura.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.errors.ApiException;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;

/**
 * Classe de serviço para interagir com a API do Gemini para tradução de texto.
 * Utiliza a interface 'client.models' diretamente para geração de conteúdo.
 *
 * NOTA: Esta implementação combina a chamada simplificada (String + null)
 * com a inicialização estática do Client para otimizar a performance.
 */
public class ConsultaGemini {

    // Cliente estático para gerenciar a API Key e a comunicação.
    // O Cliente é criado APENAS UMA VEZ quando a classe é carregada.
    private static final Client client;

    // A chave é definida aqui. Use a variável de ambiente GOOGLE_API_KEY se preferir.
    private static final String API_KEY = System.getenv("GEMINI_APIKEY");

    // Bloco estático para inicializar o Client apenas uma vez
    static {
        try {
            // Usa o builder com a chave diretamente (forma que funcionou para você).
            // Em projetos maiores, use 'new Client()' e configure a variável de ambiente GOOGLE_API_KEY.
            client = Client.builder().apiKey(API_KEY).build();
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao inicializar o cliente Gemini. Chave inválida ou problema de conexão.");
            throw new RuntimeException("Falha na inicialização do Gemini Client.", e);
        }
    }

    /**
     * Obtém a tradução do texto fornecido para o português usando o modelo Gemini.
     * @param texto O texto a ser traduzido.
     * @return A tradução do texto obtida do Gemini.
     */
    public static String obterTraducao(String texto) {

        // Prompt simplificado para garantir apenas a tradução.
        String prompt = "Apenas traduza, sem me dizer nada alem disso, o seguinte trecho: " + texto;

        try {
            // Chamada simplificada com 3 argumentos: modelo, prompt (String), e config (null).
            // Usamos o modelo mais recente: gemini-2.5-flash.
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null // Configuração de geração (GenerationConfig), passamos null para usar a default.
            );

            // O resultado é obtido diretamente
            return response.text();

        } catch (ApiException e) {
            // Tratamento de erros de comunicação ou API
            System.err.println("Erro ao se comunicar com a API do Gemini: " + e.getMessage());
            return "Erro: Falha ao obter tradução. Detalhes: " + e.getMessage();
        }
    }
}