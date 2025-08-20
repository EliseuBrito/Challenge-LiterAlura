package alura.com.br.ChallengeLiterAlura.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alura.com.br.ChallengeLiterAlura.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("""
           SELECT DISTINCT autor FROM Autor autor
           LEFT JOIN FETCH autor.livros
           """)
    List<Autor> listarTodosComLivros();

    @Query("""
           SELECT DISTINCT autor FROM Autor autor
           LEFT JOIN FETCH autor.livros
           WHERE (autor.dataNascimento IS NULL OR autor.dataNascimento <= :ano)
             AND (autor.dataMorte IS NULL OR autor.dataMorte >= :ano)
           """)
    List<Autor> obterAutoresVivosComLivrosNoAno(Integer ano);

    @Query("""
           SELECT autor FROM Autor autor
           WHERE (autor.dataNascimento IS NULL OR autor.dataNascimento <= :ano)
             AND (autor.dataMorte IS NULL OR autor.dataMorte >= :ano)
           """)
    List<Autor> filtrarVivosPorAno(Integer ano);

    Optional<Autor> findByNomeIgnoreCase(String nome);
}
