package alura.com.br.ChallengeLiterAlura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import alura.com.br.ChallengeLiterAlura.dto.LivroDTO;
import alura.com.br.ChallengeLiterAlura.dto.ResponseDTO;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Service
public class ConsumoApi {
    private static final String BASE_URL = "https://gutendex.com/books/?search=";

    private final HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(15))
            .followRedirects(HttpClient.Redirect.NORMAL) 
            .build();

    private final ObjectMapper mapper;

    public ConsumoApi(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<LivroDTO> buscarPrimeiroPorTitulo(String titulo) {
        String encoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        URI uri = URI.create(BASE_URL + encoded);

        HttpRequest req = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(30))
                .header("User-Agent", "LiterALura/1.0 (Java 24; Windows)")
                .header("Accept", "application/json")
                .GET()
                .build();

        int maxAttempts = 3;
        long backoffMs = 600;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
                int sc = resp.statusCode();
                if (sc >= 200 && sc < 300) {
                    ResponseDTO dto = mapper.readValue(resp.body(), ResponseDTO.class);
                    if (dto.getResults() != null && !dto.getResults().isEmpty()) {
                        return Optional.of(dto.getResults().get(0));
                    }
                    return Optional.empty();
                } else if (sc == 429 || (sc >= 500 && sc < 600)) {
                    Thread.sleep(backoffMs);
                    backoffMs *= 2;
                    continue;
                } else {
                    System.err.println("Gutendex HTTP " + sc + " - corpo: " + resp.body());
                    return Optional.empty();
                }
            } catch (java.net.http.HttpTimeoutException te) {
                System.err.println("Timeout na tentativa " + attempt + ": " + te.getMessage());
                if (attempt == maxAttempts) return Optional.empty();
                try { Thread.sleep(backoffMs); } catch (InterruptedException ignored) {}
                backoffMs *= 2;
            } catch (Exception e) {
                System.err.println("Erro chamando Gutendex (tentativa " + attempt + "): "
                        + e.getClass().getSimpleName() + " - " + e.getMessage());
                if (attempt == maxAttempts) return Optional.empty();
                try { Thread.sleep(backoffMs); } catch (InterruptedException ignored) {}
                backoffMs *= 2;
            }
        }
        return Optional.empty();
    }
}