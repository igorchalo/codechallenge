package br.com.brasilct.codechallenge.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.UnknownHostException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.domain.util.Util;
import br.com.brasilct.codechallenge.repository.MongoDbRepository;

import com.mongodb.DB;

public class ImportDataServiceTest {
	
	@Captor
	private ArgumentCaptor<ArrayList<Line>> lineCaptor;
	
	@Captor
	private ArgumentCaptor<ArrayList<Station>> stationCaptor;
	
	 @Before
	 public void init(){
	    MockitoAnnotations.initMocks(this);
	 }
	 
	@Test
	public void execute_unknownHostException() throws UnknownHostException{
		ImportDataService importDataService = spy(new ImportDataService());	
		doThrow(new UnknownHostException()).when(importDataService).getMongoDb(isA(MongoDbRepository.class));
	}
	
	
	@Test
	public void execute_success() throws UnknownHostException{
		DB mongoDB = mock(DB.class);
		
		MongoDbRepository mongoDbRepository = mock(MongoDbRepository.class);
		
		CsvReaderService csvReaderService = mock(CsvReaderService.class);
		when(csvReaderService.execute(eq(Util.LINES_PATH), isA(LineDelegate.class))).thenReturn(getLines());
		when(csvReaderService.execute(eq(Util.STATIONS_PATH), isA(StationDelegate.class))).thenReturn(getStations());
		
		ImportDataService importDataService = spy(new ImportDataService());	
		doReturn(mongoDB).when(importDataService).getMongoDb(isA(MongoDbRepository.class));
		doReturn(csvReaderService).when(importDataService).getCsvReaderService();
		doReturn(mongoDbRepository).when(importDataService).getMongoDbRepository();
		
		importDataService.execute();
		
		verify(mongoDbRepository).insertLines(isA(DB.class), lineCaptor.capture());
		verify(mongoDbRepository).insertStations(isA(DB.class), stationCaptor.capture());
		
		Assert.assertEquals("O tamanho da lista deveria ser 2",2,lineCaptor.getAllValues().get(0).size());
		Assert.assertEquals("O tamanho da lista deveria ser 2",2,stationCaptor.getAllValues().get(0).size());
	}
	
	
	private ArrayList<Line> getLines(){
		Line l1 = new Line();
		
		Line l2 = new Line();

		ArrayList<Line> lines = new ArrayList<Line>();
		lines.add(l1);
		lines.add(l2);
		
		return lines;
	}
	
	private ArrayList<Station> getStations(){
		Station s1 = new Station();
		
		Station s2 = new Station();
		
		ArrayList<Station> stations = new ArrayList<Station>();
		stations.add(s1);
		stations.add(s2);
		
		return stations;
	}

}
