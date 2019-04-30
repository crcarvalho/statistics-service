package br.com.fiap.microservices.statisticsservice.controller;


import br.com.fiap.microservices.statisticsservice.dto.EstatisticaDTO;
import br.com.fiap.microservices.statisticsservice.dto.TransacaoDTO;
import br.com.fiap.microservices.statisticsservice.exception.ErrosExecutionException;
import br.com.fiap.microservices.statisticsservice.exception.ServerAplicationException;
import br.com.fiap.microservices.statisticsservice.repository.StatisticRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/statistics-service")
public class StatisticsServiceController {

    @Autowired
    private StatisticRepository repository;

    @ApiOperation(httpMethod = "POST", value = "Método de criação de transações")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Criado",
                    response = Object.class),
            @ApiResponse(
                    code = 204,
                    message = "Diferenças entre chamadas maior 60 segundos",
                    response = Object.class)
    })
    @PostMapping("/transaction")
    public ResponseEntity post( @ApiParam(format = "Json", name = "TransacaoDTO")
                                @RequestBody TransacaoDTO transacao) throws ErrosExecutionException, ServerAplicationException {

        try {
            repository.add(transacao);
        } catch (ErrosExecutionException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerAplicationException();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(httpMethod = "GET", value = "Retorna valores das transações dentro de 60 segundos")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Retorno EstatisticaDTO ",
                    response = EstatisticaDTO.class)
    })
    @GetMapping(value = "/statistics", produces = "application/json")
    public ResponseEntity<EstatisticaDTO> get() {
        Map<Long, TransacaoDTO> mapTransacoes = repository.getTransactionsIn60s();
        EstatisticaDTO estatistica = new EstatisticaDTO();

        if (mapTransacoes == null || mapTransacoes.isEmpty()) {
            estatistica.setSum(0.00);
            estatistica.setAvg(0.00);
            estatistica.setMax(0.00);
            estatistica.setMin(0.00);
            estatistica.setCount(0l);
        } else {
            estatistica.setSum(mapTransacoes.values().stream().mapToDouble(value -> value.getAmount()).sum());
            estatistica.setAvg(mapTransacoes.values().stream().mapToDouble(value -> value.getAmount()).average().getAsDouble());
            estatistica.setMax(mapTransacoes.values().stream().mapToDouble(value -> value.getAmount()).max().getAsDouble());
            estatistica.setMin(mapTransacoes.values().stream().mapToDouble(value -> value.getAmount()).min().getAsDouble());
            estatistica.setCount(mapTransacoes.values().stream().mapToDouble(value -> value.getAmount()).count());
        }
        return new ResponseEntity<>(estatistica, HttpStatus.OK);
    }

}
