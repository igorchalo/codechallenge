package br.com.brasilct.codechallenge.domain;

import org.jgrapht.EdgeFactory;

public class GraphEdgeFactory implements EdgeFactory<Vertex, Edge> {

    @Override
    public Edge createEdge(final Vertex origin, final Vertex destiny) {
        return new Edge(origin, destiny);
    }
}
