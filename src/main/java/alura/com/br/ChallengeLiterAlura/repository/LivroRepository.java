package alura.com.br.ChallengeLiterAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alura.com.br.ChallengeLiterAlura.model.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("""
           SELECT livro FROM Livro livro
           LEFT JOIN FETCH livro.autor
           """)
    List<Livro> buscarTodosComAutor();

    @Query("""
           SELECT livro FROM Livro livro
           LEFT JOIN FETCH livro.autor
           WHERE UPPER(livro.idioma) = UPPER(:idioma)
           """)
    List<Livro> buscarPorIdiomaComAutor(String idioma);

    Optional<Livro> findByTituloIgnoreCase(String titulo);
}
