package VPP.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import DER.goal.InformedExpectedProduction;
import Event.ExpectedProductionInfo;
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

public class CollectExpectedProduction extends OrganizationalTask {

	private EventHandler expectedProductionInfoHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			ExpectedProductionInfo expProdInfo
				= (ExpectedProductionInfo) evt;
				
			if (expProdInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			ExpectedProductionInfo expProdInfo
				= (ExpectedProductionInfo) evt;
			
			DERProductionRecord derProdRecord
				= (DERProductionRecord) owner.getAttribute(
						VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
			
			if (!derProdRecord.hasDailyRecord(
					expProdInfo.getSenderId(),	expProdInfo.getDate())) {
				derProdRecord.createDERsDailyRecord(
						expProdInfo.getSenderId(), expProdInfo.getDate());
			}
			
			for (int i = 0; i < 48; i++) {
				double expProduction = expProdInfo.getExpectationList().get(i);
				derProdRecord.setExpectedProduction(
						Integer.valueOf(expProdInfo.getSenderId()),
						expProdInfo.getDate(), i, expProduction);
			}
			
		}
	};
	
	public CollectExpectedProduction() {
		super();
		
		/* Role definition */
		Role der = new DER();
		
		Goal informedExpectedProduction = new InformedExpectedProduction();
		
		der.add(informedExpectedProduction);
		
		this.add(der);
		
		/* Activity Diagram Definition */
		StartPoint startPoint = CollectExpectedProduction.createStartPoint();
		this.add(startPoint);
		
		Activity activity = CollectExpectedProduction.createActivity(
				informedExpectedProduction);
		this.add(activity);
		
		startPoint.setNext(activity, null);
		
		EndPoint endPoint = CollectExpectedProduction.createEndPoint();
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
				ExpectedProductionInfo.class, expectedProductionInfoHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				ExpectedProductionInfo.class, expectedProductionInfoHandler);
	}
}
