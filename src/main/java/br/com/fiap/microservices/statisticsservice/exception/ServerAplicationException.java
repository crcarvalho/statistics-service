package br.com.fiap.microservices.statisticsservice.exception;

import static br.com.fiap.microservices.statisticsservice.exception.Exceptions.toMap;

public class ServerAplicationException extends Exception {

    public ServerAplicationException(String... searchParamsMap) {
        super("Erro interno do servidor " + toMap(String.class, String.class, searchParamsMap));
    }
}