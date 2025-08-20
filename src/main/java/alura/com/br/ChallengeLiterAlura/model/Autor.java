package alura.com.br.ChallengeLiterAlura.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    private String nome;

    @Column(name = "data_nascimento")
    private Integer dataNascimento;

    @Column(name = "data_morte")
    private Integer dataMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Livro> livros = new HashSet<>();

    public Autor() {}

    public Autor(String nome, Integer dataNascimento, Integer dataMorte) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataMorte = dataMorte;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getdataNascimento() { return dataNascimento; }
    public void setdataNascimento(Integer dataNascimento) { this.dataNascimento = dataNascimento; }
    public Integer getdataMorte() { return dataMorte; }
    public void setdataMorte(Integer dataMorte) { this.dataMorte = dataMorte; }
    public Set<Livro> getLivros() { return livros; }

    @Override
    public String toString() {
        return "----- Autor -----" +
                "\nNome: " + nome +
                "\nData Nascimento: " + dataNascimento +
                "\nData de falecimento:"+ dataMorte +
                "\nLivros:"+ livros+
                "\n-------------------";
    }


}
