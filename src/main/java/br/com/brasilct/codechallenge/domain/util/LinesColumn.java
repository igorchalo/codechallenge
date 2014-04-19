package br.com.brasilct.codechallenge.domain.util;

public enum LinesColumn {

	STATION1("station1"),
	STATION2("station2"),
	LINE("line");
	
	private String value;

    private LinesColumn(String value) {
      this.value = value;
    }
    
    public String getValue() {
        return value;
    }
	
}
