package br.com.cooperativa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api10() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api-votacao-cooperativa-1.0")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cooperativa.controller"))
				.paths(PathSelectors.regex(".*/v1.0.*"))
				.build()
				.apiInfo(apiInfo10());
	}

	public ApiInfo apiInfo10() {
		return new ApiInfoBuilder().title("API Votação Cooperativa Rest")
				.description("Serviço para gerenciamento de votações da cooperativa")
				.version("1.0").build();
	}
	
	
	@Bean
	public Docket api11() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api-votacao-cooperativa-1.1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cooperativa.controller"))
				.paths(PathSelectors.regex(".*/v1.1.*"))
				.build()
				.apiInfo(apiInfo11());
	}

	public ApiInfo apiInfo11() {
		return new ApiInfoBuilder().title("Teste de versionamento 1.1")
				.description("Serviço para gerenciamento de votações da cooperativa")
				.version("1.1").build();
	}
	
	@Bean
	public Docket api12() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api-votacao-cooperativa-1.2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.cooperativa.controller"))
				.paths(PathSelectors.regex(".*/v1.2.*"))
				.build()
				.apiInfo(apiInfo12());
	}

	public ApiInfo apiInfo12() {
		return new ApiInfoBuilder().title("Teste de versionamento 1.2")
				.description("Serviço para gerenciamento de votações da cooperativa")
				.version("1.2").build();
	}

}
