package SmartGrid.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.TimeUtil;
import Event.ElectricityProductionIncomings;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class PayProductionCost extends Task {

	public PayProductionCost() {
		// TODO Auto-generated constructor stub
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		payProductionCost();
	}
	
	@Override
	protected void registerHandlers() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void deregisterHandlers() {
		// TODO Auto-generated method stub

	}

	private void payProductionCost() {
		VPPProductionRecord vppProdRecord
			= (VPPProductionRecord) owner.getAttribute(
					SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
		
		Date date = TimeUtil.getYesterdayMidnight(
				SimulationEngine.getSimulationService().getTime());
		
		for (Integer vppId:vppProdRecord.getVPPIds()) {
			List<Double> payments = new ArrayList<Double>();
			
			for (int i = 0; i < 48; i++) {
				payments.add(vppProdRecord.getProductionCost(
						vppId, date, i));
			}
			
			ElectricityProductionIncomings pay
				= new ElectricityProductionIncomings(
						owner.getId(), vppId, date, payments);
			
			SimulationEngine.getSimulationService().publish(pay);
		}
	}
}
