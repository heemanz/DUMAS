package Simulation;

import java.util.Date;
import java.util.Scanner;

import util.TimeUtil;
import DER.WindTurbine;
import Internet.Internet;
import SmartGrid.SmartGrid;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CVPPSimulator {

	public static final long DELIBERATION_INTERVAL = 10000;
	public static final double PROBABILITY_TO_FAIL = 0.001;
	
	private SimulationEngine simEngine;
	
	public CVPPSimulator() {
		simEngine = SimulationEngine.getInstance();
		simEngine.pause();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CVPPSimulator simulator = new CVPPSimulator();
		
		simulator.simEngine.setTime(TimeUtil.getMidnightTime(new Date().getTime()));
		
		simulator.setScenario(1);
		
		simulator.simEngine.start();
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);

		while (true) {
			String cmd = keyboard.nextLine();
			System.out.println(cmd);
			
			if (cmd.length() > 0) {
				simulator.simEngine.stop();
				break;
			}
		}
	}

	public void setScenario(int numOfDERs) {
		System.out.println("Number of DERs: " + numOfDERs);
		
		Internet internet = new Internet(DELIBERATION_INTERVAL);
		simEngine.add(internet);
		
		SmartGrid grid = new SmartGrid(DELIBERATION_INTERVAL, 0.05, 0.5, 0.5);
		
		int gridId = simEngine.add(grid);
		
		int vppId = simEngine.add(new VirtualPowerPlant(DELIBERATION_INTERVAL, gridId, 0.5, 0.5));
		
		internet.add(gridId, 0.0);
		internet.add(vppId, 0.0);

		for (int i = 0; i < numOfDERs; i++) {
			int derId = simEngine.add(new WindTurbine(DELIBERATION_INTERVAL, gridId, 0.1, 0.05));
			internet.add(derId, PROBABILITY_TO_FAIL);
		}
	}
}
