package VPP.goal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import util.TimeUtil;
import VPP.VirtualPowerPlant;
import VPP.task.ObtainIncomingsFromSmartGrid;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ObtainedProductionIncomings extends Goal {

	HashMap<Date, List<Double>> incomings;

	public ObtainedProductionIncomings() {
		super();
		
		add(new ObtainIncomingsFromSmartGrid(), null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Agent owner) {
		super.initialize(owner);
		
		incomings = (HashMap<Date, List<Double>>) owner.getAttribute(
				VirtualPowerPlant.KEY_PRODUCTION_INCOMINGS);
	}
	
	@Override
	public void finalize() {
		super.finalize();
		
		incomings = null;
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
		
		if (!incomings.containsKey(yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (incomings.containsKey(yesterday)) {
			result = true;
		}
		
		return result;
	}

}
