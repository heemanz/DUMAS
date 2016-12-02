package DER;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import util.TimeUtil;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProductionRecord {

	private HashMap<Date, List<Double[]>> records;
	private Date lastDate;
	
	public ProductionRecord() {
		records = new HashMap<Date, List<Double[]>>();
		
		generateWindSpeedForOneDay(SimulationEngine.getSimulationService().getTime());
	}

	public Double getWindSpeed(Date date, int index) {
		return records.get(date).get(index)[0];
	}

	public void setWindSpeed(Date date, int index, double windSpeed) {
		records.get(date).get(index)[0] = Double.valueOf(windSpeed);
	}
	
	public Double getExpectedProduction(Date date, int index) {
		return records.get(date).get(index)[1];
	}
	
	public void setExpectedProduction(Date date, int index, double expectedProduction) {
		records.get(date).get(index)[1] = Double.valueOf(expectedProduction);
	}
	
	public Double getActualProduction(Date date, int index) {
		return records.get(date).get(index)[2];
	}
	
	public void setActualProduction(Date date, int index, double actualProduction) {
		records.get(date).get(index)[2] = Double.valueOf(actualProduction);
	}
	
	public void generateWindSpeedForOneDay(long time) {
		Date date = TimeUtil.getMidnightDate(time);
		
		addNewDailyRecord(date);
		
		for (int i = 0; i < 48; i++) {
			double windSpeed = 2.0;// Math.random() * 4.0;
			setWindSpeed(date, i, Double.valueOf(windSpeed));
		}
	}
	
	private void addNewDailyRecord(Date date) {
		lastDate = date;
		
		List<Double[]> data = new ArrayList<Double[]>();
		
		for (int i = 0; i < 48; i++) {
			Double[] fields = new Double[3];
			fields[0] = null;
			fields[1] = null;
			fields[2] = null;
			
			data.add(fields);
		}
		
		records.put(date, data);
	}
	
	public Date getLastDate() {
		return lastDate; 
	}
}
