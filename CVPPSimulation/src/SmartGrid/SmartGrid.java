package SmartGrid;

import java.util.Set;
import java.util.TreeSet;

import SmartGrid.goal.CalculatedProductionCost;
import SmartGrid.goal.ManagedDERMemberships;
import SmartGrid.goal.ManagedVPPMemberships;
import SmartGrid.goal.PaidProductionCost;
import SmartGrid.goal.ReceivedActualProduction;
import SmartGrid.goal.ReceivedExpectedProduction;
import SmartGrid.goal.UsedElectricity;
import nise.ajou.ac.kr.roch.Organization;

public class SmartGrid extends Organization {

	public static final String KEY_DERS = "DERs";
	public static final String KEY_VPPS = "VPPs";
	public static final String KEY_PRICE = "price";
	public static final String KEY_ALPHA = "alpha";
	public static final String KEY_BETA = "beta";
	public static final String KEY_VPP_PRODUCTION_RECORDS = "VPPPRoductionRecords";
	
	public SmartGrid(long deliberationCycle, double price, double alpha, double beta) {
		super(deliberationCycle);
		
		add(new UsedElectricity());
		add(new ManagedDERMemberships());
		add(new ManagedVPPMemberships());
		//add(new ProvidedProductionCost());
		
		add(new ReceivedExpectedProduction());
		add(new ReceivedActualProduction());
		add(new CalculatedProductionCost());
		add(new PaidProductionCost());
		
		Set<Integer> ders = new TreeSet<Integer>();
		Set<Integer> vpps = new TreeSet<Integer>();
		
		setAttribute(KEY_DERS, ders);
		setAttribute(KEY_VPPS, vpps);
		setAttribute(KEY_PRICE, price);
		setAttribute(KEY_ALPHA, Double.valueOf(alpha));
		setAttribute(KEY_BETA, Double.valueOf(beta));
		setAttribute(KEY_VPP_PRODUCTION_RECORDS, new VPPProductionRecord());
	}

}
