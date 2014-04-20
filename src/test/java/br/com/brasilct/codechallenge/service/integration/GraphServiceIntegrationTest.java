package br.com.brasilct.codechallenge.service.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;
import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.domain.util.Util;
import br.com.brasilct.codechallenge.service.CsvReaderService;
import br.com.brasilct.codechallenge.service.GraphService;

public class GraphServiceIntegrationTest {
	
	private static GraphService graphService;
	
	private static Graph<Vertex, Edge> graph;
	
	@BeforeClass
	public static void init(){
		CsvReaderService csvReaderService = new CsvReaderService();
		
		ArrayList<Line> lines = csvReaderService.execute(Util.LINES_PATH,new LineDelegate());
		ArrayList<Station> stations = csvReaderService.execute(Util.STATIONS_PATH,new StationDelegate());
		
        graphService = new GraphService(stations,lines);
        graph = graphService.execute();
	}
	
	/**
	 * Get data from csvFile and execute dijkstra.
	 * 
	 * Test -
	 * Line 1 Acton Town.
	 * 
	 * 
	 * Line 1 and Line 52  (Chiswick Park, line 4)
	 * Line 1 and Line 73  (Ealing Common, line 4)
	 * Line 1 and Line 73  (Ealing Common, line 10)
	 * Line 1 and Line 234 (South Ealing,  line 10)
	 * Line 1 and Line 265 (Turnham Green, line 10)
	 * 
	 */
	@Test
	public void dijkstraShortestPathIntegrationTest(){
        Station actonTownStation = graphService.getStation("Acton Town");
        Set<Plataform> plataforms = actonTownStation.getPlataforms();
        
        Assert.assertEquals("O nome da estacao esta errado.","Acton Town", actonTownStation.getName());
        Assert.assertEquals("A estacao deveria ter 5 nos",5,plataforms.size());
        
        Iterator<Plataform> plataformIterator = plataforms.iterator();
        
        Plataform p1 = plataformIterator.next();
        Plataform p2 = plataformIterator.next();
        Plataform p3 = plataformIterator.next();
        Plataform p4 = plataformIterator.next();
        Plataform p5 = plataformIterator.next();
        
        Assert.assertEquals(p1.getLine(), 4);
        Assert.assertEquals(p2.getLine(), 10);
        Assert.assertEquals(p3.getLine(), -1);
        Assert.assertEquals(p4.getLine(), -1);
        Assert.assertEquals(p5.getLine(), -1);
        
        Edge edge1 = graph.getEdge(p1,graphService.getStation("Chiswick Park").getPlataforms().iterator().next());
        Assert.assertEquals("A ligacao deveria ser adjacente",3.0, graph.getEdgeWeight(edge1));
        
        Edge edge2 = graph.getEdge(p2,graphService.getStation("South Ealing").getPlataforms().iterator().next());
        Assert.assertEquals("A ligacao deveria ser adjacente",3.0, graph.getEdgeWeight(edge2));
        
        Station originStation = graphService.getStation("Acton Town");
        Station destinyStation = graphService.getStation("Stamford Brook");

        DijkstraShortestPath<Vertex, Edge> algorithm = new DijkstraShortestPath<Vertex, Edge>(graph, originStation, destinyStation);
        Assert.assertEquals("O tempo deveria ser de 9.","Tempo gasto: 9 minutos.", Util.getTime(algorithm));
	}
	
	@Test
	public void oxfordToJohn(){
		Station originStation = graphService.getStation("Oxford Circus");
		Station destinyStation = graphService.getStation("St. John's Wood");

		DijkstraShortestPath<Vertex, Edge> algorithm = new DijkstraShortestPath<Vertex, Edge>(graph, originStation, destinyStation);
        Assert.assertEquals("O tempo deveria ser de 21.","Tempo gasto: 21 minutos.", Util.getTime(algorithm));
	}
	
}
