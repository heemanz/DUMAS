package nise.ajou.ac.kr.roch;

public class DefaultFitnessFunction implements FitnessFunction {

	private static FitnessFunction instance;
	
	public static FitnessFunction getInstance() {
		if (instance == null) {
			instance = new DefaultFitnessFunction();
		}
		
		return instance;
	}
	
	public DefaultFitnessFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(Agent agent) {
		return 1.0;
	}

}
