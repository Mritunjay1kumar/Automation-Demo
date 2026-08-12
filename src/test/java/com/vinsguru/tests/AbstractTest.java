package com.vinsguru.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.vinsguru.listeners.TestListners;
import com.vinsguru.util.Config;
import com.vinsguru.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Listeners({TestListners.class})
public abstract class AbstractTest {

    protected WebDriver driver;
    public static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeSuite(alwaysRun = true)
    public void setUpConfig(){
        Config.initialize();
    }

    @BeforeTest
//    @Parameters({"browser"})
    public void setDriver(ITestContext ctx) throws MalformedURLException {
        if(Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED))){
            this.driver=getremoteDriver();
            ctx.setAttribute(Constants.DRIVER, this.driver);
        }
        else{
            this.driver=getLocalDriver();
        }

    }

    private WebDriver getremoteDriver() throws MalformedURLException {
        Capabilities caps=new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            caps=new FirefoxOptions();
        }
        String urlFormat=Config.get(Constants.GRID_URL_FORMAT);
        String hubHost=Config.get(Constants.GRID_HUB_HOST);
        String url=String.format(urlFormat,hubHost);
        log.info("Setting up remote WebDriver"+url);

        return new RemoteWebDriver(new URL(url),caps);
    }

    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }


    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

    @AfterMethod(enabled = false)
    public void sleep(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
    }

}
