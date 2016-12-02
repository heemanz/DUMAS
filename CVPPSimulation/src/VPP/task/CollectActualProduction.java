package VPP.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import DER.goal.InformedActualProduction;
import Event.ActualProductionInfo;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import VPP.role.DER;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.OrganizationalTask;
import nise.ajou.ac.kr.roch.Role;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.StartPoint;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CollectActualProduction extends OrganizationalTask {

	private EventHandler productionInfoHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			ActualProductionInfo prodInfo
				= (ActualProductionInfo) evt;
				
			if (prodInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			ActualProductionInfo prodInfo
				= (ActualProductionInfo) evt;
			
			DERProductionRecord derProdRecord
				= (DERProductionRecord) owner.getAttribute(
						VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
			
			if (!derProdRecord.hasDailyRecord(
					prodInfo.getSenderId(),	prodInfo.getDate())) {
				derProdRecord.createDERsDailyRecord(
						prodInfo.getSenderId(), prodInfo.getDate());
			}
			
			for (int i = 0; i < 48; i++) {
				double production = prodInfo.getProductionList().get(i);
				derProdRecord.setActualProduction(
						Integer.valueOf(prodInfo.getSenderId()),
						prodInfo.getDate(), i, production);
			}
			
		}
	};
	
	public CollectActualProduction() {
		super();
		
		/* Role definition */
		Role der = new DER();
		
		Goal informedActualProduction = new InformedActualProduction();
		
		der.add(informedActualProduction);
		
		this.add(der);
		
		/* Activity Diagram Definition */
		StartPoint startPoint = CollectActualProduction.createStartPoint();
		this.add(startPoint);
		
		Activity activity = CollectActualProduction.createActivity(
				informedActualProduction);
		this.add(activity);
		
		startPoint.setNext(activity, null);
		
		EndPoint endPoint = CollectActualProduction.createEndPoint();
		this.add(endPoint);
		
		activity.setNext(endPoint, null);
	}

	@Override
	public HashMap<Role, List<Integer>> assignRolesTo(Set<Integer> members) {
		HashMap<Role, List<Integer>> rolePlayers
			= new HashMap<Role, List<Integer>>();
		
		@SuppressWarnings("unchecked")
		Set<Integer> registeredDERs
			= (Set<Integer>) owner.getAttribute(VirtualPowerPlant.KEY_DERS);
		
		List<Role> roles = this.getRoles();
		
		for (Role role:roles) {
			if (role instanceof DER) {
				List<Integer> ders = new ArrayList<Integer>();
				
				for (Integer memberId:members) {
					if (registeredDERs.contains(memberId)) {
						ders.add(memberId);
					}
				}
				
				rolePlayers.put(role, ders);
			}
		}
		
		return rolePlayers;
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				ActualProductionInfo.class, productionInfoHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				ActualProductionInfo.class, productionInfoHandler);
	}
}
