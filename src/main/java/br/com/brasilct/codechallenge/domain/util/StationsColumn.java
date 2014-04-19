package br.com.brasilct.codechallenge.domain.util;

public enum StationsColumn {
	ID("id"),
	LATITUDE("latitude"),
	LONGITUDE("longitude"),
	NAME("name"),
	DISPLAY_NAME("display_name"),
	ZONE("zone"),
	TOTAL_LINES("total_lines"),
	RAIL("rail");
	
	private String value;

    private StationsColumn(String value) {
      this.value = value;
    }
    
    public String getValue() {
        return value;
    }
	
}
