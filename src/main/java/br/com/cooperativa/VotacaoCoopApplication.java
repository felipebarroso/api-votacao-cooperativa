package br.com.cooperativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class VotacaoCoopApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoCoopApplication.class, args);
	}

}
