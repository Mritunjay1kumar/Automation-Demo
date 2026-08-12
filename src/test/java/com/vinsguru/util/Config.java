package com.vinsguru.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final Logger log = LoggerFactory.getLogger(Config.class);
    public static final String DEFAULT_PROPERTIES="config/default.properties";
    private static Properties prop = new Properties();

    public static void initialize(){

        //load default properties
        prop=loadProperties();

        //check for any updates

        for(String key : prop.stringPropertyNames()){
            if(System.getProperties().containsKey(key)){
                prop.setProperty(key,System.getProperty(key));
            }
        }

        //print
        log.info("Test Properties: "+prop );
        log.info("============");
        for(String key : prop.stringPropertyNames()){
            log.info("Key: "+key+" Value: "+prop.getProperty(key));
        }
        log.info("============");

    }

    public static String get(String key){
        return prop.getProperty(key);
    }

    public static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream stream=ResourceLoader.getResource(DEFAULT_PROPERTIES)){
            properties.load(stream);
        }catch (Exception e){
            log.info("Could not load properties from "+DEFAULT_PROPERTIES);
        }
        return properties;
    }
}
