package br.com.brasilct.codechallenge.domain.util;

public class Formatter {
	
	public static String formatCsvValue(String value){
        return value.replace("\"", "");
	}
	

}
