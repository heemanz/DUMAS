package SmartGrid;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VPPProductionRecord {

	private HashMap<Integer, HashMap<Date, List<Double[]>>> records;
	
	public VPPProductionRecord() {
		records = new HashMap<Integer, HashMap<Date, List<Double[]>>>();
	}

	public HashMap<Date, List<Double[]>> createVPPRecord(Integer vppId) {
		HashMap<Date, List<Double[]>> vppRecord = new HashMap<Date, List<Double[]>>();
		
		records.put(vppId, vppRecord);
		
		return vppRecord;
	}
	
	public void createVPPsDailyRecord(Integer vppId, Date date) {
		HashMap<Date, List<Double[]>> vppRecord = null;
		
		if (hasVPPRecord(vppId)) {
			vppRecord = records.get(vppId);
		}
		else {
			vppRecord = createVPPRecord(vppId);
		}
		
		List<Double[]> data = new ArrayList<Double[]>();
		
		for (int i = 0; i < 48; i++) {
			Double[] fields = new Double[3];
			
			fields[0] = null;
			fields[1] = null;
			fields[2] = null;
			
			data.add(fields);
		}
		
		vppRecord.put(date, data);
	}
	
	public Set<Integer> getVPPIds() {
		return records.keySet();
	}
	
	public boolean hasVPPRecord(Integer vppId) {
		return records.containsKey(vppId);
	}
	
	public boolean hasDailyRecord(Integer vppId, Date date) {
		return records.get(vppId).containsKey(date);
	}
	
	public Double getExpectedProduction(Integer vppId, Date date, int index) {
		return records.get(vppId).get(date).get(index)[0];
	}
	
	public void setExpectedProduction(
			Integer vppId, Date date, int index, double expectedProduction) {
		records.get(vppId).get(date).get(index)[0] = expectedProduction;
	}
	
	public Double getActualProduction(Integer vppId, Date date, int index) {
		return records.get(vppId).get(date).get(index)[1];
	}
	
	public void setActualProduction(
			Integer vppId, Date date, int index, double actualProduction) {
		records.get(vppId).get(date).get(index)[1] = actualProduction;
	}
	
	public Double getProductionCost(Integer vppId, Date date, int index) {
		return records.get(vppId).get(date).get(index)[2];
	}
	
	public void setProductionCost(
			Integer vppId, Date date, int index, double productionCost) {
		records.get(vppId).get(date).get(index)[2] = productionCost;
	}
	
	public boolean hasExpectedProduction(Set<Integer> vppIds, Date date) {
		boolean result = true;
		
		for (Integer vppId:vppIds) {
			if (records.containsKey(vppId)) {
				if (records.get(vppId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getExpectedProduction(vppId, date, i) == null) {
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
	
	public boolean hasActualProduction(Set<Integer> vppIds, Date date) {
		boolean result = true;
		
		for (Integer vppId:vppIds) {
			if (records.containsKey(vppId)) {
				if (records.get(vppId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getActualProduction(vppId, date, i) == null) {
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
	
	public boolean hasProductionCost(Set<Integer> vppIds, Date date) {
		boolean result = true;
		
		for (Integer vppId:vppIds) {
			if (records.containsKey(vppId)) {
				if (records.get(vppId).containsKey(date)) {
					for (int i = 0; i < 48; i++) {
						if (getProductionCost(vppId, date, i) == null) {
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
