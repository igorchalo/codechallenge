package br.com.brasilct.codechallenge.domain.csv;

import java.util.StringTokenizer;

import br.com.brasilct.codechallenge.domain.util.Delegate;
import br.com.brasilct.codechallenge.domain.util.Verify;

public class LineDelegate implements Delegate<Line> {

	private static final String FIRST_HEADER_VALUE = "\"station1\"";
	
	@Override
	public Line apply(Object object) {
		
		Line line = null;
		
		 if(Verify.isNotNull(object) && object instanceof StringTokenizer){
			 StringTokenizer tokenizer = (StringTokenizer) object;
			 
			 if(tokenizer.countTokens() == 3){
				 String firstLine = (String)tokenizer.nextElement();
				 
				 if(Verify.notEquals(firstLine, FIRST_HEADER_VALUE)){
					 line = new Line();
					 line.setStation1(firstLine);
					 line.setStation2((String)tokenizer.nextElement());
					 line.setLine((String)tokenizer.nextElement()); 
				 }
				 
			 }
			 
		 }
		return line;
		
	}

}
