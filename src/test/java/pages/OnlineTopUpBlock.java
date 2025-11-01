package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.Set;

public class OnlineTopUpBlock {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By root = By.xpath(
            "//section[.//h2[contains(normalize-space(.), 'Онлайн пополнение без комиссии')]]"
    );
    private final By openSelectOverlay = By.cssSelector(".select__options, .select._open .select__options");


    public OnlineTopUpBlock(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
    }

    private WebElement scope(){
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(root));
        scrollIntoView(el);
        return el;
    }

    private boolean isMac() {
        return System.getProperty("os.name", "").toLowerCase().contains("mac");
    }
    private Keys selectAllModifier() {
        return isMac() ? Keys.COMMAND : Keys.CONTROL;
    }

    private void closeAnyOpenSelect() {
        if (!driver.findElements(openSelectOverlay).isEmpty()) {
            try {
                new org.openqa.selenium.interactions.Actions(driver).moveByOffset(5, 5).click().perform();
            } catch (Exception ignored) {}
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(openSelectOverlay));
        }
    }

    private void safeClick(WebElement el) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (ElementClickInterceptedException | JavascriptException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    // Проверить название указанного блока;
    public String getTitle() {
        String title = scope().findElement(By.xpath(".//h2")).getText()
                .replace('\u00A0', ' ')//пробел
                .replaceAll("\\s+", " ")//перенос
                .trim();
        System.out.println("Заголовок блока: " + title);
        return title.toLowerCase();
    }

    /*
    Проверить наличие логотипов платёжных систем;
            //img[contains(@alt,'Visa')]
            //img[contains(@alt,'Verified By Visa')]
            //img[contains(@alt,'MasterCard')]
            //img[contains(@alt,'MasterCard Secure Code')]
            //img[contains(@alt,'Белкарт')]
    */
    public boolean hasKnownPaymentLogos() {
        String[] keywords = {"Visa", "MasterCard", "Белкарт", "MIR", "МИР", "Apple Pay", "Google Pay"};

        for (String word : keywords) {
            By by = By.xpath(
                    ".//img[contains(@alt, '" + word + "')]" +
                            " | .//*[name()='svg' and contains(@aria-label, '" + word + "')]"
            );
            try {
                WebElement logo = scope().findElement(by);
                if (logo.isDisplayed()) {
                    System.out.println("Найден логотип: " + word);
                    return true;
                }
            } catch (NoSuchElementException ignored) {}
        }
        return false;
    }

    // Проверить работу ссылки «Подробнее о сервисе»;
    public void clickMoreDetails() {
        Set<String> before = driver.getWindowHandles();
        WebElement link = scope().findElement(By.xpath(
                ".//a[contains(.,'Подробнее о сервисе')]"
                ));
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();

        Set<String> after = driver.getWindowHandles();
        if (after.size() > before.size()) {
            after.removeAll(before);
            String newHandle = after.iterator().next();
            driver.switchTo().window(newHandle);
        }
        wait.until(d -> !d.getCurrentUrl().isEmpty());
    }

    public void chooseMobileServices() {
        By tab = By.xpath(
                ".//button[contains(.,'Услуги связи')]"
                        + " | .//a[contains(.,'Услуги связи')]"
                        + " | .//label[contains(.,'Услуги связи')]"
        );
        WebElement el = scope().findElement(tab);
        scrollIntoView(el);
        System.out.println("Выбираем вкладку: Услуги связи");
        safeClick(el);
        closeAnyOpenSelect();
    }

    public void setPhone(String phone) {
        WebElement phoneInput = scope().findElement(By.xpath(
                ".//input[@class='phone' and @id='connection-phone']"
        ));
        System.out.println("Вводим номер телефона: " + phone);
        wait.until(ExpectedConditions.visibilityOf(phoneInput));
        closeAnyOpenSelect();
        scrollIntoView(phoneInput);
        safeClick(phoneInput);

        phoneInput.sendKeys(Keys.chord(selectAllModifier(), "A"));
        phoneInput.sendKeys(Keys.DELETE);
        phoneInput.sendKeys(phone);
    }

    public void setAmount(String amount) {
        WebElement amountInput = scope().findElement(By.xpath(
                ".//input[@class='total_rub' and @id='connection-sum']"
        ));
        System.out.println("Вводим сумму: " + amount);
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        scrollIntoView(amountInput);
        safeClick(amountInput);

        amountInput.sendKeys(Keys.chord(selectAllModifier(), "A"));
        amountInput.sendKeys(Keys.DELETE);
        amountInput.sendKeys(amount);
    }
    //Заполнить поля и проверить работу кнопки «Продолжить» (проверяем только вариант «Услуги связи», номер 297777777)
    public void clickContinue() {
        WebElement btn = scope().findElement(By.xpath(".//button[contains(., 'Продолжить')]"));
        scrollIntoView(btn);
        System.out.println("Нажимаем кнопку «Продолжить»");
        safeClick(btn);
    }

    public boolean nextStepVisible() {
        try {
            WebElement marker = new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h1|//h2|//div[contains(@class,'step') or contains(@class,'success') or contains(.,'Оплата')]")
                    ));
            System.out.println("Переход на следующий шаг подтверждён");
            return marker.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Следующий шаг не найден!");
            return false;
        }
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
