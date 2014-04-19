package br.com.brasilct.codechallenge.service.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.jgrapht.Graph;
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
		CsvReaderService csvReaderService = new CsvReaderService();
		
		ArrayList<Line> lines = csvReaderService.execute(Util.LINES_PATH,new LineDelegate());
		ArrayList<Station> stations = csvReaderService.execute(Util.STATIONS_PATH,new StationDelegate());
		
        GraphService graphService = new GraphService(stations,lines);
        Graph<Vertex, Edge> graph = graphService.execute();
        
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
        
        Edge edge1 = graph.getEdge(graphService.getStation("Acton Town"),graphService.getStation("Chiswick Park"));
        //System.out.println(graph.getEdgeWeight(edge1));
        
        Edge edge2 = graph.getEdge(p2, graphService.getStation("Acton Town"));
        //System.out.println(graph.getEdgeWeight(edge2));
        
        Edge edge3 = graph.getEdge(graphService.getStation("Acton Town"),p3);
        //System.out.println(graph.getEdgeWeight(edge3));
        
        Edge edge4 = graph.getEdge(graphService.getStation("Acton Town"), p4);
        //System.out.println(graph.getEdgeWeight(edge4));
	}
	
}
