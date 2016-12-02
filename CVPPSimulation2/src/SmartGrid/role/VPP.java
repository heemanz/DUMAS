package SmartGrid.role;

import nise.ajou.ac.kr.roch.Agent;
import nise.ajou.ac.kr.roch.Role;

public class VPP extends Role {

	public VPP() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateFitnessOf(Agent member) {
		return 1.0;
	}

}
