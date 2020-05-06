package br.com.selecao.bestroute.services;

import static br.com.selecao.bestroute.domain.dijkstra.Dijkstra.findShortestPath;
import static br.com.selecao.bestroute.util.CSVUtils.loadObjectList;
import static br.com.selecao.bestroute.util.GenericConvert.mapAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Column;

import br.com.selecao.bestroute.domain.dijkstra.Edge;
import br.com.selecao.bestroute.domain.dijkstra.Graph;
import br.com.selecao.bestroute.domain.dijkstra.Vertex;
import br.com.selecao.bestroute.domain.dto.Route;
import br.com.selecao.bestroute.domain.dto.response.BestRouteResponse;
import br.com.selecao.bestroute.domain.entities.RouteEntity;
import br.com.selecao.bestroute.domain.repositories.RouteRepository;

@Service
public class RouteService {

	@Autowired
	private RouteRepository repository;
	
	@Transactional
	public List<RouteEntity> uploadRoutes(final MultipartFile inputFile) {
		
		final List<Column> columns = new ArrayList<>();
		columns.add(new Column(0, "from", CsvSchema.ColumnType.STRING));
		columns.add(new Column(1, "to", CsvSchema.ColumnType.STRING));
		columns.add(new Column(2, "cust", CsvSchema.ColumnType.NUMBER));
		
		final List<Route> routes = loadObjectList(Route.class, columns, inputFile);
		
		if (CollectionUtils.isEmpty(routes)) {
			return Collections.emptyList();
		}
		
		repository.deleteAll();
		
		return repository.saveAll(mapAll(routes, RouteEntity.class));
		
	}

	public BestRouteResponse getBestRoute(final String from, final String to) {
		
		final List<RouteEntity> routes = repository.findAll();
		
		final Graph graph = buildGraph(routes);
		final Vertex cityFrom = graph.findByDescription(from.toUpperCase());
		final Vertex cityTo = graph.findByDescription(to.toUpperCase());
		
		final BestRouteResponse response = new BestRouteResponse();
		if (Objects.isNull(cityFrom) || Objects.isNull(cityTo)) {
			response.setRoutes("Route not found");
			return response;
		}
		
		final List<Vertex> shortestPath = findShortestPath(graph, cityFrom, cityTo);
		
		if (shortestPath.isEmpty() || shortestPath.size() == 1) {
			response.setRoutes("Route not found");
			return response;
		}
		
		final StringBuffer citiesVisited = new StringBuffer();
		Integer totalDistance = 0;
		for (int i = shortestPath.size() - 1; i >= 0; i--) {
			if (citiesVisited.length() > 0)
				citiesVisited.append(" - ");
			citiesVisited.append(shortestPath.get(i).getDescription());
			
			totalDistance = shortestPath.get(i).getDistance();
		}
			
		response.setTotalCust("$" + totalDistance);
		response.setRoutes(citiesVisited.toString());
		
		return response;
	}
	
	private Graph buildGraph(final List<RouteEntity> routes) {
		
		final Map<String, Vertex> mapVertices = new HashMap<>();
		
		routes.forEach(route -> {

			if (!mapVertices.containsKey(route.getFrom())) {
				mapVertices.put(route.getFrom(), Vertex.builder()
						.description(route.getFrom())
						.edges(new ArrayList<>())
						.next(new ArrayList<>()).build());
			}
			if (!mapVertices.containsKey(route.getTo())) {
				mapVertices.put(route.getTo(), Vertex.builder()
						.description(route.getTo())
						.edges(new ArrayList<>())
						.next(new ArrayList<>()).build());
			}

			final Vertex vActual = mapVertices.get(route.getFrom());
			vActual.getNext().add(mapVertices.get(route.getTo()));
			vActual.getEdges().add(Edge.builder()
					.origin(vActual)
					.weigth(route.getCust())
					.destination(mapVertices.get(route.getTo())).build());
			
		});
		
		return Graph.builder().vertices(new HashSet<>(mapVertices.values())).build();
	}

}
