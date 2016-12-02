package Internet.app.task;

import org.javatuples.Pair;

import util.InternetUtil;
import Event.IPRequest;
import Event.IPResponse;
import Internet.app.goal.HandledInternetError;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.FitnessFunction;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Decision;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.activity.StartPoint;
import nise.ajou.ac.kr.simulationengine.Event;
import nise.ajou.ac.kr.simulationengine.EventHandler;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class ProcessInternetMessage extends CompositeTask {

	private EventHandler receivingReqEventhandler = new EventHandler() {

		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			if (evt instanceof IPRequest) {
				IPRequest request = (IPRequest) evt;
				if (request.throughInternet() &&
					request.getReceiverId() == owner.getId()) {
					result = true;
				}
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			IPRequest request = (IPRequest) evt;
			
			SimulationEngine.getSimulationService().publish(request.getBody());
		}
		
	};
	
	private EventHandler receivingRspEventhandler = new EventHandler() {

		@Override
		public boolean canProcess(Event evt) {
			boolean result = false;
			
			if (evt instanceof IPResponse) {
				IPResponse response = (IPResponse) evt;
				if (response.throughInternet() &&
					response.getReceiverId() == owner.getId()) {
					result = true;
				}
			}
			
			return result;
		}

		@Override
		public void handle(Event evt) {
			IPResponse response = (IPResponse) evt;
			
			if (response.getResultCode() != IPResponse.SUCCESS) {
				if (response.getResultCode()
					== IPResponse.CONNECTION_ERROR) {
					owner.setAttribute(
							InternetUtil.KEY_INTERNET_ERROR, response);
				}
				return;
			}
			
			SimulationEngine.getSimulationService().publish(response.getBody());
		}
		
	};
	
	public ProcessInternetMessage() {
		super();
		
		StartPoint startPoint = createStartPoint();
		add(startPoint);
		
		Decision doesErrorOccur = createDecision();
		add(doesErrorOccur);
		
		startPoint.setNext(doesErrorOccur, null);
		
		doesErrorOccur.addNext(new Pair<Node, FitnessFunction>(
				startPoint,
				new FitnessFunction() {
					@Override
					public double evaluate(Agent agent) {
						double result = 0.0;
						
						if (agent.getAttribute(
								InternetUtil.KEY_INTERNET_ERROR)
							== null) {
							result = 1.0;
						}
						
						return result;
					}
				}));
		
		Activity handledInternetError
			= createActivity(new HandledInternetError());
		add(handledInternetError);
		
		doesErrorOccur.addNext(new Pair<Node, FitnessFunction>(
				handledInternetError,
				new FitnessFunction() {
					@Override
					public double evaluate(Agent agent) {
						double result = 0.0;
						
						if (agent.getAttribute(
								InternetUtil.KEY_INTERNET_ERROR)
							!= null) {
							result = 1.0;
						}
						
						return result;
					}
				}));
		
		handledInternetError.setNext(startPoint, null);
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().subscribe(
				IPRequest.class, receivingReqEventhandler);
		SimulationEngine.getSimulationService().subscribe(
				IPResponse.class, receivingRspEventhandler);
	}

	@Override
	protected void deregisterHandlers() {		
		SimulationEngine.getSimulationService().unsubscribe(
				IPRequest.class, receivingReqEventhandler);
		SimulationEngine.getSimulationService().unsubscribe(
				IPResponse.class, receivingRspEventhandler);
	}

}
