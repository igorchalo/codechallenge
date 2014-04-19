package br.com.brasilct.codechallenge.domain.util;

public class Verify {
	
	public static boolean isNotNull(Object object){
		return object != null;
	}
	
	public static boolean notEquals(String string1,String string2){
		return !string1.equalsIgnoreCase(string2);
	}

}
