package com.primeradiants;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main 
{
	
	private static Logger logger = new Logger();
	
    public static void main(String[] args) throws ParserConfigurationException, IOException
    {
        logger.info("Generating novent.js...");
        
        String path = getProgramPath();
        File descriptor = new File(path + "\novent-descriptor.xml");
        Document xmlDescriptor = parseXmlFile(descriptor);
        
        if(xmlDescriptor == null) {
        	logger.error("Unreadable xml file!");
        	logger.error("Build failure.");
        	return;
        }
        
        
        
        logger.info("Build success.");
    } 
    
    public static String getProgramPath() throws UnsupportedEncodingException {
        URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }
    
    public static Document parseXmlFile(File file) {
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			return dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			return null;
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
    }
    
    public static boolean isExistingFile(String path) {
    	File f = new File(path);
    	return f.exists() && !f.isDirectory();
    }
}
