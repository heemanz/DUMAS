package VPP.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Event.ElectricityProductionIncomings;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ObtainIncomingsFromSmartGrid extends Task {

	private EventHandler prodIncomingsInfoHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			ElectricityProductionIncomings prodIncomings
				= (ElectricityProductionIncomings) evt;
				
			if (prodIncomings.getReceiverId() == owner.getId()) {
				result = true;
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			ElectricityProductionIncomings prodIncomings
				= (ElectricityProductionIncomings) evt;
			
			@SuppressWarnings("unchecked")
			HashMap<Date, List<Double>> incomings
				= (HashMap<Date, List<Double>>) owner.getAttribute(
						VirtualPowerPlant.KEY_PRODUCTION_INCOMINGS);
			
			incomings.put(prodIncomings.getDate(),
					prodIncomings.getPrices());
			
			double totalIncomings = 0.0;
			
			for (int i = 0; i < 48; i++) {
				//System.out.println(prodIncomings.getDate().toString() + " VPP[" + owner.getId() + "] at [" + i + "] incomings: " + prodIncomings.getPrices().get(i));
				totalIncomings += prodIncomings.getPrices().get(i);
			}
			
			//System.out.println(" VPP[" + owner.getId() + "] totol incomings: " + totalIncomings);
			if (days++ < 740) {
				System.out.println(totalIncomings);
			}
		}
		
	};
	
	static int days = 0;
	
	public ObtainIncomingsFromSmartGrid() {
		super();
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				ElectricityProductionIncomings.class,
				prodIncomingsInfoHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				ElectricityProductionIncomings.class,
				prodIncomingsInfoHandler);
	}
}
