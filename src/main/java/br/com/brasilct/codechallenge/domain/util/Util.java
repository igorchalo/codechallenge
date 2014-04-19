package br.com.brasilct.codechallenge.domain.util;

import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;
import br.com.brasilct.codechallenge.service.GraphService;

public class Util {
	
	public static final String STATIONS_PATH = "src/main/resources/stations.csv";

	public static final String LINES_PATH = "src/main/resources/lines.csv";
	
    public static void printRoute(final List<Edge> result) {
        Vertex lastVertex = null;
        
        for (int i = 0; i < result.size(); i++) {
            Edge edge = result.get(i);
            
            if (i == 0) {
                if (edge.getVertex1() instanceof Plataform) {
                    System.out.println(edge.getVertex1());
                    lastVertex = edge.getVertex1();
                    
                } else if (edge.getVertex2() instanceof Plataform) {
                    System.out.println(edge.getVertex2());
                    lastVertex = edge.getVertex2();
                }
            } else if (i < result.size() - 1) {
                if (edge.getVertex1() == lastVertex) {
                    System.out.println(edge.getVertex2());
                    lastVertex = edge.getVertex2();
                    
                } else if (edge.getVertex2() == lastVertex) {
                    System.out.println(edge.getVertex1());
                    lastVertex = edge.getVertex1();
                }

            }
        }
    }

    public static String getTime(final DijkstraShortestPath<Vertex, Edge> algorithm) {
        return "Tempo gasto: " + ((int) algorithm.getPathLength() - 2 * GraphService.MAX_WEIGHT) + " minutos.";
    }

}
