package com.primeradiants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.primeradiants.novent.exceptions.NoventParsingException;
import com.primeradiants.novent.javascript.NoventToJavascript;
import com.primeradiants.novent.model.Novent;
import com.primeradiants.xml.PositionalXMLReader;

public class Main 
{
	
	private static Logger logger = new Logger();
	
    public static void main(String[] args) throws UnsupportedEncodingException
    {
        logger.info("Generating novent.js...");
        
        String path = getProgramPath();
        File folder = new File(path);
        File descriptor = folder.toPath().resolve("novent-descriptor.xml").toFile();
        if(!descriptor.exists() || descriptor.isDirectory()) {
        	logger.error("Missing file novent-descriptor.xml");
        	logger.error("BUILD FAILURE");
        	return;
        }
        
        Document xmlDescriptor = parseXmlFile(descriptor);
        
        if(xmlDescriptor == null) {
        	logger.error("Unreadable xml file!");
        	logger.error("BUILD FAILURE");
        	return;
        }
        
        try {
        	logger.info("Parsing Novent models...");
			Novent novent = Novent.fromNode((Element) xmlDescriptor.getElementsByTagName("novent").item(0));
			logger.info("Converting models to Javascript...");
			String script = NoventToJavascript.convert(novent);
			
			File result = folder.toPath().resolve("novent.js").toFile();
			if(descriptor.exists() && descriptor.isFile()) {
				result.delete();
			}
			
			result.createNewFile();
			FileUtils.writeStringToFile(result, script);
			
		} catch (NoventParsingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("BUILD FAILURE");
			return;
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("BUILD FAILURE");
		}
        
        logger.info("BUILD SUCCESS");
    } 
    
    public static String getProgramPath() throws UnsupportedEncodingException {
        URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }
    
    public static Document parseXmlFile(File file) {
    	try {
			InputStream is = new FileInputStream(file);
			Document doc = PositionalXMLReader.readXML(is);
			is.close();
			
			return doc;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		} catch (SAXException e) {
			logger.error(e.getMessage());
			return null;
		}
    }
    
    public static boolean isExistingFile(String path) {
    	File f = new File(path);
    	return f.exists() && !f.isDirectory();
    }
}
