package nise.ajou.ac.kr.simulationengine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventManager {
	private ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<EventHandler>> eventHandlersMap = new ConcurrentHashMap<Class<? extends Event>, ConcurrentLinkedQueue<EventHandler>>();

	public void subscribe(Class<? extends Event> eventClass, EventHandler handler) {
		ConcurrentLinkedQueue<EventHandler> handlerList = eventHandlersMap.get(eventClass);

		if (handlerList == null) {
			handlerList = new ConcurrentLinkedQueue<EventHandler>();
			handlerList.add(handler);
			eventHandlersMap.put(eventClass, handlerList);
		}
		else if (!handlerList.contains(handler)) {
			handlerList.add(handler);
		}
		else
		{
			System.out.println("this event handler already subscribes.");
		}
	}
	
	public void unsubscribe(Class<? extends Event> eventClass, EventHandler handler) {
		ConcurrentLinkedQueue<EventHandler> handlerList = eventHandlersMap.get(eventClass);
		
		if (handlerList != null) {
			if (handlerList.contains(handler)) {
				handlerList.remove(handler);
			}
			else {
				System.out.println("this event handler does not subscribe.");
			}
		}
		else {
			System.out.println("there is no event handler for event type(" + eventClass.getCanonicalName() + ")");
		}
	}
	
	public void publish(Event event) {
		ConcurrentLinkedQueue<EventHandler> handlerList = eventHandlersMap.get(event.getClass());
		
		if (handlerList != null) {
			for (EventHandler handler : handlerList) {
				if (handler.canProcess(event)) {
					handler.handle(event);
				}
			}
		}
	}
}
