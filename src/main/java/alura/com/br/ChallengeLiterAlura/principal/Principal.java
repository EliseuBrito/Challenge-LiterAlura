package alura.com.br.ChallengeLiterAlura.principal;

import alura.com.br.ChallengeLiterAlura.model.Autor;
import alura.com.br.ChallengeLiterAlura.model.Livro;
import alura.com.br.ChallengeLiterAlura.service.CatalogoService;
import alura.com.br.ChallengeLiterAlura.service.ConsumoApi;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi(null);
    private final CatalogoService catalogoService = new CatalogoService(consumoApi, null, null);

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    ===== LiterAlura =====
                    1) Buscar livro por título (via API)
                    2) Listar livros cadastrados
                    3) Listar autores cadastrados
                    4) Listar autores vivos em determinado ano
                    5) Listar livros por idioma (EN, ES, FR, PT)
                    
                    0) Sair
                    """);

            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Use apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> autoresVivosNoAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Encerrando a aplicação. Até logo!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Informe o título do livro: ");
        String titulo = scanner.nextLine().trim();

        catalogoService.buscarESalvarPorTitulo(titulo).ifPresentOrElse(
            livro -> {
                System.out.println("[Livro salvo/atualizado com sucesso]");
                exibirLivro(livro);
            },
            () -> System.out.println("Nenhum resultado encontrado na API para o título informado.")
        );
    }

    private void listarLivros() {
        List<Livro> livros = catalogoService.listarLivros();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(this::exibirLivro);
        }
    }

    private void listarAutores() {
        List<Autor> autores = catalogoService.listarAutores();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
        } else {
            autores.forEach(this::exibirAutor);
        }
    }

    private void autoresVivosNoAno() {
        System.out.print("Digite o ano desejado (ex: 1900): ");
        try {
            int ano = Integer.parseInt(scanner.nextLine().trim());
            List<Autor> autores = catalogoService.autoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.printf("Nenhum autor vivo encontrado no ano %d.%n", ano);
            } else {
                autores.forEach(this::exibirAutor);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido. Utilize apenas números.");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Informe o idioma (EN, ES, FR, PT): ");
        String idioma = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

        List<Livro> livros = catalogoService.livrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.printf("Nenhum livro encontrado no idioma %s.%n", idioma);
        } else {
            livros.forEach(this::exibirLivro);
        }
    }

    private void exibirLivro(Livro livro) {
        String autor = livro.getAutor() != null ? livro.getAutor().getNome() : "(Autor desconhecido)";
        System.out.printf("""
                ----- LIVRO ------
                Título    : %s
                Autor     : %s
                Idioma    : %s
                Downloads : %d
                """, livro.getTitulo(), autor, livro.getIdioma(), livro.getquantidadeDownloads());
    }

    private void exibirAutor(Autor autor) {
        System.out.printf("""
                --------------------------
                Nome        : %s
                Nascimento  : %s
                Falecimento : %s
                Livros      : %d
                """,
                autor.getNome(),
                autor.getdataNascimento() != null ? autor.getdataNascimento() : "-",
                autor.getdataMorte() != null ? autor.getdataMorte() : "-",
                autor.getLivros() != null ? autor.getLivros().size() : 0);
    }
}
