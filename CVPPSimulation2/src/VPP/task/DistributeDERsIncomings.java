package VPP.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.InternetUtil;
import util.TimeUtil;
import Event.ElectricityProductionIncomings;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class DistributeDERsIncomings extends Task {

	public DistributeDERsIncomings() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		distributedDERsIncomings();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void distributedDERsIncomings() {
		DERProductionRecord derProdRecord
			= (DERProductionRecord) owner.getAttribute(
					VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		long time = SimulationEngine.getSimulationService().getTime()
				- TimeUtil.A_DAY;
		
		Date yesterday = TimeUtil.getMidnightDate(time);
		
		for (Integer derId:derProdRecord.getDERIds()) {
			List<Double> payments = new ArrayList<Double>();
			
			for (int i = 0; i < 48; i++) {
				double payment = derProdRecord.getPayment(derId, yesterday, i);
				payments.add(payment);
			}
			
			ElectricityProductionIncomings pay
				= new ElectricityProductionIncomings(
						owner.getId(), derId, yesterday, payments);
			
			SimulationEngine.getSimulationService().publish(
					InternetUtil.createIPRequest(pay));
		}
	}
}
