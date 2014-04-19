package br.com.brasilct.codechallenge.domain;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;
import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.service.CsvReaderService;
import br.com.brasilct.codechallenge.service.GraphService;

public class GraphSearch {

	protected static final String STATIONS_PATH = "src/main/resources/stations.csv";

	protected static final String LINES_PATH = "src/main/resources/lines.csv";
	
	private static Graph<Vertex, Edge> graph;
	
	private static GraphService graphService;
	
	public static void init(){
		CsvReaderService csvReaderService = new CsvReaderService();
		
		ArrayList<Line> lines = csvReaderService.execute(LINES_PATH,new LineDelegate());
		ArrayList<Station> stations = csvReaderService.execute(STATIONS_PATH,new StationDelegate());
		
	    graphService = new GraphService(stations, lines);
        graph = graphService.execute();
	}
	
	//TODO-connect with database
    public void execute(String orign,String destiny) {

        Station originStation = graphService.getStation(orign);
        Station destinyStation = graphService.getStation(destiny);

        DijkstraShortestPath<Vertex, Edge> algorithm = new DijkstraShortestPath<Vertex, Edge>(graph, originStation, destinyStation);

        printRoute(algorithm.getPathEdgeList());

        printTime(algorithm);

    }

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

    public static void printTime(final DijkstraShortestPath<Vertex, Edge> algorithm) {
        System.out.println("Tempo gasto: " + ((int) algorithm.getPathLength() - 2 * GraphService.MAX_WEIGHT));
    }

}