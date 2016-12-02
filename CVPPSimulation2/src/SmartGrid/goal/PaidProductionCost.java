package SmartGrid.goal;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import util.TimeUtil;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import SmartGrid.task.PayProductionCost;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class PaidProductionCost extends Goal {

	private Set<Date> paidDays = null;
	
	public PaidProductionCost() {
		super();
		
		paidDays = new TreeSet<Date>();
		
		this.add(new PayProductionCost(), null);
	}

	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		VPPProductionRecord vppProdRecord = (VPPProductionRecord) agent.getAttribute(
				SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
		
		@SuppressWarnings("unchecked")
		Set<Integer> vppIds = (Set<Integer>) agent.getAttribute(SmartGrid.KEY_VPPS);
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (!paidDays.contains(yesterday) &&
			vppIds.size() > 0 &&
			vppProdRecord.hasProductionCost(vppIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		long curTime = SimulationEngine.getSimulationService().getTime();
		paidDays.add(TimeUtil.getYesterdayMidnight(curTime));
		
		return true;
	}

}
