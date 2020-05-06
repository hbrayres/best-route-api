package br.com.selecao.bestroute.domain.dijkstra;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(of = "description")
@EqualsAndHashCode(callSuper = false, of = "description")
public class Vertex implements Comparable<Vertex> {

	private String description;
	private Integer distance;
	private boolean visited;
	private Vertex ancestor;
	private List<Vertex> next;
	private List<Edge> edges;
	
	@Override
	public int compareTo(final Vertex other) {
		if (this.distance < other.distance ) return -1;
		if (this.distance > other.distance) return 1;
		return 1;
	}

}
