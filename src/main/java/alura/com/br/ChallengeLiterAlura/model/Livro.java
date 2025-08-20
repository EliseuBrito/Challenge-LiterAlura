package alura.com.br.ChallengeLiterAlura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String titulo;

    @Column(length = 5)
    private String idioma;

    @Column(name = "quantidade_downloads")
    private Long quantidadeDownloads;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(String titulo, String idioma, Long quantidadeDownloads) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.quantidadeDownloads = quantidadeDownloads;
    }


    public Long getId() { return id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Long getquantidadeDownloads() { return quantidadeDownloads; }

    public void setquantidadeDownloads(Long quantidadeDownloads) { this.quantidadeDownloads = quantidadeDownloads; }

    public Autor getAutor() { return autor; }

    public void setAutor(Autor autor) { this.autor = autor; }


    @Override
    public String toString() {
        return "----- LIVRO -----" +
                "\nTítulo: " + titulo +
                "\nAutor (es): " + autor +
                "\nIdioma: " + idioma +
                "\nDownloads: " + quantidadeDownloads +
                "\n-------------------";
    }
                
}

