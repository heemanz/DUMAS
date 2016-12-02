package nise.ajou.ac.kr.simulationengine;

public interface SimulationService {
	public void subscribe(Class<? extends Event> eventClass, EventHandler handler);
	public void unsubscribe(Class<? extends Event> eventClass, EventHandler handler);
	public void publish(Event evt);
	public void notifyOnce(long after, TickHandler handler);
	public void cancelToNotify(TickHandler handler);
	public void notifyPeriodically(long period, TickHandler handler);
	public long getTime();
}
