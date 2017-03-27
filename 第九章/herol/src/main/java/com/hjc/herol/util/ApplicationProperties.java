package com.hjc.herol.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private Properties props = new Properties();

    public ApplicationProperties() {
        InputStream infile;
        String filePath = System.getProperty("sysProperties", "/application.properties");
        try {
            if (filePath != null && !filePath.equals("/application.properties")) {
                infile = new FileInputStream(filePath);
            }
            else {
                infile = getClass().getResourceAsStream(filePath);
            }
            props.load(infile);
            infile.close();
        }
        catch (FileNotFoundException ex) {
        }
        catch (IOException ex) {
        }
    }

    public String getProperty(String key) {
        return this.props.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return this.props.getProperty(key, defaultValue);
    }

}
