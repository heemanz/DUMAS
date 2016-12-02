package VPP.task;

import VPP.goal.CalculatedDERsIncomings;
import VPP.goal.DistributedDERsIncomings;
import VPP.goal.ObtainedProductionIncomings;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.StartPoint;

public class ProvideDERsIncentive extends CompositeTask {

	public ProvideDERsIncentive() {
		super();
		
		/* Activity Diagram Definition */
		StartPoint startPoint = ProvideDERsIncentive.createStartPoint();
		this.add(startPoint);
		
		Activity obtainedProductionIncomings
			= ProvideDERsIncentive.createActivity(
					new ObtainedProductionIncomings());
		this.add(obtainedProductionIncomings);
		
		startPoint.setNext(obtainedProductionIncomings, null);
		
		Activity calculatedDERsIncomings
			= ProvideDERsIncentive.createActivity(
					new CalculatedDERsIncomings());
		this.add(calculatedDERsIncomings);
		
		obtainedProductionIncomings.setNext(
				calculatedDERsIncomings, null);
		
		Activity distributedDERsIncomings
			= ProvideDERsIncentive.createActivity(
					new DistributedDERsIncomings());
		this.add(distributedDERsIncomings);
		
		calculatedDERsIncomings.setNext(
				distributedDERsIncomings, null);
	
		distributedDERsIncomings.setNext(obtainedProductionIncomings, null);
	}

}
