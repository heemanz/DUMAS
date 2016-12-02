package DER.task;

import Event.ElectricityProductionIncomings;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ReceiveProductionCost extends Task {

	private EventHandler incomingsHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			ElectricityProductionIncomings incomings = (ElectricityProductionIncomings) evt;
				
			if (incomings.getReceiverId() == owner.getId()) {
				result = true;
			}
			return result;
		}

		@Override
		public void handle(Event evt) {
			ElectricityProductionIncomings incomings = (ElectricityProductionIncomings) evt;
			
			double totalIncomings = 0.0;
			
			for (int i = 0; i < 48; i++) {
				//System.out.println("DER[" + owner.getId() + "] at [" + i + "] incomings: " + incomings.getPrices().get(i));
				totalIncomings += incomings.getPrices().get(i);
			}
			
			//System.out.println(" DER[" + owner.getId() + "] totol incomings: " + totalIncomings);
		}
	};
			
	public ReceiveProductionCost() {
		super();
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(ElectricityProductionIncomings.class, incomingsHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(ElectricityProductionIncomings.class, incomingsHandler);
	}

}
