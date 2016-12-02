package VPP.task;

import java.util.Date;
import java.util.Set;

import util.TimeUtil;
import Event.DERsRegistrationInfo;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.roch.Organization;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProcessDERsSubscription extends Task {

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
			Set<Integer> ders
				= (Set<Integer>) owner.getAttribute(VirtualPowerPlant.KEY_DERS);
			
			ders.add(regInfo.getSenderId());
			((Organization) owner).add(Integer.valueOf(regInfo.getSenderId()));
			
			DERProductionRecord derProdRecord
				= (DERProductionRecord) owner.getAttribute(
						VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
			
			Date date = TimeUtil.getMidnightDate(
					SimulationEngine.getSimulationService().getTime());
			derProdRecord.createDERsDailyRecord(regInfo.getSenderId(), date);
		}

	};
	
	public ProcessDERsSubscription() {
		super();
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				DERsRegistrationInfo.class, dersRegistrationHandler);
	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				DERsRegistrationInfo.class, dersRegistrationHandler);
	}

}
