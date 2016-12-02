package nise.ajou.ac.kr.simulationengine;

public interface EventHandler {
	public boolean canProcess(Event evt);
	public void handle(Event evt);
}
