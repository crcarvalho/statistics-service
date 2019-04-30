package br.com.fiap.microservices.statisticsservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ResponseException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dataHora;
    private String messagem;
    private String detalhes;

    public ResponseException(String messagem, String detalhes, LocalDateTime dataHora) {
        super();
        this.dataHora = dataHora;
        this.messagem = messagem;
        this.detalhes = detalhes;
    }
}
