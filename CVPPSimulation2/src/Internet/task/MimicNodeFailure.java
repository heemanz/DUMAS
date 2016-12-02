package Internet.task;

import java.util.Random;

import util.TimeUtil;
import Event.Restore;
import Internet.Internet;
import Internet.NodeState;
import Internet.NodeStateTable;
import Internet.Record;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;
import nise.ajou.ac.kr.simulationengine.TickHandler;

public class MimicNodeFailure extends Task {

	private Random random;
	private NodeStateTable nodeStateTable;
	
	private TickHandler tickHandler = new TickHandler() {

		@Override
		public void tick(long delta) {
			for (Integer nodeId:nodeStateTable.getNodeSet()) {
				Record record = nodeStateTable.getStateRecord(nodeId);
				
				if (record.getState() == NodeState.Active) { 
					double probToFail = record.getProbabilityToFail();
					
					boolean fail = random.nextDouble() < probToFail;
					
					if (fail) {
						record.setState(NodeState.Deactive);
					}
				}
			}
			
		}
	};
	
	private EventHandler restoreEventHandler = new EventHandler() {

		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			if (evt instanceof Restore) {
				result = true;
			}
			return result;
		}

		@Override
		public void handle(Event evt) {
			Restore restore = (Restore) evt;
			
			Record record
				= nodeStateTable.getStateRecord(
						Integer.valueOf(restore.getSenderId()));
			
			record.setState(NodeState.Active);
		}
		
	};
	
	public MimicNodeFailure() {
		super();
		
		random = new Random();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		nodeStateTable = (NodeStateTable) owner.getAttribute(
				Internet.KEY_NODE_STATE_TABLE);
	}
	
	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().notifyPeriodically(
				TimeUtil.HALF_HOUR, tickHandler);
		
		SimulationEngine.getSimulationService().subscribe(
				Restore.class, restoreEventHandler);
	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().cancelToNotify(tickHandler);
		
		SimulationEngine.getSimulationService().unsubscribe(
				Restore.class, restoreEventHandler);
	}

}
