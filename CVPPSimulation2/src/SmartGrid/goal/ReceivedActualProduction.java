package SmartGrid.goal;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import SmartGrid.task.ReceiveActualProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ReceivedActualProduction extends Goal {

	private VPPProductionRecord vppProdRecord;
	private Set<Integer> vppIds;
	
	public ReceivedActualProduction() {
		super();
		
		this.add(new ReceiveActualProduction(), null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Agent owner) {
		super.initialize(owner);
		
		vppProdRecord = (VPPProductionRecord) owner.getAttribute(
				SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
		
		vppIds = (Set<Integer>) owner.getAttribute(SmartGrid.KEY_VPPS);
	}
	
	@Override
	public void finalize() {
		super.finalize();
		
		vppProdRecord = null;
		vppIds = null;
	}
	
	@Override
	public boolean shouldPursue(Agent agent) {
		boolean result = false;
		
		VPPProductionRecord vppProdRecord = (VPPProductionRecord) agent.getAttribute(
				SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
		
		@SuppressWarnings("unchecked")
		Set<Integer> vppIds = (Set<Integer>) agent.getAttribute(SmartGrid.KEY_VPPS);
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date midnight = TimeUtil.getMidnightDate(curTime);
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (TimeUtil.isAfter(curTime, midnight, 2 * TimeUtil.A_HOUR) &&
			!vppProdRecord.hasActualProduction(vppIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date yesterday = TimeUtil.getYesterdayMidnight(curTime);
		
		if (vppProdRecord.hasActualProduction(vppIds, yesterday)) {
			result = true;
		}
		
		return result;
	}

}
