package nise.ajou.ac.kr.simulationengine;

public interface SimulationControl {
	public abstract void setTime(long time);
	public abstract void start();
	public abstract void pause();
	public abstract void resume();
	public abstract void stop();
}
