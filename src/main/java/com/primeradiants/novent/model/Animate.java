package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;
import com.primeradiants.novent.model.ParsingUtil.EaseEnum;
import com.primeradiants.novent.model.ParsingUtil.PropertyEnum;
import com.primeradiants.novent.model.ParsingUtil.TargetEnum;

public class Animate extends EventElement {
	private ParsingUtil.TargetEnum targetType;
	private String target;
	private ParsingUtil.PropertyEnum property;
	private double value;
	private int duration;
	private ParsingUtil.EaseEnum ease;
	
	public Animate(List<EventElement> childElements, TargetEnum targetType, String target, PropertyEnum property,
			double value, int duration, EaseEnum ease) {
		super(childElements);
		this.targetType = targetType;
		this.target = target;
		this.property = property;
		this.value = value;
		this.duration = duration;
		this.ease = ease;
	}
	public ParsingUtil.TargetEnum getTargetType() {
		return targetType;
	}
	public void setTargetType(ParsingUtil.TargetEnum targetType) {
		this.targetType = targetType;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public ParsingUtil.PropertyEnum getProperty() {
		return property;
	}
	public void setProperty(ParsingUtil.PropertyEnum property) {
		this.property = property;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public ParsingUtil.EaseEnum getEase() {
		return ease;
	}
	public void setEase(ParsingUtil.EaseEnum ease) {
		this.ease = ease;
	}
	public static Animate fromNode(Element node) throws NoventParsingException {

		Pair<ParsingUtil.TargetEnum, String> target = ParsingUtil.validateTarget(node, "target");
		ParsingUtil.PropertyEnum property = ParsingUtil.validateEnumValue(node, "property", ParsingUtil.PropertyEnum.class);
		double value;
		
		if(property.valueType() == ParsingUtil.PropertyValueEnum.integer)
			value = ParsingUtil.validateIntegerAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.positiveInteger)
			value = ParsingUtil.validatePositiveIntegerAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.betweenZeroAndOne)
			value = ParsingUtil.validateBetweenZeroAndOneAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.real)
			value = ParsingUtil.validateRealAttr(node, "value");
		else
			value = ParsingUtil.validatePositiveRealAttr(node, "value");
		
		int duration = ParsingUtil.validatePositiveIntegerAttr(node, "duration");
		ParsingUtil.EaseEnum ease = ParsingUtil.validateEnumValue(node, "property", ParsingUtil.EaseEnum.class);
		
		List<EventElement> childElements = new ArrayList<EventElement>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			childElements.add(EventElement.fromNode((Element) children.item(i)));
		}
		
		return new Animate(childElements, target.getLeft(), target.getRight(), property, value, duration, ease);
	}
}
