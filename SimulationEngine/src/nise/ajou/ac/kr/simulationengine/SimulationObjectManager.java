package nise.ajou.ac.kr.simulationengine;

import java.util.HashMap;

public interface SimulationObjectManager {
	public abstract int add(SimulationObject obj);
	public abstract HashMap<Integer, SimulationObject> getSimulationObjects();
	public abstract SimulationObject find(int id);
	public abstract void remove(int id);
}
