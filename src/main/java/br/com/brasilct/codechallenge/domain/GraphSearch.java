package br.com.brasilct.codechallenge.domain;

import java.net.UnknownHostException;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;

import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.repository.MongoDbRepository;
import br.com.brasilct.codechallenge.service.GraphService;

import com.mongodb.DB;

public class GraphSearch {

	private static Graph<Vertex, Edge> graph;
	
	private static GraphService graphService;

	public static void init() throws UnknownHostException{
		MongoDbRepository mongoDbRepository = new MongoDbRepository();
		DB db = mongoDbRepository.getMongoDb();
		
	    graphService = new GraphService(mongoDbRepository.getAllStations(db), mongoDbRepository.getAllLines(db));
        graph = graphService.execute();
	}
	
    public List<Edge> getShortestPath(String origin,String destiny) {

        Station originStation = graphService.getStation(origin);
        Station destinyStation = graphService.getStation(destiny);

        DijkstraShortestPath<Vertex, Edge> algorithm = new DijkstraShortestPath<Vertex, Edge>(graph, originStation, destinyStation);

        return algorithm.getPathEdgeList();
    }
    
    public String getRouteTime(String origin,String destiny) {

        Station originStation = graphService.getStation(origin);
        Station destinyStation = graphService.getStation(destiny);

        DijkstraShortestPath<Vertex, Edge> algorithm = new DijkstraShortestPath<Vertex, Edge>(graph, originStation, destinyStation);

        return getTime(algorithm);
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

    public static String getTime(final DijkstraShortestPath<Vertex, Edge> algorithm) {
        return "Tempo gasto: " + ((int) algorithm.getPathLength() - 2 * GraphService.MAX_WEIGHT);
    }

}