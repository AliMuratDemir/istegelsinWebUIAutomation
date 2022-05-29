package com.utilities.base;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.utilities.helper.Browser;
import com.utilities.helper.Configuration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BaseTest {

    protected static WebDriver driver;
    private static final Logger log = Logger.getLogger(BaseTest.class);
    private static Configuration config = Configuration.getInstance();
    //private apiTestExample apiTest =new apiTestExample();
    private static Browser browser = new Browser();

    @BeforeScenario
    public void setUp() {
        PropertyConfigurator.configure("properties/log4j.properties");
        log.info("Settings Installation Start!");

        if (config.getKey() != null) {
            /** TESTINIUM ENV **/
            browser.createTestiniumDriver(config.getKey());

        } else {

            /** LOCAL ENV **/
            browser.createLocalDriver();

        }
    }

    @AfterScenario
    public void tearDown() throws UnirestException {

        System.out.println("SESSIONID:  " + ((RemoteWebDriver) driver).getSessionId());
        //apiTest.getNodeIp((((RemoteWebDriver) driver).getSessionId()).toString());

        driver.manage().timeouts().pageLoadTimeout(5, SECONDS);

       // getDriver().quit();
        //log.info("Driver close");
    }



    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(RemoteWebDriver driver) {
        BaseTest.driver = driver;
    }

}