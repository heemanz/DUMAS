package DER.task;

import java.util.ArrayList;
import java.util.Date;

import util.TimeUtil;
import DER.WindTurbine;
import DER.ProductionRecord;
import Event.ActualProductionInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class InformActualProduction extends Task {

	public InformActualProduction() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		informActualProduction();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void informActualProduction() {
		int vppId = (Integer) owner.getAttribute(WindTurbine.KEY_VPP_ID);
		
		ProductionRecord productionRecord
			= (ProductionRecord) owner.getAttribute(WindTurbine.KEY_PRODUCTION_RECORD);
		
		ArrayList<Double> productionList = new ArrayList<Double>();
		
		Date yesterday = TimeUtil.getYesterdayMidnight(
				SimulationEngine.getSimulationService().getTime());
		
		for (int i = 0; i < 48; i++) {
			double production = 0.0;
			
			try {
				production = productionRecord.getActualProduction(
						yesterday, i);
			} catch (Exception e) {

			}
			
			productionList.add(Double.valueOf(production));
		}
		
		ActualProductionInfo actualProductionInfo
			= new ActualProductionInfo(
					owner.getId(), vppId, yesterday, productionList);
		
		SimulationEngine.getSimulationService().publish(actualProductionInfo);
	}
}
