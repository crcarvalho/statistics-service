package br.com.fiap.microservices.statisticsservice;

import br.com.fiap.microservices.statisticsservice.controller.StatisticsServiceController;
import br.com.fiap.microservices.statisticsservice.dto.EstatisticaDTO;
import br.com.fiap.microservices.statisticsservice.dto.TransacaoDTO;
import br.com.fiap.microservices.statisticsservice.exception.ErrosExecutionException;
import br.com.fiap.microservices.statisticsservice.repository.StatisticRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsServiceController.class)
public class StatisticsServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StatisticRepository repository;

	private Long dataHoraAtual;

	@Before
	public void setUp() throws Exception {
		dataHoraAtual = System.currentTimeMillis();
	}

	@Test
	public void transactionCreated() throws Exception {
		TransacaoDTO transacao = new TransacaoDTO(10.00, dataHoraAtual - 1);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String jsonInString = mapper.writeValueAsString(transacao);

		when(this.repository.add(transacao)).thenCallRealMethod();

		mvc.perform(post("/statistics-service/transaction/")
				.contentType("application/json").content(jsonInString))
				.andExpect(status().isCreated());
	}

	@Test
	public void estatisticasDentro60Segundos() throws Exception {
		EstatisticaDTO estatistica = new EstatisticaDTO(
				300.00,
				100.00,
				100.00,
				100.00,
				3L
		);

		for( int i = 0; i < 3; i++ ){
			this.repository.add( new TransacaoDTO(100.00, dataHoraAtual + i));
		}

		Map<Long, TransacaoDTO> transacoes = new HashMap<>();
		for( TransacaoDTO t : this.repository.all()){
			if( dataHoraAtual - t.getTimestamp() <= 60000 ){
				transacoes.put( t.getTimestamp(), t );
			}
		}

		when(this.repository.getTransactionsIn60s()).thenReturn(transacoes);

		mvc.perform(get("/statistics-service/statistics/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(response -> {
					String json = response.getResponse().getContentAsString();
					EstatisticaDTO estatistica2 = new ObjectMapper().readValue(json, EstatisticaDTO.class);
					assertThat(estatistica2).isEqualToComparingFieldByField(estatistica2);
				});
	}

	@Test
	public void estatisticasFora60Segundos() throws Exception {
		EstatisticaDTO estatistica = new EstatisticaDTO(
				0.00,
				0.00,
				0.00,
				0.00,
				0l
		);

		for( int i = 0; i < 3; i++ ){
			this.repository.add( new TransacaoDTO(100.00, dataHoraAtual - 100000 ));
		}

		Map<Long, TransacaoDTO> transacoes = new HashMap<>();
		for( TransacaoDTO t : this.repository.all()){
			if( dataHoraAtual - t.getTimestamp() <= 60000 ){
				transacoes.put( t.getTimestamp(), t );
			}
		}

		when(this.repository.getTransactionsIn60s()).thenReturn(transacoes);

		mvc.perform(get("/statistics-service/statistics/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(response -> {
					String json = response.getResponse().getContentAsString();
					EstatisticaDTO estatistica2 = new ObjectMapper().readValue(json, EstatisticaDTO.class);
					assertThat(estatistica).isEqualToComparingFieldByField(estatistica2);
				});
	}

}
