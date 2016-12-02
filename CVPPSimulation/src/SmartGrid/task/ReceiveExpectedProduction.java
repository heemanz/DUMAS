package SmartGrid.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import Event.CollectedExpectedProductionInfo;
import SmartGrid.SmartGrid;
import SmartGrid.VPPProductionRecord;
import SmartGrid.role.VPP;
import VPP.goal.InformedCollectedExpectedProduction;
import nise.ajou.ac.kr.roch.Goal;
import nise.ajou.ac.kr.roch.OrganizationalTask;
import nise.ajou.ac.kr.roch.Role;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.StartPoint;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ReceiveExpectedProduction extends OrganizationalTask {

	private EventHandler expectedProductionInfoHandler = new EventHandler() {
		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			CollectedExpectedProductionInfo expProdInfo
				= (CollectedExpectedProductionInfo) evt;
				
			if (expProdInfo.getReceiverId() == owner.getId()) {
				result = true;
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			CollectedExpectedProductionInfo expProdInfo
				= (CollectedExpectedProductionInfo) evt;
			
			VPPProductionRecord vppProdRecord
				= (VPPProductionRecord) owner.getAttribute(
						SmartGrid.KEY_VPP_PRODUCTION_RECORDS);
			
			if (!vppProdRecord.hasDailyRecord(
					expProdInfo.getSenderId(),	expProdInfo.getDate())) {
				vppProdRecord.createVPPsDailyRecord(
						expProdInfo.getSenderId(), expProdInfo.getDate());
			}
			
			for (int i = 0; i < 48; i++) {
				double expProduction = expProdInfo.getExpectationList().get(i);
				vppProdRecord.setExpectedProduction(
						Integer.valueOf(expProdInfo.getSenderId()),
						expProdInfo.getDate(), i, expProduction);
			}
			
		}
	};
	
	public ReceiveExpectedProduction() {
		super();
		
		Role vpp = new VPP();
		
		Goal informedCollectedExpectedProduction = new InformedCollectedExpectedProduction();
		
		vpp.add(informedCollectedExpectedProduction);
		
		this.add(vpp);
		
		StartPoint startPoint = ReceiveExpectedProduction.createStartPoint();
		this.add(startPoint);
		
		Activity activity = ReceiveExpectedProduction.createActivity(
				informedCollectedExpectedProduction);
		this.add(activity);
		
		startPoint.setNext(activity, null);
		
		EndPoint endPoint = ReceiveExpectedProduction.createEndPoint();
		this.add(endPoint);
		
		activity.setNext(endPoint, null);
	}
	
	@Override
	public HashMap<Role, List<Integer>> assignRolesTo(Set<Integer> members) {
		HashMap<Role, List<Integer>> rolePlayers = new HashMap<Role, List<Integer>>();
		
		@SuppressWarnings("unchecked")
		Set<Integer> registeredVPPs = (Set<Integer>) owner.getAttribute(SmartGrid.KEY_VPPS);
		
		List<Role> roles = this.getRoles();
		
		for (Role role:roles) {
			if (role instanceof VPP) {
				List<Integer> vpps = new ArrayList<Integer>();
				
				for (Integer memberId:members) {
					if (registeredVPPs.contains(memberId)) {
						vpps.add(memberId);
					}
				}
				
				rolePlayers.put(role, vpps);
			}
		}
		
		return rolePlayers;
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(CollectedExpectedProductionInfo.class, expectedProductionInfoHandler);

	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(CollectedExpectedProductionInfo.class, expectedProductionInfoHandler);
	}
}
