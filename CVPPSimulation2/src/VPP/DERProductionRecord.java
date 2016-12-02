package VPP;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DERProductionRecord {
	private HashMap<Integer, HashMap<Date, List<Double[]>>> records;
	
	public DERProductionRecord() {
		records = new HashMap<Integer, HashMap<Date, List<Double[]>>>();
	}

	public HashMap<Date, List<Double[]>> createDERRecord(Integer derId) {
		HashMap<Date, List<Double[]>> derRecord = new HashMap<Date, List<Double[]>>();
		
		records.put(derId, derRecord);
		
		return derRecord;
	}
	
	public void createDERsDailyRecord(Integer derId, Date date) {
		HashMap<Date, List<Double[]>> derRecord = null;
		
		if (hasDERRecord(derId)) {
			derRecord = records.get(derId);
		}
		else {
			derRecord = createDERRecord(derId);
		}
		
		List<Double[]> data = new ArrayList<Double[]>();
		
		for (int i = 0; i < 48; i++) {
			Double[] fields = new Double[3];
			
			fields[0] = null;
			fields[1] = null;
			fields[2] = null;
			
			data.add(fields);
		}
		
		derRecord.put(date, data);
	}
	
	public Set<Integer> getDERIds() {
		return records.keySet();
	}
	
	public boolean hasDERRecord(Integer derId) {
		return records.containsKey(derId);
	}
	
	public boolean hasDailyRecord(Integer derId, Date date) {
		return records.get(derId).containsKey(date);
	}
	
	public Double getExpectedProduction(Integer derId, Date date, int index) {
		Double result = null;
		
		if (!hasDailyRecord(derId, date)) {
			createDERsDailyRecord(derId, date);
		}
		
		result = records.get(derId).get(date).get(index)[0];
		
		result = (result == null) ? 0.0 : result;
		
		return result;
	}
	
	public void setExpectedProduction(
			Integer derId, Date date, int index, double expectedProduction) {
		records.get(derId).get(date).get(index)[0] = expectedProduction;
	}
	
	public Double getActualProduction(Integer derId, Date date, int index) {
		if (!hasDailyRecord(derId, date)) {
			this.createDERsDailyRecord(derId, date);
		}
		
		return records.get(derId).get(date).get(index)[1];
	}
	
	public void setActualProduction(
			Integer derId, Date date, int index, double actualProduction) {
		records.get(derId).get(date).get(index)[1] = actualProduction;
	}
	
	public Double getPayment(Integer derId, Date date, int index) {
		return records.get(derId).get(date).get(index)[2];
	}
	
	public void setPayment(
			Integer derId, Date date, int index, double payment) {
		records.get(derId).get(date).get(index)[2] = payment;
	}
	
	public boolean hasExpectedProduction(Set<Integer> derIds, Date date) {
		boolean result = true;
		
		for (Integer derId:derIds) {
			if (records.containsKey(derId)) {
				if (records.get(derId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getExpectedProduction(derId, date, i) == null) {
							result = false;
							break;
						}
					}
					if (result == false) {
						break;
					}
				}
				else {
					result = false;
					break;
				}
			}
			else {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public boolean hasActualProduction(Set<Integer> derIds, Date date) {
		boolean result = true;
		
		for (Integer derId:derIds) {
			if (records.containsKey(derId)) {
				if (records.get(derId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getActualProduction(derId, date, i) == null) {
							result = false;
							break;
						}
					}
					if (result == false) {
						break;
					}
				}
				else {
					result = false;
					break;
				}
			}
			else {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public boolean hasPayment(Set<Integer> derIds, Date date) {
		boolean result = true;
		
		for (Integer derId:derIds) {
			if (records.containsKey(derId)) {
				if (records.get(derId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getPayment(derId, date, i) == null) {
							result = false;
							break;
						}
					}
					if (result == false) {
						break;
					}
				}
				else {
					result = false;
					break;
				}
			}
			else {
				result = false;
				break;
			}
		}
		return result;
	}
}
