package alura.com.br.ChallengeLiterAlura.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
