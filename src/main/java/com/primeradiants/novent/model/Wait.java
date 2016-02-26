package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Wait extends EventElement {
	private int duration;

	public Wait(List<EventElement> childElements, int duration) {
		super(childElements);
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public static Wait fromNode(Element node) throws NoventParsingException {
		int duration = ParsingUtil.validatePositiveIntegerAttr(node, "duration");
		List<EventElement> childElements = new ArrayList<EventElement>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i) instanceof Element)
				childElements.add(EventElement.fromNode((Element) children.item(i)));
		}
		return new Wait(childElements, duration);
	}
}
