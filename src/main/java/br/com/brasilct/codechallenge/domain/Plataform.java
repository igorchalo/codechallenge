package br.com.brasilct.codechallenge.domain;

import br.com.brasilct.codechallenge.domain.csv.Station;

public class Plataform extends Vertex {

    private final Station Station;

    private int line = -1;

    public Plataform(final Station Station, final int line) {
        this.Station = Station;
        this.line = line;
    }

    public Plataform(final Station Station) {
        this.Station = Station;
    }

    public boolean hasLine() {
        return line != -1;
    }

    public int getLine() {
        return line;
    }

    public void setLine(final int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Estação " + Station.getName()+ ", plataforma na line " + line;
    }

}
