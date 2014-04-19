package br.com.brasilct.codechallenge.repository;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.brasilct.codechallenge.domain.csv.Line;
import br.com.brasilct.codechallenge.domain.csv.Station;
import br.com.brasilct.codechallenge.domain.util.LinesColumn;
import br.com.brasilct.codechallenge.domain.util.StationsColumn;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//TODO- add configuration - pom
	public DB getMongoDb() throws UnknownHostException{
		logger.trace("> getMongoDb()");
		MongoClient mongo = new MongoClient("localhost" , 27017);
		DB db = mongo.getDB("subway");
		
		logger.trace("< getMongoDb()");
		return db;
	}
	
	public void insertLines(DB db,ArrayList<Line> lines){
		logger.trace("> insertLines()");
		
		DBCollection lineTable = db.getCollection("line");
		
		for(Line line : lines){
			BasicDBObject document = new BasicDBObject();			
			document.put(LinesColumn.STATION1.getValue(), line.getStation1());
			document.put(LinesColumn.STATION2.getValue(),line.getStation2());
			document.put(LinesColumn.LINE.getValue(), line.getLine());
			lineTable.insert(document);
		}
		
		logger.trace("< insertLines()");
	}
	
	public void insertStations(DB db,ArrayList<Station> stations){
		logger.trace("> insertStations()");
		
		DBCollection table = db.getCollection("station");
		
		for(Station station : stations){
			BasicDBObject document = new BasicDBObject();			
			document.put(StationsColumn.ID.getValue(), station.getId());
			document.put(StationsColumn.LATITUDE.getValue(),station.getLatitude());
			document.put(StationsColumn.LONGITUDE.getValue(), station.getLongitude());
			document.put(StationsColumn.NAME.getValue(), station.getName());
			document.put(StationsColumn.DISPLAY_NAME.getValue(), station.getDisplayName());
			document.put(StationsColumn.ZONE.getValue(), station.getZone());
			document.put(StationsColumn.TOTAL_LINES.getValue(), station.getTotalLines());
			document.put(StationsColumn.RAIL.getValue(), station.getRail());
			
			table.insert(document);
		}
		
		logger.trace("< insertStations()");
	}
	
	public ArrayList<Line> getAllLines(DB db){
		logger.trace("> getAllLines()");
		
		ArrayList<Line> lines = new ArrayList<Line>();
		
		DBCursor cursor =  db.getCollection("line").find();
		
		while(cursor.hasNext()) {
			DBObject document = cursor.next();

			Line line = new Line();
			line.setStation1(getColumnValue(document,LinesColumn.STATION1.getValue()));
			line.setStation2(getColumnValue(document,LinesColumn.STATION2.getValue()));
			line.setLine(getColumnValue(document,LinesColumn.LINE.getValue()));
			
			lines.add(line);
		}
		
		logger.trace("> getAllLines(): {}",lines.size());
		return lines;
	}

	
	public ArrayList<Station> getAllStations(DB db){
		logger.trace("> getAllStations()");
		ArrayList<Station> stations = new ArrayList<Station>();
		
		DBCursor cursor =  db.getCollection("line").find();
		
		while(cursor.hasNext()) {
			DBObject document = cursor.next();
			
			Station station = new Station();
			station.setId(getColumnValue(document,StationsColumn.ID.getValue()));
			station.setLatitude(getColumnValue(document,StationsColumn.LATITUDE.getValue()));
			station.setLongitude(getColumnValue(document,StationsColumn.LONGITUDE.getValue()));
			station.setName(getColumnValue(document,StationsColumn.NAME.getValue()));
			station.setDisplayName(getColumnValue(document,StationsColumn.DISPLAY_NAME.getValue()));
			station.setZone(getColumnValue(document,StationsColumn.ZONE.getValue()));
			station.setTotalLines(getColumnValue(document,StationsColumn.TOTAL_LINES.getValue()));
			station.setRail(getColumnValue(document,StationsColumn.RAIL.getValue()));
		}
		
		logger.trace("> getAllStations(): {}",stations.size());
		return stations;
	}
	
	private String getColumnValue(DBObject document,String columnName){
		Object columnValue = document.get(LinesColumn.STATION1.getValue());
		
		if(columnValue instanceof String){
			return (String) columnValue;
		}else {
			return "";
		}
		
	}
}
