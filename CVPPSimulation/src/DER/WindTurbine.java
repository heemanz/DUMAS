package DER;

import DER.goal.GeneratedElectricity;
import DER.goal.InformedActualProduction;
import DER.goal.InformedExpectedProduction;
import DER.goal.ReceivedProductionCost;
import DER.goal.SubscribedToSmartGrid;
import DER.goal.SubscribedToVPP;
import nise.ajou.ac.kr.roch.Agent;

public class WindTurbine extends Agent {

	public static final String KEY_SG_ID = "SGID";
	public static final String KEY_VPP_ID = "VPPID";
	public static final String KEY_SUBSCRIBED_TO_SG = "SubscribedToSG";
	public static final String KEY_SUBSCRIBED_TO_VPP = "SubscribedToVPP";
	public static final String KEY_PRODUCTION_RECORD = "ProductionRecord";
	public static final String KEY_SIGMA_SYST = "SigmaSystem";
	public static final String KEY_SIGMA_RES = "SigmaResidual";
	
	public WindTurbine(long deliberationCycle, int sgId,
			double sigmaSyst, double sigmaRes) {
		super(deliberationCycle);
		
		ProductionRecord prodRecord = new ProductionRecord();
		
		this.add(new SubscribedToSmartGrid());
		this.add(new GeneratedElectricity());
		this.add(new SubscribedToVPP());
		this.add(new InformedExpectedProduction());
		this.add(new InformedActualProduction());
		this.add(new ReceivedProductionCost());
		
		setAttribute(KEY_SG_ID, Integer.valueOf(sgId));
		setAttribute(KEY_SUBSCRIBED_TO_SG, Boolean.FALSE);
		setAttribute(KEY_SUBSCRIBED_TO_VPP, Boolean.FALSE);
		setAttribute(KEY_PRODUCTION_RECORD, prodRecord);
		setAttribute(KEY_SIGMA_SYST, Double.valueOf(sigmaSyst));
		setAttribute(KEY_SIGMA_RES, Double.valueOf(sigmaRes));
	}

}
