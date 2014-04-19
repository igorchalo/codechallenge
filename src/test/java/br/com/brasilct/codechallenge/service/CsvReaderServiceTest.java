package br.com.brasilct.codechallenge.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.domain.util.Util;

public class CsvReaderServiceTest {
	
	@Test
	public void nullPath(){
		CsvReaderService csvReaderService = spy(new CsvReaderService());	
		csvReaderService.execute(null, null);
	}
	
	@Test
	public void executeLines_success() throws IOException{
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(bufferedReader.readLine()).thenReturn("\"station1\",\"station2\",\"line\"","11,163,1","11,212,1",null);
		
		CsvReaderService csvReaderService = spy(new CsvReaderService());	
		doReturn(bufferedReader).when(csvReaderService).getBufferedReader(Util.LINES_PATH);
		ArrayList<Line> lines = csvReaderService.execute(Util.LINES_PATH, new LineDelegate());
		
		Assert.assertEquals("O tamanho da lista deveria ser 2",2,lines.size());
		
		Assert.assertEquals("A estacao1 nao eh a esperada.","11",lines.get(0).getStation1());
		Assert.assertEquals("A estacao2 nao eh a esperada.","163",lines.get(0).getStation2());
		Assert.assertEquals("A linha nao eh a esperada.","1",lines.get(0).getLine());
		
		Assert.assertEquals("A estacao1 nao eh a esperada.","11",lines.get(1).getStation1());
		Assert.assertEquals("A estacao2 nao eh a esperada.","212",lines.get(1).getStation2());
		Assert.assertEquals("A linha nao eh a esperada.","1",lines.get(1).getLine());
	}
	
	@Test
	public void executeStations_success() throws IOException{
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(bufferedReader.readLine()).thenReturn("\"id\",\"latitude\",\"longitude\",\"name\",\"display_name\",\"zone\",\"total_lines\",\"rail\"","1,51.5028,-0.2801,\"Acton Town\",\"Acton<br />Town\",3,2,0","2,51.5143,-0.0755,\"Aldgate\",NULL,1,2,0",null);
		
		CsvReaderService csvReaderService = spy(new CsvReaderService());	
		doReturn(bufferedReader).when(csvReaderService).getBufferedReader(Util.STATIONS_PATH);
		ArrayList<Station> station = csvReaderService.execute(Util.STATIONS_PATH, new StationDelegate());
		
		Assert.assertEquals("O tamanho da lista deveria ser 2",2,station.size());
		
		Assert.assertEquals("O id nao eh o esperado","1",station.get(0).getId());
		Assert.assertEquals("O nome nao eh o esperado","Acton Town",station.get(0).getName());
		Assert.assertEquals("O displayName nao eh o esperado","Acton<br />Town",station.get(0).getDisplayName());
		
		Assert.assertEquals("O id nao eh o esperado","2",station.get(1).getId());
		Assert.assertEquals("O nome nao eh o esperado","Aldgate",station.get(1).getName());
		Assert.assertEquals("O displayName nao eh o esperado","NULL",station.get(1).getDisplayName());
	}

}
