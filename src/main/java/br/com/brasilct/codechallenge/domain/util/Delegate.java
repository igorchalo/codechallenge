package br.com.brasilct.codechallenge.domain.util;

public interface Delegate<T> {
	
	T apply(Object object);

}
