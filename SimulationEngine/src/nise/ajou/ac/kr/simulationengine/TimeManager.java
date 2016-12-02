package nise.ajou.ac.kr.simulationengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TimeManager {
	// Simulation World's time
	private long time;
	
	private List<ScheduledJob> schedule = new ArrayList<ScheduledJob>();
	
	private HashMap<TickHandler, Long> periodicJobMap = new HashMap<TickHandler, Long>();
	
	private class ScheduledJob {
		private long time;
		private TickHandler handler;
		
		public ScheduledJob(long time, TickHandler handler) {
			this.time = time;
			this.handler = handler;
		}
		
		long getTime() { 
			return time;
		}
		
		TickHandler getHandler() {
			return handler;
		}
	}
	
	public TimeManager() {
		this.time = 0;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public Date getDate() {
		return new Date(time);
	}
	public void advance() {
		Collections.sort(schedule, new Comparator<ScheduledJob>() {
			public int compare(ScheduledJob obj1, ScheduledJob obj2) {
				return (obj1.getTime() < obj2.getTime()) ? -1 : (obj1.getTime() > obj2.getTime()) ? 1 : 0;
			}
		});
		
		if (schedule.size() > 0) {
			ScheduledJob job = schedule.remove(0);
			
			long delta = job.getTime() - getTime();
			this.setTime(job.getTime());
			
			TickHandler handler = job.getHandler();
			
			if (handler != null) {
				handler.tick(delta);
			}
			
			if (periodicJobMap.containsKey(handler)) {
				notifyOnce(periodicJobMap.get(handler).longValue(), handler);
			}
		}
	}
	
	public void notifyOnce(long after, TickHandler handler) {
		this.addScheduledJob(getTime() + after, handler);
	}
	
	public void cancelToNotify(TickHandler handler) {
		int index = -1;
		
		for (int i = 0; i < schedule.size(); i++) {
			ScheduledJob scheduledJob = schedule.get(i);
			if (handler == scheduledJob.getHandler()) {
				index = i;
				break;
			}
		}
		
		if (index > -1) {
			schedule.remove(index);
		}
		
		if (periodicJobMap.containsKey(handler)) {
			periodicJobMap.remove(handler);
		}
	}
	
	public void notifyPeriodically(long period, TickHandler handler) {
		periodicJobMap.put(handler, period);
		notifyOnce(period, handler);
	}
	
	private void addScheduledJob(long time, TickHandler handler) {
		ScheduledJob scheduledJob = new ScheduledJob(time, handler);
		schedule.add(scheduledJob);
	}
}
