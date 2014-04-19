package br.com.brasilct.codechallenge.service;

import java.util.List;

import junit.framework.Assert;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.GraphEdgeFactory;
import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.util.Util;

public class GraphServiceTest {
	/**
	 * BaseTest.
	 */
	@Test
	public void dijkstraShortestPathBasic_example(){
		
		SimpleWeightedGraph<String, DefaultWeightedEdge>  graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        DefaultWeightedEdge e1 = graph.addEdge("A", "B"); 
        graph.setEdgeWeight(e1, 3); 
        
        DefaultWeightedEdge e2 = graph.addEdge("A", "C"); 
        graph.setEdgeWeight(e2, 12); 
        
        DefaultWeightedEdge e3 = graph.addEdge("B", "D"); 
        graph.setEdgeWeight(e3, 12); 
        
        DefaultWeightedEdge e4 = graph.addEdge("C", "D"); 
        graph.setEdgeWeight(e4, 12); 
        
        List<DefaultWeightedEdge> shortestPath =  DijkstraShortestPath.findPathBetween(graph, "A", "D");
        
        Assert.assertEquals("(A : B)",shortestPath.get(0).toString());
        Assert.assertEquals("(B : D)",shortestPath.get(1).toString());
		
	}
	
	 /**
     * Poc for test dijkstra algorith.
     */
    @Test
    public void dijkstraShortestPathBasic_poc() {

        SimpleWeightedGraph<Vertex, Edge> graph = new SimpleWeightedGraph<Vertex, Edge>(new GraphEdgeFactory());

        Station se = new Station("Sé");
        Plataform pSe1 = new Plataform(se, 1);
        Plataform pSe3 = new Plataform(se, 3);

        Station saoBento = new Station("São Bento");
        Plataform pSaoBento = new Plataform(saoBento, 1);

        Station liberdade = new Station("Liberdade");
        Plataform pLiberdade = new Plataform(liberdade, 1);

        Station anhangabau = new Station("Anhangabaú");
        Plataform pAnhangabau = new Plataform(anhangabau, 3);

        Station pedroII = new Station("Pedro II");
        Plataform pPedroII = new Plataform(pedroII, 3);

        graph.addVertex(se);
        graph.addVertex(pSe1);
        graph.addVertex(pSe3);
        graph.addVertex(saoBento);
        graph.addVertex(pSaoBento);
        graph.addVertex(liberdade);
        graph.addVertex(pLiberdade);
        graph.addVertex(anhangabau);
        graph.addVertex(pAnhangabau);
        graph.addVertex(pedroII);
        graph.addVertex(pPedroII);

        Edge e0 = graph.addEdge(se, pSe1);
        graph.setEdgeWeight(e0, GraphService.MAX_WEIGHT);

        Edge e1 = graph.addEdge(se, pSe3);
        graph.setEdgeWeight(e1, GraphService.MAX_WEIGHT);

        Edge e2 = graph.addEdge(pSe1, pSe3);
        graph.setEdgeWeight(e2, GraphService.TRANSHIPMENT_WEIGHT);

        Edge e3 = graph.addEdge(liberdade, pLiberdade);
        graph.setEdgeWeight(e3, GraphService.MAX_WEIGHT);

        Edge e4 = graph.addEdge(saoBento, pSaoBento);
        graph.setEdgeWeight(e4, GraphService.MAX_WEIGHT);

        Edge e5 = graph.addEdge(anhangabau, pAnhangabau);
        graph.setEdgeWeight(e5, GraphService.MAX_WEIGHT);

        Edge e6 = graph.addEdge(pedroII, pPedroII);
        graph.setEdgeWeight(e6, GraphService.MAX_WEIGHT);

        Edge e7 = graph.addEdge(pLiberdade, pSe1);
        graph.setEdgeWeight(e7, GraphService.ADJACENT_STATION);

        Edge e8 = graph.addEdge(pSe1, pSaoBento);
        graph.setEdgeWeight(e8, GraphService.ADJACENT_STATION);

        Edge e9 = graph.addEdge(pAnhangabau, pSe3);
        graph.setEdgeWeight(e9, GraphService.ADJACENT_STATION);

        Edge e10 = graph.addEdge(pSe3, pPedroII);
        graph.setEdgeWeight(e10, GraphService.ADJACENT_STATION);

        DijkstraShortestPath<Vertex, Edge> alg = new DijkstraShortestPath<Vertex, Edge>(graph, pedroII, saoBento);

        //System.out.println(alg.getPathEdgeList());
        Util.printRoute(alg.getPathEdgeList());

        System.out.println(Util.getTime(alg));

    }
    
    @Test
    public void graphSearch(){
    	//GraphSearch graphSearch = new GraphSearch();
    	//graphSearch.execute("Acton Town", "Aldgate");
    }
	
}
