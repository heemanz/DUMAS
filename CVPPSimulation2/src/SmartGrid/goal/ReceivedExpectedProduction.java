package SmartGrid.goal;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import SmartGrid.task.ReceiveExpectedProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ReceivedExpectedProduction extends Goal {

	private VPPProductionRecord vppProdRecord;
	private Set<Integer> vppIds;
	
	public ReceivedExpectedProduction() {
		super();
		
		this.add(new ReceiveExpectedProduction(), null);
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
		Date tomorrow = TimeUtil.getTomorrowMidnight(curTime);
		
		if (TimeUtil.isAfter(curTime, midnight, 23 * TimeUtil.A_HOUR) &&
			!vppProdRecord.hasExpectedProduction(vppIds, tomorrow)) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean isAchieved() {
		boolean result = false;
		
		long curTime = SimulationEngine.getSimulationService().getTime();
		Date tomorrow = TimeUtil.getTomorrowMidnight(curTime);
		
		if (vppProdRecord.hasExpectedProduction(vppIds, tomorrow)) {
			result = true;
		}
		
		return result;
	}

}
