package Internet.app.task;

import java.util.Date;

import Event.Restore;
import util.InternetUtil;
import nise.ajou.ac.kr.roch.Task;
import nise.ajou.ac.kr.simulationengine.SimulationEngine;
import nise.ajou.ac.kr.simulationengine.TickHandler;

public class HandleConnectionError extends Task {

	private long timeToRestore;
	
	private TickHandler restoreHandler = new TickHandler() {

		@Override
		public void tick(long delta) {
			SimulationEngine.getSimulationService().publish(
					new Restore(owner.getId()));
			
			System.out.println("Internet Node[" + owner.getId() + "] Restored at "
					+ new Date(SimulationEngine.getSimulationService().getTime()));
			owner.setAttribute(InternetUtil.KEY_INTERNET_ERROR, null);
		}
		
	};
	
	public HandleConnectionError(long timeToRestore) {
		super();
		
		this.timeToRestore = timeToRestore;
	}

	@Override
	protected void registerHandlers() {
		SimulationEngine.getSimulationService().notifyOnce(
				timeToRestore, restoreHandler);
	}

	@Override
	protected void deregisterHandlers() {
	}

}
