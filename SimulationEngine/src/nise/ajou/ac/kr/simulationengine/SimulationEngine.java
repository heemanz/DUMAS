package nise.ajou.ac.kr.simulationengine;

import java.util.HashMap;


public class SimulationEngine implements SimulationControl, SimulationService, SimulationObjectManager, Runnable {

	private static SimulationEngine engine = null;
	
	private TimeManager timeManager = new TimeManager();
	private EventManager eventManager = new EventManager();
	private SimulationState currentState = null;
	private SimulationState running = null;
	private SimulationState stopped = null;
	private boolean threadRunning = false;
	
	private HashMap<Integer, SimulationObject> simulationObjects = new HashMap<Integer, SimulationObject>();
	
	public static SimulationEngine getInstance() {
		if (engine == null) {
			engine = new SimulationEngine();
		}
		
		return engine;
	}
	
	protected SimulationEngine() {
		running = new SimulationState() {
			@Override
			public void update() {
				timeManager.advance();
			}
		};
		
		stopped = new SimulationState() {
			@Override
			public void update() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static SimulationService getSimulationService() {
		return getInstance();
	}
	
	public static SimulationControl getSimulationControl() {
		return getInstance();
	}
	
	@Override
	public void subscribe(Class<? extends Event> eventClass, EventHandler handler) {
		eventManager.subscribe(eventClass, handler);
	}

	@Override
	public void unsubscribe(Class<? extends Event> eventClass, EventHandler handler) {
		eventManager.unsubscribe(eventClass, handler);
	}

	@Override
	public void publish(Event evt) {
		eventManager.publish(evt);
	}

	@Override
	public void notifyOnce(long after, TickHandler handler) {
		timeManager.notifyOnce(after, handler);
	}
	
	@Override
	public void cancelToNotify(TickHandler handler) {
		timeManager.cancelToNotify(handler);
	}
	
	@Override
	public void notifyPeriodically(long period, TickHandler handler) {
		timeManager.notifyPeriodically(period, handler);
	}
	
	@Override
	public long getTime() {
		return timeManager.getTime();
	}

	@Override
	public void setTime(long time) {
		timeManager.setTime(time);
	}
	
	@Override
	public void start() {
		synchronized(this) {
			currentState = running;
			threadRunning = true;
		}
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void pause() {
		synchronized(this) {
			currentState = stopped;
		}
	}

	@Override
	public void resume() {
		synchronized(this) {
			currentState = running;	
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		synchronized(this) {
			threadRunning = false;
			engine = null;
		}
	}

	@Override
	public int add(SimulationObject obj) {
		Integer id = Integer.valueOf(simulationObjects.size());
		
		obj.setId(id);
		obj.initialize();
		obj.activate();
		
		simulationObjects.put(id, obj);
		
		return id.intValue();
	}
	
	@Override
	public HashMap<Integer, SimulationObject> getSimulationObjects() {
		return simulationObjects;
	}
	
	@Override
	public SimulationObject find(int id) {
		return simulationObjects.get(Integer.valueOf(id));
	}
	
	@Override
	public void remove(int id) {
		SimulationObject obj = simulationObjects.remove(Integer.valueOf(id));
		
		obj.deactivate();
		obj.finalize();
		obj.dispose();
	}
	
	@Override
	public void run() {
		while (isThreadRunning()) {
			currentState.update();
		}
	}
	
	private boolean isThreadRunning() {
		synchronized (this) {
			return threadRunning;
		}
	}
}
