package SmartGrid.task;

import java.util.Set;

import SmartGrid.SmartGrid;
import Event.DERsRegistrationInfo;
import Event.VPPsInfo;
import nise.ajou.ac.kr.roch.Organization;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProcessDERsRegistration extends Task {

	private EventHandler dersRegistrationHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			DERsRegistrationInfo regInfo = (DERsRegistrationInfo) evt;
			
			if (regInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			return result;
		}

		@Override
		public void handle(Event evt) {
			DERsRegistrationInfo regInfo = (DERsRegistrationInfo) evt;
			
			@SuppressWarnings("unchecked")
			Set<Integer> ders = (Set<Integer>) owner.getAttribute(SmartGrid.KEY_DERS);
			
			ders.add(regInfo.getSenderId());
			((Organization) owner).add(Integer.valueOf(regInfo.getSenderId()));
			
			@SuppressWarnings("unchecked")
			Set<Integer> vpps = (Set<Integer>) owner.getAttribute(SmartGrid.KEY_VPPS);
			
			if (!vpps.isEmpty()) {
				VPPsInfo vppsInfo = new VPPsInfo(owner.getId(),	regInfo.getSenderId(), vpps.iterator().next());
				
				SimulationEngine.getSimulationService().publish(vppsInfo);
			}
		}

	};
	
	public ProcessDERsRegistration() {
		super();
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(DERsRegistrationInfo.class, dersRegistrationHandler);
	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(DERsRegistrationInfo.class, dersRegistrationHandler);
	}

}
