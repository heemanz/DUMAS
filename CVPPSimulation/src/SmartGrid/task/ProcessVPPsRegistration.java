package SmartGrid.task;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import Event.VPPsRegistrationInfo;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import nise.ajou.ac.kr.roch.Organization;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProcessVPPsRegistration extends Task {

	private EventHandler vppsRegistrationHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			VPPsRegistrationInfo regInfo = (VPPsRegistrationInfo) evt;
			
			if (regInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			return result;
		}

		@Override
		public void handle(Event evt) {
			VPPsRegistrationInfo regInfo = (VPPsRegistrationInfo) evt;
			
			@SuppressWarnings("unchecked")
			Set<Integer> vpps = (Set<Integer>) owner.getAttribute(SmartGrid.KEY_VPPS);
			
			vpps.add(regInfo.getSenderId());
			((Organization) owner).add(Integer.valueOf(regInfo.getSenderId()));
			
			VPPProductionRecord vppProdRecord
			= (VPPProductionRecord) owner.getAttribute(
					SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
			
			Date date = TimeUtil.getMidnightDate(
					SimulationEngine.getSimulationService().getTime());
			
			vppProdRecord.createVPPsDailyRecord(regInfo.getSenderId(), date);
		}

	};
	
	public ProcessVPPsRegistration() {
		super();
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(VPPsRegistrationInfo.class, vppsRegistrationHandler);
	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(VPPsRegistrationInfo.class, vppsRegistrationHandler);
	}

}
