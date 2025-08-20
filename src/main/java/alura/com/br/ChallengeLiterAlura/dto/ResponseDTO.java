package alura.com.br.ChallengeLiterAlura.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO {
    private Integer count;
    private List<LivroDTO> results;

    public Integer getCount() { return count; }
    public List<LivroDTO> getResults() { return results; }
}
