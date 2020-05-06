package br.com.selecao.bestroute.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.selecao.bestroute.domain.dto.response.BestRouteResponse;
import br.com.selecao.bestroute.domain.dto.response.ErrorInfo;
import br.com.selecao.bestroute.services.RouteService;

@RestController
@RequestMapping("/routes")
public class RouteApi implements RouteDefinition {

	@Autowired
	private RouteService service;
	
	@Override
	@PostMapping
	public ResponseEntity<?> uploadRoutes(@RequestPart(value = "file") final MultipartFile inputFile) {
		if (CollectionUtils.isEmpty(service.uploadRoutes(inputFile))) {
			return ResponseEntity.status(BAD_REQUEST).body(ErrorInfo.builder()
							.code(BAD_REQUEST.value())
							.message("Error ao fazer upload das rotas").build());
		}
		return ok().build();
	}

	@Override
	@GetMapping("/shortests")
	public ResponseEntity<BestRouteResponse> getBestRoute(@RequestParam(required = true) final String from, @RequestParam(required = true) final String to) {
		return ok().body(service.getBestRoute(from, to));
	}

}
