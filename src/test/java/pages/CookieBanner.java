package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieBanner {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By banner = By.xpath("//div[contains(@class,'cookie') and contains(@class,'show')]");
    private final By declineBtn = By.xpath(
            "//div[contains(@class,'cookie') and contains(@class,'show')]//button[" +
                    "contains(@class,'cookie__cancel') or normalize-space(.)='Отклонить' or @data-close='Отклонить']"
    );
    private final By acceptBtn = By.xpath(
            "//div[contains(@class,'cookie') and contains(@class,'show')]//button[" +
                    "contains(@class,'cookie__agree') or normalize-space(.)='Принять' or @id='cookie-agree']"
    );

    public CookieBanner(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    public boolean isShown() {
        try {
            return driver.findElement(banner).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void decline() {
        if (!isShown()) return;
        System.out.println("Закрываем cookie-баннер");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(declineBtn));
        safeClick(btn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(banner));
    }

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException | JavascriptException ignored) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}