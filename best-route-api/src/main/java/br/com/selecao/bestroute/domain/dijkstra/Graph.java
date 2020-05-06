package br.com.selecao.bestroute.domain.dijkstra;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Graph {

	private Set<Vertex> vertices;

	public Vertex findByDescription(final String description) {
		return vertices.stream().filter(vertex -> vertex.getDescription().equals(description))
				.findFirst().orElse(null);
	}
}
