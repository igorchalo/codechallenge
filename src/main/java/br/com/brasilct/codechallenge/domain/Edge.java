package br.com.brasilct.codechallenge.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {

    private static final long serialVersionUID = 1L;

    private final Vertex Vertex1;
    private final Vertex Vertex2;

    public Edge(final Vertex v1, final Vertex v2) {
        this.Vertex1 = v1;
        this.Vertex2 = v2;
    }

    public Vertex getVertex1() {
        return Vertex1;
    }

    public Vertex getVertex2() {
        return Vertex2;
    }

}
