package SmartGrid.task;

import SmartGrid.goal.CalculatedProductionCost;
import SmartGrid.goal.PaidProductionCost;
import SmartGrid.goal.ReceivedActualProduction;
import SmartGrid.goal.ReceivedExpectedProduction;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public class ProvideProductionCost extends CompositeTask {

	public ProvideProductionCost() {
		super();
		
		/* Activity Diagram Definition */
		StartPoint startPoint = CompositeTask.createStartPoint();
		this.add(startPoint);
		
		Activity receivedActualProduction = CompositeTask.createActivity(
				new ReceivedActualProduction());
		this.add(receivedActualProduction);
		
		startPoint.setNext(receivedActualProduction, null);
		
		Activity calculatedProductionCost = CompositeTask.createActivity(
				new CalculatedProductionCost());
		this.add(calculatedProductionCost);
		
		receivedActualProduction.setNext(calculatedProductionCost, null);
		
		Activity paidProductionCost = CompositeTask.createActivity(
				new PaidProductionCost());
		this.add(paidProductionCost);
		
		calculatedProductionCost.setNext(paidProductionCost, null);
		
		Activity receivedExpectedProduction = CompositeTask.createActivity(
				new ReceivedExpectedProduction());
		this.add(receivedExpectedProduction);
		
		paidProductionCost.setNext(receivedExpectedProduction, null);
		
		receivedExpectedProduction.setNext(receivedActualProduction, null);
	}

}
