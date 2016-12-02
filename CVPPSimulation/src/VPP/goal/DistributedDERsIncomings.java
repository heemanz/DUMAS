package VPP.goal;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import VPP.task.DistributeDERsIncomings;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class DistributedDERsIncomings extends Goal {

	public DistributedDERsIncomings() {
		super();
		
		add(new DistributeDERsIncomings(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		DERProductionRecord derProdRecord = (DERProductionRecord) agent.getAttribute(
				VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		@SuppressWarnings("unchecked")
		Set<Integer> derIds = (Set<Integer>) agent.getAttribute(VirtualPowerPlant.KEY_DERS);
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (derProdRecord.hasPayment(derIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		return true;
	}

}
