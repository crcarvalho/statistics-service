package br.com.fiap.microservices.statisticsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class StatisticsServiceApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(StatisticsServiceApplication.class, args);
		System.out.println("================ Aplicação on-line =============");


	}

}
