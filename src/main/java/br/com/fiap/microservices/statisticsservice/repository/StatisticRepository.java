package br.com.fiap.microservices.statisticsservice.repository;

import br.com.fiap.microservices.statisticsservice.dto.TransacaoDTO;
import br.com.fiap.microservices.statisticsservice.exception.ErrosExecutionException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StatisticRepository {
    private static Map<Long, TransacaoDTO> transactions = Collections.synchronizedMap(new HashMap<>());

    public Collection< TransacaoDTO > all(){
        return transactions.values();
    }
    public boolean add(TransacaoDTO transacao) throws ErrosExecutionException {
        Long systemTimestamp = System.currentTimeMillis();

        if (systemTimestamp - transacao.getTimestamp() > 60000) {
            throw new ErrosExecutionException();
        }

        transactions.put(transacao.getTimestamp(), transacao);
        return true;
    }

    public Map<Long, TransacaoDTO> getTransactionsIn60s() {
        Long timeStampAtual = System.currentTimeMillis();
        Map<Long, TransacaoDTO> transactionsIn60s = new HashMap<>();

        for (Map.Entry<Long, TransacaoDTO> pair : transactions.entrySet()) {
            if (timeStampAtual - pair.getKey() <= 60000) {
                transactionsIn60s.put(pair.getKey(), pair.getValue());
            }
        }

        return transactionsIn60s;
    }
}
