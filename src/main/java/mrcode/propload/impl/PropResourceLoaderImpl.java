package mrcode.propload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import mrcode.propload.PropResourceLoader;

public class PropResourceLoaderImpl implements PropResourceLoader {
    
    private final Class loaderClass;
    
    public PropResourceLoaderImpl() {
        this.loaderClass = this.getClass();
    }
    
    public PropResourceLoaderImpl(Class loaderClass) {
        this.loaderClass = loaderClass;
    }

    public Properties loadProperties(String location) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        if (location.startsWith("file:")) {
            String filename = StringUtils.remove(location, "file:");
            System.out.println("Loading file: " + filename);
            return loadPropertiesFromFile(filename);
        }
        if (location.startsWith("classpath:")) {
            String filename = StringUtils.remove(location, "classpath:");
            System.out.println("Loading from classpath: " + filename);
            return loadPropertiesFromClasspath(filename);
        }
        return null;
    }

    private Properties loadPropertiesFromClasspath(String filename) {
        InputStream inputStream = loaderClass.getClassLoader().getResourceAsStream(filename);
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    protected Properties loadPropertiesFromFile(String filename) {
        File file = new File(filename);
        if (file.canRead()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    Properties properties = new Properties();
                    properties.load(fileInputStream);
                    return properties;
                } catch (IOException e) {
                } finally {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e) {
            }
        }
        return null;
    }

}
