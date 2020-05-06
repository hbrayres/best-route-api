package br.com.selecao.bestroute.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import br.com.selecao.bestroute.domain.dto.response.BestRouteResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Routes", tags = { "Route" }, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public interface RouteDefinition {

	@ApiOperation(value = "Service to save routes")
	public ResponseEntity<?> uploadRoutes(MultipartFile inputFile);
	
	@ApiOperation(value = "Service to list best route from a Origem to destination")
	public ResponseEntity<BestRouteResponse> getBestRoute(String from, String to);
}
