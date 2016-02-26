package com.primeradiants.novent.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;
import com.primeradiants.novent.model.ParsingUtil.EaseEnum;
import com.primeradiants.novent.model.ParsingUtil.PropertyEnum;
import com.primeradiants.novent.model.ParsingUtil.TargetEnum;

public class Wiggle extends EventElement {
	private String name;
	private ParsingUtil.TargetEnum targetType;
	private String target;
	private ParsingUtil.PropertyEnum property;
	private double amplitude;
	private int frequency;
	private ParsingUtil.EaseEnum ease;
	
	public Wiggle(List<EventElement> childElements, String name, TargetEnum targetType, String target,
			PropertyEnum property, double amplitude, int frequency, EaseEnum ease) {
		super(childElements);
		this.name = name;
		this.targetType = targetType;
		this.target = target;
		this.property = property;
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.ease = ease;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public ParsingUtil.EaseEnum getEase() {
		return ease;
	}
	public void setEase(ParsingUtil.EaseEnum ease) {
		this.ease = ease;
	}
	
	public static Wiggle fromNode(Element node) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		Pair<ParsingUtil.TargetEnum, String> target = ParsingUtil.validateTarget(node, "target");
		ParsingUtil.PropertyEnum property = ParsingUtil.validateEnumValue(node, "property", ParsingUtil.PropertyEnum.class);
		double amplitude;
		
		if(property.valueType() == ParsingUtil.PropertyValueEnum.integer)
			amplitude = ParsingUtil.validateIntegerAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.positiveInteger)
			amplitude = ParsingUtil.validatePositiveIntegerAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.betweenZeroAndOne)
			amplitude = ParsingUtil.validateBetweenZeroAndOneAttr(node, "value");
		else if(property.valueType() == ParsingUtil.PropertyValueEnum.real)
			amplitude = ParsingUtil.validateRealAttr(node, "value");
		else
			amplitude = ParsingUtil.validatePositiveRealAttr(node, "value");
		
		int frequency = ParsingUtil.validateIntegerAttr(node, "frequency");
		ParsingUtil.EaseEnum ease = ParsingUtil.validateEnumValue(node, "property", ParsingUtil.EaseEnum.class);
		
		NodeList children = node.getChildNodes();
		if(children.getLength() > 0)
			throw new NoventParsingException("Invalid wiggle tag: at line " + node.getUserData("lineNumber") + ", end tag must be an empty tag.");
		
		return new Wiggle(null, name, target.getLeft(), target.getRight(), property, amplitude, frequency, ease);
	}
}
