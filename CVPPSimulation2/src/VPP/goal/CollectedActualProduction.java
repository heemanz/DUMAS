package VPP.goal;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import VPP.task.CollectActualProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CollectedActualProduction extends Goal {

	private DERProductionRecord derProdRecord;
	private Set<Integer> derIds;
	
	public CollectedActualProduction() {
		super();
		
		add(new CollectActualProduction(), null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Agent owner) {
		super.initialize(owner);
		
		derProdRecord = (DERProductionRecord) owner.getAttribute(
				VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		derIds = (Set<Integer>) owner.getAttribute(VirtualPowerPlant.KEY_DERS);
	}
	
	@Override
	public void finalize() {
		super.finalize();
		
		derProdRecord = null;
		derIds = null;
	}
	
	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		DERProductionRecord derProdRecord
			= (DERProductionRecord) agent.getAttribute(
					VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		@SuppressWarnings("unchecked")
		Set<Integer> derIds = (Set<Integer>) agent.getAttribute(
				VirtualPowerPlant.KEY_DERS);
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date midnight = TimeUtil.getMidnightDate(curTime);
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (TimeUtil.isAfter(curTime, midnight, 1 * TimeUtil.A_HOUR) &&
			!derProdRecord.hasActualProduction(derIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date midnight = TimeUtil.getMidnightDate(curTime);
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (TimeUtil.isAfter(
				curTime, midnight, TimeUtil.A_HOUR + 50 * TimeUtil.A_MINUTE) ||
			derProdRecord.hasActualProduction(derIds, yesterday)) {
			result = true;
		}
		
		return result;
	}
}
