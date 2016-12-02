package VPP.task;

import VPP.VirtualPowerPlant;
import Event.VPPsRegistrationInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class SubscribeToSmartGrid extends Task {

	public SubscribeToSmartGrid() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		subscribeToSmartGrid();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void subscribeToSmartGrid() {
		int sgId
			= (Integer) owner.getAttribute(VirtualPowerPlant.KEY_SG_ID);
		
		VPPsRegistrationInfo regReq
			= new VPPsRegistrationInfo(owner.getId(), sgId);
		
		SimulationEngine.getSimulationService().publish(regReq);
		
		owner.setAttribute(VirtualPowerPlant.KEY_SUBSCRIBED_TO_SG,
				Boolean.TRUE);
	}
}
