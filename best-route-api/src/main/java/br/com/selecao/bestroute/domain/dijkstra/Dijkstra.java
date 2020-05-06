package br.com.selecao.bestroute.domain.dijkstra;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

	private static final Integer MAX_DISTANCE = MAX_VALUE / 2;
	
	public static List<Vertex> findShortestPath(final Graph graph, final Vertex origin, final Vertex destination) {
		
		final List<Vertex> shortestPath = new ArrayList<>();
		final List<Vertex> notVisited = new ArrayList<>();
		
		shortestPath.add(origin);
		graph.getVertices().forEach(vertice -> {
			if (vertice.getDescription().equalsIgnoreCase(origin.getDescription())) {
				vertice.setDistance(0);
			} else {
				vertice.setDistance(MAX_DISTANCE);
			}
			
			notVisited.add(vertice);
			
		});
		
		sort(notVisited);
		
		while(!notVisited.isEmpty()) {
			
			final Vertex actual = notVisited.get(0);
			
			actual.getEdges().forEach(edge -> {
				final Vertex near = edge.getDestination();
				
				Vertex vertexPath;
				
				if (!near.isVisited()) {
					if (near.getDistance() > (actual.getDistance() + edge.getWeigth())) {
						
						near.setDistance(actual.getDistance() + edge.getWeigth());
						near.setAncestor(actual);
						
						if (near.equals(destination)) {
							shortestPath.clear();
							vertexPath = near;
							shortestPath.add(near);
							
							while (vertexPath.getAncestor() != null) {
								shortestPath.add(vertexPath.getAncestor());
								vertexPath = vertexPath.getAncestor();
							}
							
							sort(notVisited);
						}
					}
				}
			});
			
			actual.setVisited(true);
			notVisited.remove(actual);
			
			sort(notVisited);
		}
		
		return shortestPath;
	}
}
