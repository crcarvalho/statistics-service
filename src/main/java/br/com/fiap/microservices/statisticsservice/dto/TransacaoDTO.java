package br.com.fiap.microservices.statisticsservice.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@EqualsAndHashCode
public class TransacaoDTO {

    @ApiModelProperty(required = true, value = "Data/Hora em formato timestamp")
    private Long timestamp;

    @ApiModelProperty(required = true, value = "Valor da transação")
    private Double amount;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransacaoDTO(Double amount, Long timestamp){
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public TransacaoDTO() {
    }
}

