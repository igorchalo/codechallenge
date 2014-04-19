package br.com.brasilct.codechallenge.service.integration;

import java.util.ArrayList;

import org.junit.Test;

import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.service.CsvReaderService;

public class GraphServiceIntegrationTest {

	protected static final String STATIONS_PATH = "src/main/resources/stations.csv";

	protected static final String LINES_PATH = "src/main/resources/lines.csv";
	
	@Test
	public void dijkstraShortestPathIntegrationTest(){
		CsvReaderService csvReaderService =  new CsvReaderService();
		
		ArrayList<Line> lines = csvReaderService.execute(LINES_PATH,new LineDelegate());
		ArrayList<Station> stations = csvReaderService.execute(STATIONS_PATH,new StationDelegate());
		
		
		
	}
	
	/**
	 * Find any route.
	 */
	@Test
	public void findAnyRoute(){
		
	}
}
