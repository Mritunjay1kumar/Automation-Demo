package com.vinsguru.listeners;

import com.vinsguru.util.Constants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListners implements ITestListener {

    @Override
     public void onTestFailure(ITestResult result) {
        TakesScreenshot driver=(TakesScreenshot) result.getTestContext().getAttribute(Constants.DRIVER);
        String screenshotPath=driver.getScreenshotAs(OutputType.BASE64);
        String htmlImageFormat =
                "<img width=700 src='data:image/png;base64,%s'/>";

        String htmlImage=String.format(htmlImageFormat, screenshotPath);

        Reporter.log("Screenshot: " + htmlImage);
    }

}
