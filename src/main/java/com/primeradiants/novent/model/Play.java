package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;
import com.primeradiants.novent.model.ParsingUtil.LoopTypeEnum;
import com.primeradiants.novent.model.ParsingUtil.TargetEnum;

public class Play extends EventElement {
	private ParsingUtil.TargetEnum targetType;
	private String target;
	private ParsingUtil.LoopTypeEnum loop;
	public Play(List<EventElement> childElements, TargetEnum targetType, String target, LoopTypeEnum loop) {
		super(childElements);
		this.targetType = targetType;
		this.target = target;
		this.loop = loop;
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
	public ParsingUtil.LoopTypeEnum getLoop() {
		return loop;
	}
	public void setLoop(ParsingUtil.LoopTypeEnum loop) {
		this.loop = loop;
	}
	public static Play fromNode(Element node) throws NoventParsingException {

		Pair<ParsingUtil.TargetEnum, String> target = ParsingUtil.validateTarget(node, "target");
		ParsingUtil.LoopTypeEnum loop = ParsingUtil.validateEnumValue(node, "loop", ParsingUtil.LoopTypeEnum.class);
		
		List<EventElement> childElements = new ArrayList<EventElement>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i) instanceof Element)
				childElements.add(EventElement.fromNode((Element) children.item(i)));
		}
		
		return new Play(childElements, target.getLeft(), target.getRight(), loop);
	}
}
