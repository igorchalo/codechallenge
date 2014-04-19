package br.com.brasilct.codechallenge.domain.csv;

import java.util.HashSet;
import java.util.Set;

import br.com.brasilct.codechallenge.domain.Plataform;
import br.com.brasilct.codechallenge.domain.Vertex;

public class Station extends Vertex {

	private String id;
	
	private String latitude;
	
	private String longitude;
	
	private String name;
	
	private String displayName;
	
	private String zone;
	
	private String totalLines;
	
	private String rail;

	private final Set<Plataform> plataforms;
	 
	public Station(){
		this.plataforms = new HashSet<Plataform>();
	}
	
	public Station(final String name) {
	  this.name = name;
	  this.plataforms = new HashSet<Plataform>();
	}
	  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(String totalLines) {
		this.totalLines = totalLines;
	}

	public String getRail() {
		return rail;
	}

	public void setRail(String rail) {
		this.rail = rail;
	}
	
	@Override
    public String toString() {
        return "Estação " + getName();
    }

    public Set<Plataform> getPlataforms() {
        return plataforms;
    }

    public void addPlataform(final Plataform plataform) {
        this.plataforms.add(plataform);
    }
	
	
}
