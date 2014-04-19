package br.com.brasilct.codechallenge.service;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.LineDelegate;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.csv.StationDelegate;
import br.com.brasilct.codechallenge.domain.util.Util;
import br.com.brasilct.codechallenge.repository.MongoDbRepository;

import com.mongodb.DB;

public class ImportDataService implements Service {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void execute() {
		logger.trace("> execute()");

		try {
			MongoDbRepository mongoDbRepository = getMongoDbRepository();
			DB db = getMongoDb(mongoDbRepository);
			
			logger.trace("Executing csvReaderService");
			CsvReaderService csvReaderService = getCsvReaderService();
			logger.trace("Finished csvReaderService");
			
			ArrayList<Line> lines = csvReaderService.execute(Util.LINES_PATH,new LineDelegate());

			logger.trace("Inserting lines... ");
			mongoDbRepository.insertLines(db, lines);
			logger.trace("Finished inserting lines... ");
			
			ArrayList<Station> stations = csvReaderService.execute(Util.STATIONS_PATH,new StationDelegate());

			logger.trace("Inserting stations... ");
			mongoDbRepository.insertStations(db, stations);
			logger.trace("Finished inserting stations... ");
			
		} catch (UnknownHostException e) {
			logger.error("Nao foi possivel conectar no Mongodb",e);
		}
		
		
		logger.trace("< execute()");
	}
	
	protected MongoDbRepository getMongoDbRepository(){
		return new MongoDbRepository();
	}
	
	protected DB getMongoDb(MongoDbRepository mongoDbRepository) throws UnknownHostException{
		return mongoDbRepository.getMongoDb();
	}
	
	protected CsvReaderService getCsvReaderService(){
		return new CsvReaderService();
	}

}
