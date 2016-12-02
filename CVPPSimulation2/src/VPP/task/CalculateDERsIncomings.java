package VPP.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import util.TimeUtil;
import VPP.DERProductionRecord;
import VPP.VirtualPowerPlant;
import VPP.goal.CollectedActualProduction;
import VPP.goal.CollectedExpectedProduction;
import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.CompositeTask;
import nise.ajou.ac.kr.roch.activity.Activity;
import nise.ajou.ac.kr.roch.activity.EndPoint;
import nise.ajou.ac.kr.roch.activity.StartPoint;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CalculateDERsIncomings extends CompositeTask {

	public CalculateDERsIncomings() {
		super();
		
		/* Activity Diagram Definition */
		StartPoint startPoint = ProvideDERsIncentive.createStartPoint();
		this.add(startPoint);
		
		Activity collectedExpectedProduction
			= ProvideDERsIncentive.createActivity(
					new CollectedExpectedProduction());
		this.add(collectedExpectedProduction);
		
		startPoint.setNext(collectedExpectedProduction, null);
		
		Activity collectedActualProduction
			= ProvideDERsIncentive.createActivity(
					new CollectedActualProduction());
		this.add(collectedActualProduction);
	
		collectedExpectedProduction.setNext(
				collectedActualProduction, null);
		
		EndPoint endPoint = ProvideDERsIncentive.createEndPoint();
		this.add(endPoint);
		
		collectedActualProduction.setNext(endPoint, null);
	}

	@Override
	public void initialize(Agent owner) {
		super.initialize(owner);
		
		calculateDERsIncomings();
	}
	
	public void calculateDERsIncomings() {
		@SuppressWarnings("unchecked")
		HashMap<Date, List<Double>> incomings
			= (HashMap<Date, List<Double>>) owner.getAttribute(
					VirtualPowerPlant.KEY_PRODUCTION_INCOMINGS);
		
		DERProductionRecord derProdRecord
		= (DERProductionRecord) owner.getAttribute(
				VirtualPowerPlant.KEY_DER_PRODUCTION_RECORD);
		
		double alpha = (Double) owner.getAttribute(VirtualPowerPlant.KEY_ALPHA);
		double beta = (Double) owner.getAttribute(VirtualPowerPlant.KEY_BETA);
		
		long time = SimulationEngine.getSimulationService().getTime()
				- TimeUtil.A_DAY;
		
		Date yesterday = TimeUtil.getMidnightDate(time);
		
		List<Double> VGC = incomings.get(yesterday);
	
		List<Double> prodCs = calculateProdCs(derProdRecord, yesterday);
		
		List<Double> Zs = calculateZs(
				derProdRecord, prodCs, alpha, beta, yesterday);
		
		for (int i = 0; i < 48; i++) {
			for (Integer derId: derProdRecord.getDERIds()) {
				Double actProd
					= derProdRecord.getActualProduction(derId, yesterday, i);
				
				Double expProd
					= derProdRecord.getExpectedProduction(derId, yesterday, i);
				
				if (actProd == null) actProd = 0.0;
				if (expProd == null) expProd = 0.0;
				
				double acc = Zs.get(i) / (1 + alpha *
						Math.pow(Math.abs(actProd - expProd), beta));
				
				double VCi = acc * actProd / prodCs.get(i)
						* VGC.get(i);
				
				derProdRecord.setPayment(derId, yesterday, i, VCi);
			}
		}
	}
	
	private List<Double> calculateProdCs(
			DERProductionRecord derProdRecord, Date date) {
		List<Double> prodCs = new ArrayList<Double>();
		
		for (int i = 0; i < 48; i++) {
			double prodC = 0.0;
			for (Integer derId: derProdRecord.getDERIds()) {
				Double actProd
					= derProdRecord.getActualProduction(derId, date, i);
				
				if (actProd == null) actProd = 0.0;
				
				prodC += actProd;
			}
			
			prodCs.add(prodC);
		}
		
		return prodCs;
	}
	
	private List<Double> calculateZs(DERProductionRecord derProdRecord,
			List<Double> prodCs, double alpha, double beta, Date date) {
		List<Double> Zs = new ArrayList<Double>();
		
		for (int i = 0; i < 48; i++) {
			double sum = 0.0;
			
			for (Integer derId: derProdRecord.getDERIds()) {
				Double actProd
					= derProdRecord.getActualProduction(derId, date, i);
				
				Double expProd
					= derProdRecord.getExpectedProduction(derId, date, i);
				
				if (actProd == null) actProd = 0.0;
				if (expProd == null) expProd = 0.0;
				
				sum += (actProd / (1 + alpha *
						Math.pow(Math.abs(expProd - actProd), beta)));
			}
			
			double z = prodCs.get(i) / sum;
			
			Zs.add(z);
		}
		
		return Zs;
	}
	
}
