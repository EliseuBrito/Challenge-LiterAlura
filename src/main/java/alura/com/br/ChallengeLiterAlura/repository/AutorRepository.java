package alura.com.br.ChallengeLiterAlura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import alura.com.br.ChallengeLiterAlura.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}