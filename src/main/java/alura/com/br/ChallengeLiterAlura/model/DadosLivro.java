package alura.com.br.ChallengeLiterAlura.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("book_id") Long id,@JsonAlias("title") String titulo,@JsonAlias("year") Integer ano,@JsonAlias("authors") List<DadosAutor> autores,@JsonAlias("languages") List<String> idiomas,@JsonAlias("download_count") Double numeroDownloads) {
}
