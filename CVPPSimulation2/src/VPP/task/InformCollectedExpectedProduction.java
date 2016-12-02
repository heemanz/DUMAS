package VPP.task;

import java.util.ArrayList;
import java.util.Date;

import util.InternetUtil;
import util.TimeUtil;
import Event.CollectedExpectedProductionInfo;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class InformCollectedExpectedProduction extends Task {

	public InformCollectedExpectedProduction() {
		// TODO Auto-generated constructor stub
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		informCollectedExpectedProduction();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void informCollectedExpectedProduction() {
		int sgId = (Integer) owner.getAttribute(VirtualPowerPlant.KEY_SG_ID);
		
		DERProductionRecord derProdRecord
			= (DERProductionRecord) owner.getAttribute(
					VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		Date tomorrow = TimeUtil.getTomorrowMidnight(
				SimulationEngine.getSimulationService().getTime());
		
		ArrayList<Double> expProdList = new ArrayList<Double>();
		
		for (int i = 0; i < 48; i++) {
			double expProduction = 0.0;
			
			for (Integer derId:derProdRecord.getDERIds()) {
				double derExpProd = derProdRecord.getExpectedProduction(
						derId, tomorrow, i);
				
				if (derExpProd == Double.NaN) {
					derExpProd = 0.0;
				}
				
				expProduction += derExpProd;
			}
			
			expProdList.add(Double.valueOf(expProduction));
		}
		
		CollectedExpectedProductionInfo aggExpProdInfo
			= new CollectedExpectedProductionInfo(
					owner.getId(), sgId, tomorrow, expProdList);
		
		SimulationEngine.getSimulationService().publish(
				InternetUtil.createIPRequest(aggExpProdInfo));
	}
}
