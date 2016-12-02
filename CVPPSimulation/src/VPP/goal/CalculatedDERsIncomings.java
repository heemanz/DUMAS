package VPP.goal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import util.TimeUtil;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import VPP.task.CalculateDERsIncomings;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CalculatedDERsIncomings extends Goal {

	private DERProductionRecord derProdRecord;
	private Set<Integer> derIds;
	
	public CalculatedDERsIncomings() {
		super();
		
		add(new CalculateDERsIncomings(), null);
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
		
		@SuppressWarnings("unchecked")
		HashMap<Date, List<Double>> incomings
			= (HashMap<Date, List<Double>>) agent.getAttribute(
					VirtualPowerPlant.KEY_PRODUCTION_INCOMINGS);
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (incomings.containsKey(yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (derProdRecord.hasPayment(derIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

}
