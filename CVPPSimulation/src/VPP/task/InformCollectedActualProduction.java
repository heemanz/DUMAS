package VPP.task;

import java.util.ArrayList;
import java.util.Date;

import util.TimeUtil;
import VPP.VirtualPowerPlant;
import VPP.DERProductionRecord;
import Event.CollectedActualProductionInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class InformCollectedActualProduction extends Task {

	public InformCollectedActualProduction() {
		// TODO Auto-generated constructor stub
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		informCollectedActualProduction();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void informCollectedActualProduction() {
		int sgId = (Integer) owner.getAttribute(VirtualPowerPlant.KEY_SG_ID);
		
		DERProductionRecord derProdRecord
			= (DERProductionRecord) owner.getAttribute(
					VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		Date yesterday = TimeUtil.getYesterdayMidnight(
				SimulationEngine.getSimulationService().getTime());
		
		ArrayList<Double> productionList = new ArrayList<Double>();
		
		for (int i = 0; i < 48; i++) {
			double production = 0.0;
			
			for (Integer derId:derProdRecord.getDERIds()) {
				Double derProduction = derProdRecord.getActualProduction(
						derId, yesterday, i);
				
				if (derProduction == null) {
					derProduction = 0.0;
				}
				
				production += derProduction;
			}
			
			productionList.add(Double.valueOf(production));
		}
		
		CollectedActualProductionInfo aggActProdInfo
			= new CollectedActualProductionInfo(
					owner.getId(), sgId, yesterday, productionList);
		
		SimulationEngine.getSimulationService().publish(aggActProdInfo);
	}

}
