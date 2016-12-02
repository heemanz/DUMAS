package Simulation;

import java.util.Date;
import java.util.Scanner;

import util.TimeUtil;
import DER.WindTurbine;
import SmartGrid.SmartGrid;
import VPP.VirtualPowerPlant;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;

public class CVPPSimulator {

	private SimulationEngine simEngine;
	
	public CVPPSimulator() {
		simEngine = SimulationEngine.getInstance();
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
		
		int sgId = simEngine.add(new SmartGrid(10000, 0.05, 0.5, 0.5));
		
		simEngine.add(new VirtualPowerPlant(10000, sgId, 0.5, 0.5));
		
		for (int i = 0; i < numOfDERs; i++) {
			simEngine.add(new WindTurbine(10000, sgId, 0.1, 0.05));
		}
	}
}
