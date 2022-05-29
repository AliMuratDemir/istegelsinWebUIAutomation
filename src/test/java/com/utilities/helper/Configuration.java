package com.utilities.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Configuration {

    private static Configuration instance;
    private Properties configProps = new Properties();

    private String environment;
    private String key;
    private String testiniumHubURL;

    private String webURL;


    public static Configuration getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
    }

    private Configuration() {
        InputStream is = null;

        try {
            is = ClassLoader.getSystemResourceAsStream("config.properties");
            Reader reader = new InputStreamReader(is, "UTF-8");
            configProps.load(reader);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            this.environment = System.getProperties().getProperty("environment");
            this.key = System.getenv("key");
            this.testiniumHubURL = configProps.getProperty("testinium.hubURL");
            this.webURL = configProps.getProperty("web.URL");

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String[][] stringTo2DArray(String string) {
        String[][] datas = new String[(string.split("\\|\\|")).length][(string.split("\\|\\|")[0]).split(",").length];

        for (int i = 0; i < (string.split("\\|\\|")).length; i++) {
            datas[i] = (string.split("\\|\\|")[i]).split(",");
        }
        return datas;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getKey() {
        return key;
    }

    public String getTestiniumHubURL() {
        return testiniumHubURL;
    }

    public String getWebURL() {
        return webURL;
    }

   }

