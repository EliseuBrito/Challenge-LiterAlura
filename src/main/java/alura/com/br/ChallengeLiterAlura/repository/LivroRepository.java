package alura.com.br.ChallengeLiterAlura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import alura.com.br.ChallengeLiterAlura.model.Livro;



public interface LivroRepository extends JpaRepository<Livro, Long> {
}