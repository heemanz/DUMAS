package VPP;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Internet.app.goal.ProcessedInternetMessage;
import VPP.goal.CollectedActualProduction;
import VPP.goal.CollectedExpectedProduction;
import VPP.goal.EncouragedDERsParticipation;
import VPP.goal.InformedCollectedActualProduction;
import VPP.goal.InformedCollectedExpectedProduction;
import VPP.goal.RegisteredDER;
import VPP.goal.SubscribedToSmartGrid;
import nise.ajou.ac.kr.roch.Organization;

public class VirtualPowerPlant extends Organization {

	public static final String KEY_SG_ID = "SGID";
	public static final String KEY_DERS = "DERs";
	public static final String KEY_ALPHA = "alpha";
	public static final String KEY_BETA = "beta";
	public static final String KEY_SUBSCRIBED_TO_SG = "SubscribedToSG";
	public static final String KEY_DER_PRODUCTION_RECORD = "DERProductionRecord";
	public static final String KEY_PRODUCTION_INCOMINGS = "ProductionIncomings";
	
	public VirtualPowerPlant(long deliberationCycle, int sgId, double alpha, double beta) {
		super(deliberationCycle);
		
		add(new ProcessedInternetMessage());
		add(new SubscribedToSmartGrid());
		add(new EncouragedDERsParticipation());
		add(new CollectedExpectedProduction());
		add(new CollectedActualProduction());
		add(new RegisteredDER());
		add(new InformedCollectedExpectedProduction());
		add(new InformedCollectedActualProduction());
		
		Set<Integer> ders = new TreeSet<Integer>();
		DERProductionRecord derProdRecord = new DERProductionRecord();
		HashMap<Date, List<Double>> incomings
			= new HashMap<Date, List<Double>>();
		
		setAttribute(KEY_SG_ID, Integer.valueOf(sgId));
		setAttribute(KEY_SUBSCRIBED_TO_SG, Boolean.FALSE);
		setAttribute(KEY_DERS, ders);
		setAttribute(KEY_ALPHA, Double.valueOf(alpha));
		setAttribute(KEY_BETA, Double.valueOf(beta));
		setAttribute(KEY_DER_PRODUCTION_RECORD, derProdRecord);
		setAttribute(KEY_PRODUCTION_INCOMINGS, incomings);
	}

}
