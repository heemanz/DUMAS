package Internet.task;

import Event.IPRequest;
import Event.IPResponse;
import Internet.Internet;
import Internet.NodeState;
import Internet.NodeStateTable;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProcessRequest extends Task {

	private NodeStateTable nodeStateTable;
	
	EventHandler reqHandler = new EventHandler() {

		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			if (evt instanceof IPRequest) {
				IPRequest request = (IPRequest) evt;
				
				if (!request.throughInternet()) {
					result = true;
				}
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			IPRequest ipRequest = (IPRequest) evt;
			
			processRequest(ipRequest);
		}
		
		private void processRequest(IPRequest request) {
			int senderId = request.getSenderId();
			int receiverId = request.getReceiverId();
			
			if (nodeStateTable.getAgentNodeState(senderId)
					== NodeState.Deactive) {
				IPResponse response
					= new IPResponse(
							request.getReceiverId(),
							request.getSenderId(),
							IPResponse.CONNECTION_ERROR,
							null);

				response.setThroughInternet();

				SimulationEngine.getSimulationService().publish(response);
			}
			else if (nodeStateTable.getAgentNodeState(receiverId)
					== NodeState.Deactive) {
				IPResponse response
					= new IPResponse(
							request.getReceiverId(),
							request.getSenderId(),
							IPResponse.CANNOT_CONNECT_TO_SERVER,
							null);

				response.setThroughInternet();

				SimulationEngine.getSimulationService().publish(response);
			}
			else {
				IPRequest localRequest = new IPRequest(
						request.getSenderId(),
						request.getReceiverId(),
						request.getBody());
				
				localRequest.setThroughInternet();
				
				SimulationEngine.getSimulationService().publish(localRequest);
			}
		}
	};
	
	EventHandler rspHandler = new EventHandler() {

		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			if (evt instanceof IPResponse) {
				IPResponse response = (IPResponse) evt;
				
				if (!response.throughInternet()) {
					result = true;
				}
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			IPResponse response = (IPResponse) evt;
				
			processResponse(response);
		}

		private void processResponse(IPResponse response) {
			int receiverId = response.getReceiverId();
			
			if (nodeStateTable.getAgentNodeState(receiverId)
					== NodeState.Active) {
				response.setThroughInternet();
				
				SimulationEngine.getSimulationService().publish(response);
			}
		}
	};
	
	public ProcessRequest() {
		super();
	}

	public void initialize(Agent owner) {
		super.initialize(owner);
		
		nodeStateTable = (NodeStateTable) owner.getAttribute(
				Internet.KEY_NODE_STATE_TABLE);
	}
	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				IPRequest.class, reqHandler);
		SimulationEngine.getSimulationService().subscribe(
				IPResponse.class, rspHandler);
	}

	@Override
	protected void deregisterHandlers() {
		SimulationEngine.getSimulationService().unsubscribe(
				IPRequest.class, reqHandler);
		SimulationEngine.getSimulationService().unsubscribe(
				IPResponse.class, rspHandler);
	}

}
