package DER.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import util.InternetUtil;
import util.TimeUtil;
import DER.WindTurbine;
import DER.ProductionRecord;
import Event.ExpectedProductionInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class InformExpectedProduction extends Task {

	public InformExpectedProduction() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		informExpectedProduction();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void informExpectedProduction() {
		int vppId = (Integer) owner.getAttribute(WindTurbine.KEY_VPP_ID);
		
		ProductionRecord productionRecord
			= (ProductionRecord) owner.getAttribute(WindTurbine.KEY_PRODUCTION_RECORD);
		
		double sigmaRes = (double) owner.getAttribute(WindTurbine.KEY_SIGMA_RES);
		
		ArrayList<Double> expectedProductionList = new ArrayList<Double>();
		
		Date tomorrow = TimeUtil.getTomorrowMidnight(
				SimulationEngine.getSimulationService().getTime());
		
		for (int i = 0; i < 48; i++) {
			double wt = productionRecord.getWindSpeed(tomorrow, i);
			
			if (wt == Double.NaN) {
				wt = 0.0;
			}
			
			double prodGeneric = 700.0
					/ (1 + Math.pow(Math.E, 0.66 * (9 - wt)));
			double NOfOneAndSigmaRes = 1.0
					+ (new Random()).nextGaussian() * sigmaRes * sigmaRes;
			
			double expectedProduction = prodGeneric * NOfOneAndSigmaRes;
			productionRecord.setExpectedProduction(
					tomorrow, i, expectedProduction);
			
			expectedProductionList.add(Double.valueOf(expectedProduction));
		}
		
		ExpectedProductionInfo expectedProductionInfo
			= new ExpectedProductionInfo(
					owner.getId(), vppId, tomorrow, expectedProductionList);
		
		SimulationEngine.getSimulationService().publish(
				InternetUtil.createIPRequest(expectedProductionInfo));
	}
}
