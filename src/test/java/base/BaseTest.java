package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result != null && !result.isSuccess()) {
                attachPageScreenshot();
                attachPageSource();
                attachBrowserConsole();
            }
        } catch (Throwable ignored) {}
        if (driver != null) driver.quit();
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachPageScreenshot() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Throwable t) {
            return new byte[0];
        }
    }

    @Attachment(value = "Page source", type = "text/html", fileExtension = ".html")
    private byte[] attachPageSource() {
        try {
            return driver.getPageSource().getBytes(StandardCharsets.UTF_8);
        } catch (Throwable t) {
            return "<no source>".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Attachment(value = "Browser console", type = "text/plain")
    private String attachBrowserConsole() {
        try {
            List<LogEntry> logs = driver.manage().logs().get(LogType.BROWSER).getAll();
            return logs.stream()
                    .map(e -> "[" + e.getLevel() + "] " + e.getMessage())
                    .collect(Collectors.joining("\n"));
        } catch (Throwable t) {
            return "<no console logs>";
        }
    }
}
