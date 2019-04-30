package br.com.fiap.microservices.statisticsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@EqualsAndHashCode
public class EstatisticaDTO {

    @ApiModelProperty(notes = "Soma das transações", required = true)
    private Double sum;

    @ApiModelProperty(notes = "Média das transações", required = true)
    private Double avg;

    @ApiModelProperty(notes = "Maior valor", required = true)
    private Double max;

    @ApiModelProperty(notes = "Menor valor", required = true)
    private Double min;

    @ApiModelProperty(notes = "Conta o número de requisições", required = true)
    private Long count;

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public EstatisticaDTO(Double sum, Double avg, Double max, Double min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public EstatisticaDTO() {
    }
}
