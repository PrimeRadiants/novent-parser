package com.primeradiants.novent.model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Page {

	private String name;
	private Materials materials;
	private Events events;
	public Page(String name, Materials materials, Events events) {
		super();
		this.name = name;
		this.materials = materials;
		this.events = events;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Materials getMaterials() {
		return materials;
	}
	public void setMaterials(Materials materials) {
		this.materials = materials;
	}
	public Events getEvents() {
		return events;
	}
	public void setEvents(Events events) {
		this.events = events;
	}
	
	public static Page fromNode(Element node) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		NodeList materialsList = node.getElementsByTagName("materials");
		
		if(materialsList.getLength() == 0)
			throw new NoventParsingException("Invalid novent-descriptor: missing materials tag for page " + name + " at line " + node.getUserData("lineNumber"));
		
		if(materialsList.getLength() > 1)
			throw new NoventParsingException("Invalid novent-descriptor: too many materials tags for page " + name + " at line " + node.getUserData("lineNumber"));
		
		Materials materials = Materials.fromNode((Element) materialsList.item(0));
		
		NodeList eventsList = node.getElementsByTagName("materials");
		
		if(eventsList.getLength() == 0)
			throw new NoventParsingException("Invalid novent-descriptor: missing events tag for page " + name + " at line " + node.getUserData("lineNumber"));
		
		if(eventsList.getLength() > 1)
			throw new NoventParsingException("Invalid novent-descriptor: too many events tags for page " + name + " at line " + node.getUserData("lineNumber"));
		
		Events events = Events.fromNode((Element) eventsList.item(0));
		
		return new Page(name, materials, events);
	}
}
