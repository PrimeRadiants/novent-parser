package com.primeradiants.novent.javascript;

import java.util.List;

import com.google.gson.Gson;
import com.primeradiants.novent.model.Animate;
import com.primeradiants.novent.model.End;
import com.primeradiants.novent.model.Event;
import com.primeradiants.novent.model.EventElement;
import com.primeradiants.novent.model.Novent;
import com.primeradiants.novent.model.Page;
import com.primeradiants.novent.model.ParsingUtil.LoopTypeEnum;
import com.primeradiants.novent.model.ParsingUtil.PropertyEnum;
import com.primeradiants.novent.model.ParsingUtil.TargetEnum;
import com.primeradiants.novent.model.Play;
import com.primeradiants.novent.model.Stop;
import com.primeradiants.novent.model.Wait;
import com.primeradiants.novent.model.Wiggle;

public class NoventToJavascript {

	public static String convert(Novent novent) {
		Gson gson = new Gson();
		String script = "var novent = new NoventEngine.Novent(\"canvas_id\", " + gson.toJson(novent.getButton()) + ");";
		
		for(Page page : novent.getPages()) {
			script += "var page = novent.Pages.New(" + gson.toJson(page) + ");";
			
			for(Event event : page.getEvents().getEvents()) {
				script += "page.Events.New(function(canvas, readyObj, callback) {" + eventToJavascript(event.getElements()) + "});";
			}
		}
		
		return script;
	}
	
	private static String eventToJavascript(List<EventElement> event) {
		String result = "";
		for(EventElement element : event) {
			if(element instanceof Animate)
				result += animateToJavascript((Animate) element);
			else if(element instanceof End)
				result += endToJavascript((End) element);
			else if(element instanceof Play)
				result += playToJavascript((Play) element);
			else if(element instanceof Stop)
				result += stopToJavascript((Stop) element);
			else if(element instanceof Wait)
				result += waitToJavascript((Wait) element);
			else if(element instanceof Wiggle)
				result += wiggleToJavascript((Wiggle) element);
		}
		return result;
	}

	private static String wiggleToJavascript(Wiggle element) {
		return "var wiggleObj = new readyObj.wiggle('" + element.getName() + "', '" + element.getTargetType() + "s', '" + element.getTarget() + "', '" + element.getProperty() + "', " + element.getAmplitude() + ", " + element.getFrequency() + ", '" + element.getEase().toString() + "');wiggleObj.start();";
	}

	private static String waitToJavascript(Wait element) {
		String result = "canvas.Timeline.new(readyObj.button).wait(" + element.getDuration() + ").call(";
		
		if(!element.getChildElements().isEmpty())
			result += "function() {" + eventToJavascript(element.getChildElements()) + "}";
		
		result += ");";
		return result;
	}

	private static String stopToJavascript(Stop element) {
		return "readyObj.materials.wiggles." + element.getTarget() + ".stop();";
	}

	private static String playToJavascript(Play element) {
		String result = "";
		if(element.getTargetType() == TargetEnum.animation || element.getTargetType() == TargetEnum.video) {
			result = "readyObj.materials." + element.getTargetType().toString() + "s." + element.getTarget() + ".play('" + element.getLoop().toString() + "'";
			if(!element.getChildElements().isEmpty())
				result += "," + "function() {" + eventToJavascript(element.getChildElements()) + "}";
			result += ");";
		}
		else if(element.getTargetType() == TargetEnum.sound) {
			result = "canvas.Sound.";
			if(element.getLoop() == LoopTypeEnum.loop)
				result += "playLoop('" + element.getTarget() + "');";
			else
				result += "get('" + element.getTarget() + "').play();";
		}
		
		return result;
	}

	private static String endToJavascript(End element) {
		return "callback();";
	}

	private static String animateToJavascript(Animate element) {
		String result = "";
		if(element.getTargetType() != TargetEnum.sound) {
			result = "canvas.Timeline.new(readyObj.materials." + element.getTargetType().toString() + "s." + element.getTarget() + ").to({" + element.getProperty().toString() + ":" + element.getValue() + "}, " + element.getDuration() + ", Ease." + element.getEase().toString() + ").call(";
			
			if(!element.getChildElements().isEmpty())
				result += "function() {" + eventToJavascript(element.getChildElements()) + "}";
		}
		else if(element.getProperty() == PropertyEnum.volume) {
			result = "canvas.Sound.fadeTo('" + element.getTarget() + "', " + element.getDuration() + ", " + element.getValue() + "";
			
			if(!element.getChildElements().isEmpty())
				result += ", function() {" + eventToJavascript(element.getChildElements()) + "}";
		}
		
		result += ");";
		return result;
	}
	
}
