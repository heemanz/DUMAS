package nise.ajou.ac.kr.roch;

import java.util.ArrayList;
import java.util.List;

import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.Decision;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.Node;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public abstract class CompositeTask extends Task {

	protected StartPoint startPoint;
	protected EndPoint endPoint;
	
	protected List<Node> nodes;

	public CompositeTask() {
		nodes = new ArrayList<Node>();
	}
		
	@Override
	protected void registerHandlers() {
		
	}
	
	@Override
	protected void deregisterHandlers() {
		
	}
	
	public void add(Node node) {
		if (node instanceof StartPoint) {
			this.startPoint = (StartPoint) node;
		}
		
		if (node instanceof EndPoint) {
			this.endPoint = (EndPoint) node;
		}
		
		nodes.add(node);
	}
	
	public static Activity createActivity(Goal goal) {
		Activity activity = new Activity(goal);
		
		return activity;
	}
	
	public static Decision createDecision() {
		Decision decision = new Decision();
		
		return decision;
	}
	
	public static EndPoint createEndPoint() {
		EndPoint ep = new EndPoint();
		
		return ep;
	}
	
	public static StartPoint createStartPoint() {
		StartPoint sp = new StartPoint();
		
		return sp;
	}

	public Node getStartPoint() {
		return startPoint;
	}
	
	public Node getEndPoint() {
		return endPoint;
	}
}
