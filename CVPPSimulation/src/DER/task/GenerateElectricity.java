package DER.task;

import java.util.Date;
import java.util.Random;

import util.TimeUtil;
import DER.WindTurbine;
import DER.ProductionRecord;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;
import nise.ajou.ac.kr.simulationengine.TickHandler;

public class GenerateElectricity extends Task {

	private ProductionRecord productionRecord;
	private Random random;
	private double sigmaSyst;
	
	private TickHandler halfHourHandler = new TickHandler() {

		@Override
		public void tick(long delta) {
			long currentTime = SimulationEngine.getSimulationService().getTime();
			
			long lastTime = productionRecord.getLastDate().getTime();
			
			if (currentTime > lastTime) {
				productionRecord.generateWindSpeedForOneDay(lastTime + TimeUtil.A_DAY);
			}

			Date midnightDate = TimeUtil.getMidnightDate(currentTime);
			int index = (int) ((currentTime - midnightDate.getTime())
					/ TimeUtil.HALF_HOUR);
			
			double wt = productionRecord.getWindSpeed(midnightDate, index);
			
			double prodGeneric = 700.0 / (1 + Math.pow(Math.E, 0.66 * (9 - wt)));
			double NOfOneAndSigmaSyst = 1.0 + random.nextGaussian() * sigmaSyst * sigmaSyst;
			
			double production = prodGeneric * NOfOneAndSigmaSyst;
			
			productionRecord.setActualProduction(midnightDate, index, production);
			
			//System.out.println("DER[" + owner.getId() + "] at[" + index + "], windspeed: " + wt + ", expProduction: " + productionRecord.getExpectedProduction(midnightDate, index) + ", production: " + production);
		}
	};
	
	public GenerateElectricity() {
		super();
		
		random = new Random();
		sigmaSyst = 0.0;
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		productionRecord = (ProductionRecord) owner.getAttribute(WindTurbine.KEY_PRODUCTION_RECORD);
		sigmaSyst = (double) owner.getAttribute(WindTurbine.KEY_SIGMA_SYST);
	}
	
	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().notifyPeriodically(TimeUtil.HALF_HOUR, halfHourHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().cancelToNotify(halfHourHandler);
	}

}
