package SmartGrid.task;

import java.util.Date;

import util.TimeUtil;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CalculateProductionCost extends Task {

	public CalculateProductionCost() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		calculate();
	}
	
	@Override
	protected void registerHandlers() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void deregisterHandlers() {
		// TODO Auto-generated method stub

	}

	private void calculate() {
		Double price = (Double) owner.getAttribute(SmartGrid.KEY_PRICE);
		Double alpha = (Double) owner.getAttribute(SmartGrid.KEY_ALPHA);
		Double beta = (Double) owner.getAttribute(SmartGrid.KEY_BETA);
		
		VPPProductionRecord vppProdRecord
			= (VPPProductionRecord) owner.getAttribute(
					SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
	
		Date date = TimeUtil.getMidnightDate(
				SimulationEngine.getSimulationService().getTime()
				- TimeUtil.A_DAY);
		
		for (Integer vppId:vppProdRecord.getVPPIds()) {
			
			if (!vppProdRecord.hasDailyRecord(vppId, date)) {
				continue;
			}
			
			for (int i = 0; i < 48; i++) {
				Double expProduction
					= vppProdRecord.getExpectedProduction(vppId, date, i);
				
				if (expProduction == null) {
					expProduction = 0.0;
				}
				
				Double actProduction
					= vppProdRecord.getActualProduction(vppId, date, i);
				
				if (actProduction == null) {
					actProduction = 0.0;
				}
				
				double payment = 0.0;
				
				if (expProduction != 0.0 && actProduction != 0.0) {
					payment = 1.0
							/ (1.0 + alpha * Math.pow(
									Math.abs(expProduction - actProduction),
									beta))
							* Math.log(actProduction) * price * actProduction;
				}
				
				vppProdRecord.setProductionCost(vppId, date, i, payment);
			}
		}
	}
}
