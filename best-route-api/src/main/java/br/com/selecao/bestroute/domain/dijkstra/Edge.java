package br.com.selecao.bestroute.domain.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class Edge {

	private Vertex origin;
	private Integer weigth;
	private Vertex destination;

	public Edge(final Vertex origin, final Vertex destination) {
		this.weigth = 1;
		this.origin = origin;
		this.destination = destination;
	}
	
}
