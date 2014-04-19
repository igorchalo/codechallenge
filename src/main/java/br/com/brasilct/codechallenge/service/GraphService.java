package br.com.brasilct.codechallenge.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import br.com.brasilct.codechallenge.domain.Edge;
import br.com.brasilct.codechallenge.domain.GraphEdgeFactory;
import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;
import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.Station;

public class GraphService {

    /**
     * Peso usado como sentinela, para garantir que uma aresta do grafo nunca seja usada (a não ser que seja a única opção).
     */
    public static final int MAX_WEIGHT = 1000;

    public static final int TRANSHIPMENT_WEIGHT = 12;

    public static final int ADJACENT_STATION = 3;

    private final Collection<Station> stations;

    private final Collection<Line> lines;

    /**
     * Mapeia nomes de estações para estações do grafo.
     */
    private final Map<String, Station> stationMap;

    public GraphService(final Collection<Station> stations, final Collection<Line> lines) {
        this.stations = stations;
        this.lines = lines;
        this.stationMap = new HashMap<String, Station>();
    }

    public Graph<Vertex, Edge> execute() {

        SimpleWeightedGraph<Vertex, Edge> graph = new SimpleWeightedGraph<Vertex, Edge>(new GraphEdgeFactory());

        Collection<Station> stations = new HashSet<Station>();

        for (Station station : this.stations) {
            Station newStation = new Station(station.getName());
            graph.addVertex(newStation);
            
            int linesConnectedToThisStation = countLinesConnectedToStation(station.getId());
            
            for (int i = 0; i < linesConnectedToThisStation; i++) {
                Plataform plataform = new Plataform(newStation);
                graph.addVertex(plataform);
                
                Edge edgeForPlataform = graph.addEdge(newStation, plataform);
                graph.setEdgeWeight(edgeForPlataform, MAX_WEIGHT);
                
                for (Plataform transhipmentPlataform : newStation.getPlataforms()) {
                    Edge edgeTranshipment = graph.addEdge(transhipmentPlataform, plataform);
                    graph.setEdgeWeight(edgeTranshipment, TRANSHIPMENT_WEIGHT);
                }
                
                newStation.addPlataform(plataform);
            }
            
            stationMap.put(station.getName(), newStation);
            stations.add(newStation);
        }

        for (Line line : lines) {
            Station singleStation1 = getStationById(line.getStation1());
            Station station1 = this.getStation(singleStation1.getName());
            
            Plataform plataform1 = null;
            
            for (Plataform plataform : station1.getPlataforms()) {
                if (!plataform.hasLine()) {
                    plataform.setLine(Integer.parseInt(line.getLine()));
                    plataform1 = plataform;
                    break;
                } else if (plataform.getLine() == Integer.parseInt(line.getLine())) {
                    plataform1 = plataform;
                    break;
                }
            }

            Station singleStation2 = getStationById(line.getStation2());
            Station station2 = this.getStation(singleStation2.getName());
            
            Plataform plataform2 = null;
            
            for (Plataform plataform : station2.getPlataforms()) {
                if (!plataform.hasLine()) {
                    plataform.setLine(Integer.parseInt(line.getLine()));
                    plataform2 = plataform;
                    break;
                } else if (plataform.getLine() == Integer.parseInt(line.getLine())) {
                    plataform2 = plataform;
                    break;
                }
            }

            Edge adjacentStation = graph.addEdge(plataform1, plataform2);
            graph.setEdgeWeight(adjacentStation, ADJACENT_STATION);
        }
        return graph;
    }

    private int countLinesConnectedToStation(final String idStation) {
        int result = 0;
        for (Line line : lines) {
            if (line.getStation1().equals(idStation) || line.getStation2().equals(idStation)) {
                result++;
            }
        }
        return result;
    }

    // TODO- user repository
    private Station getStationById(final String id) {
        for (Station station : this.stations) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null;
    }

    public Station getStation(final String name) {
        return stationMap.get(name);
    }
}
