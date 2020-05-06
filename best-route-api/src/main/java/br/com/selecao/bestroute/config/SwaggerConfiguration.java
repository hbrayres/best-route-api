package br.com.selecao.bestroute.config;

import static java.util.Arrays.asList;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Value("${info.app.name:Api Documentation}")
	private String apiName;

	@Value("${info.app.description:Api Documentation}")
	private String apiDescription;

	@Value("${info.build.version}")
	private String version;

	private static final String ACCESS_TOKEN = "access_token";

	private static final String HEADER = "header";

	private static final String GLOBAL = "global";

	private static final String ACCESS_EVERYTHING = "accessEverything";

	@Bean
	public Docket swaggerSpringfoxDocket() {

		return new Docket(SWAGGER_2).apiInfo(apiInfo()).genericModelSubstitutes(ResponseEntity.class)
				.forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(LocalDateTime.class, String.class).securitySchemes(apiKeys())
				.securityContexts(securityContexts()).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(any()).build()
				.pathMapping("/");
	}

	public ApiInfo apiInfo() {

		return new ApiInfoBuilder().title(apiName).description(apiDescription).version(version).build();
	}

	@Bean
	public UiConfiguration uiConfig() {

		return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1).defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(true)
				.docExpansion(DocExpansion.NONE).filter(false).maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA).showExtensions(false).tagsSorter(TagsSorter.ALPHA)
				.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build();
	}

	private List<ApiKey> apiKeys() {

		return asList(new ApiKey(ACCESS_TOKEN, ACCESS_TOKEN, HEADER));
	}

	private List<SecurityContext> securityContexts() {

		return asList(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*"))
				.build());
	}

	List<SecurityReference> defaultAuth() {

		AuthorizationScope authorizationScope = new AuthorizationScope(GLOBAL, ACCESS_EVERYTHING);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;

		final List<SecurityReference> defaultAuths = new ArrayList<>();
		defaultAuths.add(new SecurityReference(ACCESS_TOKEN, authorizationScopes));

		return defaultAuths;
	}
}
