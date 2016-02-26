package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Events {
	private List<Event> events;
	public Events(List<Event> events) {
		super();
		this.events = events;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public static Events fromNode(Element node) throws NoventParsingException {
		NodeList eventsList = node.getElementsByTagName("event");
		if(eventsList.getLength() == 0)
			throw new NoventParsingException("Invalid events tag: no child event tag at line " + node.getUserData("lineNumber"));
		
		List<Event> events = new ArrayList<Event>();
		for(int i = 0; i < eventsList.getLength(); i++) {
			events.add(Event.fromNode((Element) eventsList.item(i)));
		}
		
		return new Events(events);
	}
}
