package DER.task;

import util.InternetUtil;
import DER.WindTurbine;
import Event.DERsRegistrationInfo;
import Event.VPPsInfo;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class SubscribeToSmartGrid extends Task {

	private EventHandler responseHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			VPPsInfo vppsInfo = (VPPsInfo) evt;
			
			if (vppsInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			return result;
		}

		@Override
		public void handle(Event evt) {
			VPPsInfo vppsInfo = (VPPsInfo) evt;
			
			owner.setAttribute(WindTurbine.KEY_VPP_ID,
					Integer.valueOf(vppsInfo.getVPPId()));
			
			owner.setAttribute(WindTurbine.KEY_SUBSCRIBED_TO_SG,
					Boolean.TRUE);
		}

	};
	
	public SubscribeToSmartGrid() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		subscribeToSmartGrid();
	}
	
	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				VPPsInfo.class, responseHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				VPPsInfo.class, responseHandler);
	}
	
	private void subscribeToSmartGrid() {
		int sgId
			= (Integer) owner.getAttribute(WindTurbine.KEY_SG_ID);
		
		DERsRegistrationInfo regReq
			= new DERsRegistrationInfo(owner.getId(), sgId);
		
		SimulationEngine.getSimulationService().publish(
				InternetUtil.createIPRequest(regReq));
	}
}
