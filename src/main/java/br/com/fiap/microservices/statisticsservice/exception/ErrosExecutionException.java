package br.com.fiap.microservices.statisticsservice.exception;

import static br.com.fiap.microservices.statisticsservice.exception.Exceptions.toMap;

public class ErrosExecutionException extends Exception {

    public ErrosExecutionException(String... searchParamsMap) {
        super("Sua última requisição foi a mais de 60 segundos " + toMap(String.class, String.class, searchParamsMap));
    }

}