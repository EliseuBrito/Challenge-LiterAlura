package alura.com.br.ChallengeLiterAlura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import alura.com.br.ChallengeLiterAlura.dto.AutorDTO;
import alura.com.br.ChallengeLiterAlura.dto.LivroDTO;
import alura.com.br.ChallengeLiterAlura.model.Autor;
import alura.com.br.ChallengeLiterAlura.model.Livro;
import alura.com.br.ChallengeLiterAlura.repository.AutorRepository;
import alura.com.br.ChallengeLiterAlura.repository.LivroRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CatalogoService {

    private final ConsumoApi gutendex;
    private final AutorRepository autorRepo;
    private final LivroRepository livroRepo;

    public CatalogoService(ConsumoApi gutendex, AutorRepository autorRepo, LivroRepository livroRepo) {
        this.gutendex = gutendex;
        this.autorRepo = autorRepo;
        this.livroRepo = livroRepo;
    }

    @Transactional
    public Optional<Livro> buscarESalvarPorTitulo(String titulo) {
        Optional<LivroDTO> opt = gutendex.buscarPrimeiroPorTitulo(titulo);
        if (opt.isEmpty()) return Optional.empty();

        LivroDTO b = opt.get();

        String idioma = (b.getLanguages() != null && !b.getLanguages().isEmpty())
                ? b.getLanguages().get(0)
                : "XX";
        idioma = idioma == null ? "XX" : idioma.toUpperCase(Locale.ROOT);

        Long downloads = (long) (b.getDownload_count() == null ? 0 : b.getDownload_count());

        AutorDTO a = (b.getAuthors() != null && !b.getAuthors().isEmpty())
                ? b.getAuthors().get(0)
                : null;

        Autor autor = null;
        if (a != null) {
            String nomeAutor = a.getName();
            Integer nasc = a.getBirth_year();
            Integer falec = a.getDeath_year();

            autor = autorRepo.findByNomeIgnoreCase(nomeAutor)
                    .orElseGet(() -> autorRepo.save(new Autor(nomeAutor, nasc, falec)));
        }

        Optional<Livro> existente = livroRepo.findByTituloIgnoreCase(b.getTitle());
        if (existente.isPresent()) {
        
            Livro l = existente.get();
            l.setIdioma(idioma);
            l.setquantidadeDownloads(downloads);
            if (autor != null) l.setAutor(autor);
            return Optional.of(l);
        }

        Long quantidadeDownloads = null;
        Livro novo = new Livro(b.getTitle(), idioma, quantidadeDownloads);
        if (autor != null) novo.setAutor(autor);
        return Optional.of(livroRepo.save(novo));
    }

    public List<Livro> listarLivros() {
        return livroRepo.buscarTodosComAutor().stream()
                .sorted(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Autor> listarAutores() {
        return autorRepo.listarTodosComLivros().stream()
                .sorted(Comparator.comparing(Autor::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Autor> autoresVivosNoAno(Integer ano) {
        return autorRepo.filtrarVivosPorAno(ano).stream()
                .sorted(Comparator.comparing(Autor::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Livro> livrosPorIdioma(String idioma) {
        return livroRepo.buscarPorIdiomaComAutor(idioma).stream()
                .sorted(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}