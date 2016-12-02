package DER.task;

import util.InternetUtil;
import DER.WindTurbine;
import Event.DERsRegistrationInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class SubscribeToVPP extends Task {

	public SubscribeToVPP() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);

		subscribeToVPP();
	}
	
	@Override
	protected void registerHandlers() {
	}

	@Override
	protected void deregisterHandlers() {
	}

	private void subscribeToVPP() {
		int vppId = (Integer) owner.getAttribute(WindTurbine.KEY_VPP_ID);
		
		DERsRegistrationInfo regReq
			= new DERsRegistrationInfo(owner.getId(), vppId);
		
		SimulationEngine.getSimulationService().publish(
				InternetUtil.createIPRequest(regReq));
		
		owner.setAttribute(WindTurbine.KEY_SUBSCRIBED_TO_VPP, Boolean.TRUE);
	}
}
