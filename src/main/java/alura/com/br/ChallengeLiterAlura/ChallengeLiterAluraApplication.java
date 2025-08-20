package alura.com.br.ChallengeLiterAlura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import alura.com.br.ChallengeLiterAlura.principal.Principal;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();
     
    }
}
