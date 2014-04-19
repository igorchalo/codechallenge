package br.com.brasilct.codechallenge.domain.csv;

import java.util.StringTokenizer;

import br.com.brasilct.codechallenge.domain.util.Delegate;
import br.com.brasilct.codechallenge.domain.util.Formatter;
import br.com.brasilct.codechallenge.domain.util.Verify;

public class StationDelegate implements Delegate<Station> {

	private static final String FIRST_HEADER_VALUE = "\"id\"";
	
	@Override
	public Station apply(Object object) {
	
		Station station = null;
		
		 if(Verify.isNotNull(object) && object instanceof StringTokenizer){
			 StringTokenizer tokenizer = (StringTokenizer) object;
			 
			 //TODO- tratar qdo tiver virgula no nome
			 if(tokenizer.countTokens() == 8){
				 String firstLine = (String)tokenizer.nextElement();
				 
				 if(Verify.notEquals(firstLine, FIRST_HEADER_VALUE)){
					 station = new Station();
					 station.setId(firstLine);
					 station.setLatitude((String)tokenizer.nextElement());
					 station.setLongitude((String)tokenizer.nextElement());
					 station.setName(Formatter.formatCsvValue((String)tokenizer.nextElement()));
					 station.setDisplayName(Formatter.formatCsvValue((String)tokenizer.nextElement()));
					 station.setZone((String)tokenizer.nextElement());
					 station.setTotalLines((String)tokenizer.nextElement());
					 station.setRail((String)tokenizer.nextElement());
				 }
			 } 
		 }
		return station;
		
	}

}
