package br.com.brasilct.codechallenge.domain;

import java.net.UnknownHostException;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;

import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.util.Util;
import br.com.brasilct.codechallenge.repository.MongoDbRepository;
import br.com.brasilct.codechallenge.service.GraphService;

import com.mongodb.DB;

public class GraphSearch {

	private static GraphService graphService;
	
	private static Graph<Vertex, Edge> graph;
	
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

        return Util.getTime(algorithm);
    }
}