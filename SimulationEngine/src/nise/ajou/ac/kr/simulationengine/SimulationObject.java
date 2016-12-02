package nise.ajou.ac.kr.simulationengine;

import java.util.HashMap;

public abstract class SimulationObject {
	private Integer id;
	private State state = State.created;
	
	protected HashMap<String, TickHandler> tickHandlers;
	protected HashMap<String, EventHandler> eventHandlers;
	
	public enum State {
		created,
		initialized,
		active,
		deactive,
		disposed,
	}
	
	public Integer getId()
	{
		return id;
	}
	
	void setId(Integer id)
	{
		this.id = id; 
	}
	
	protected void setState(State state) {
		this.state = state;
	}
	
	public void activate() {
		setState(State.active);
	}
	
	public void deactivate() {
		setState(State.deactive);
	}
	
	public State getState() {
		return state;
	}
	
	public abstract void initialize();
	public abstract void finalize();
	
	public void dispose() {
		setState(State.disposed);
	}

	protected void add(TickHandler handler) {
		tickHandlers.put(handler.getClass().getName(), handler);
	}
	
	protected void remove(TickHandler handler) {
		tickHandlers.remove(handler.getClass().getName());
	}
	
	protected void add(EventHandler handler) {
		eventHandlers.put(handler.getClass().getName(), handler);
	}
	
	protected void remove(EventHandler handler) {
		eventHandlers.remove(handler.getClass().getName());
	}
	
	public static String InitialConfigurationFormat() {
		return "[{\"name\": \"generation-power\", \"type\": \"int\", \"units\": \"KW/h\"}, " +
			    " {\"name\": \"size\", \"type\": [{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}, " +
				                       "{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}, " +
			                           "{\"name\": \"x\", \"type\": \"double\", \"units\": \"m\"}]}]";
	}
}
