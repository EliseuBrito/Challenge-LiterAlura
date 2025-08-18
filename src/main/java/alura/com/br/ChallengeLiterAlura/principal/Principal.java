package alura.com.br.ChallengeLiterAlura.principal;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);


    public void exibeMenu(){
        var opcao =-1;

        while(opcao != 0){
            var menu = """
                    1- Buscar livro pelo titulo
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Listar livros em um determinado idioma
                    
                    0- Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;                                        
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

}
