package Internet.app.task;

import org.javatuples.Pair;

import util.InternetUtil;
import Event.IPResponse;
import Internet.app.goal.HandledConnectionError;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.FitnessFunction;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Decision;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public class HandleInternetError extends CompositeTask {

	public HandleInternetError() {
		super();
	
		/* Activity Diagram Definition */
		StartPoint startPoint = createStartPoint();
		add(startPoint);
		
		Decision isConnectionError = createDecision();
		add(isConnectionError);
		
		startPoint.setNext(isConnectionError, null);
		
		Activity handledConnectionError
			= createActivity(new HandledConnectionError());
		add(handledConnectionError);
		
		isConnectionError.addNext(new Pair<Node, FitnessFunction>(
				handledConnectionError,
				new FitnessFunction() {
					@Override
					public double evaluate(Agent agent) {
						double result = 0.0;
						
						if (agent.getAttribute(
								InternetUtil.KEY_INTERNET_ERROR)
							!= null) {
							IPResponse response
								= (IPResponse) agent.getAttribute(
										InternetUtil.KEY_INTERNET_ERROR);
							
							if (response.getResultCode()
								== IPResponse.CONNECTION_ERROR) {
								result = 1.0;
							}
						}
						
						return result;
					}
				}));
		
		isConnectionError.addNext(new Pair<Node, FitnessFunction>(
				endPoint,
				new FitnessFunction() {
					@Override
					public double evaluate(Agent agent) {
						double result = 0.0;
						
						if (agent.getAttribute(InternetUtil.KEY_INTERNET_ERROR)
							!= null) {
							IPResponse response
								= (IPResponse) agent.getAttribute(
										InternetUtil.KEY_INTERNET_ERROR);
							
							if (response.getResultCode()
								== IPResponse.CANNOT_CONNECT_TO_SERVER) {
								result = 1.0;
							}
						}
						
						return result;
					}
				}));
		
		EndPoint endPoint = createEndPoint();
		add(endPoint);
		
		handledConnectionError.setNext(endPoint, null);
	}

}
