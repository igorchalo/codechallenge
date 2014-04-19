package br.com.brasilct.codechallenge.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.brasilct.codechallenge.domain.util.Delegate;
import br.com.brasilct.codechallenge.domain.util.Verify;
import br.com.brasilct.codechallenge.exception.ExecutionException;

public class CsvReaderService {
	
	private static final String DEMILITER = ",";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public <T> ArrayList<T> execute(String path,Delegate<T> delegate){
		logger.trace("> execute()");
		ArrayList<T> elements = readLines(path,delegate);
		logger.trace("< execute()");
		return elements;
	}
	

	public <T> ArrayList<T> readLines(String filePath, Delegate<T> delegate){
		logger.trace("> readLines()");
		
		ArrayList<T> elements = new ArrayList<T>();
		BufferedReader bufferedReader = null;
		String line = "";
	 
		try {
			
			if(Verify.isNotNull(filePath)){
				bufferedReader = getBufferedReader(filePath);
				
				while ((line = bufferedReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line,DEMILITER);
					
					T element = delegate.apply(st);
					
					if(Verify.isNotNull(element)){
						elements.add(element);
					}
				}	
			} else {
				throw new ExecutionException("O caminho de importacao nao eh valido.");
			}
			
		} catch(ExecutionException e){
			logger.error("Nao foi possivel executar a importacao.",e); 
		} catch (FileNotFoundException e) {
			logger.error("Nao foi possivel encontrar o arquivo",e);
		} catch (IOException e) {
			logger.error("Nao foi possivel ler o arquivo",e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("Nao foi possivel fechar o arquivo",e);
				}
			}
		}
		
		logger.trace("< readLines(): {}",elements.size());
		return elements;
	}
	
	protected BufferedReader getBufferedReader(String filePath) throws FileNotFoundException{
		return new BufferedReader(new FileReader(filePath));
	}
}
