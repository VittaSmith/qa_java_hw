package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.*;

public class OnlineTopUpBlock {
    public enum PaymentTab {
        MOBILE("Услуги связи"),
        HOME_INTERNET("Домашний интернет"),
        INSTALLMENT("Рассрочка"),
        DEBT("Задолженность");

        public final String title;
        PaymentTab(String title) { this.title = title; }
    }

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By root = By.xpath(
            "//section[.//h2[contains(normalize-space(.), 'Онлайн пополнение без комиссии')]]");
    private final By openSelectOverlay = By.xpath(
            "//*[contains(@class,'select__options') or contains(@class,'select _open')]"
    );

    public OnlineTopUpBlock(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    private WebElement scope(){
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(root));
        scrollIntoView(el);
        return el;
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
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
        String[] keywords = {"Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт", "MIR", "МИР", "Apple Pay", "Google Pay"};
        boolean foundAny = false;

        for (String word : keywords) {
            By by = By.xpath(
                    ".//img[contains(@alt, '" + word + "')]" +
                            " | .//*[name()='svg' and (contains(@aria-label, '" + word + "') or contains(@title, '" + word + "'))]"
            );
            for (WebElement logo : scope().findElements(by)) {
                if (logo.isDisplayed()) {
                    System.out.println("Найден логотип: " + word);
                    foundAny = true;
                }
            }
        }
        return foundAny;
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

    //переключение услуг
    public void openTab(PaymentTab tab) {
        By tabBy = By.xpath(".//button[contains(.,'" + tab.title + "')]"
                + " | .//a[contains(.,'" + tab.title + "')]"
                + " | .//label[contains(.,'" + tab.title + "')]");
        WebElement el = scope().findElement(tabBy);
        scrollIntoView(el);
        System.out.println("--> Выбираем вкладку: " + tab.title);
        safeClick(el);
        closeAnyOpenSelect();
    }

    // выпадающий список услуг
    public void selectServiceType(String typeName) {
        WebElement dropdown = scope().findElement(By.xpath(".//div[contains(@class,'select__wrapper')]"));
        WebElement header = dropdown.findElement(By.xpath(".//button[contains(@class,'select__header')]"));
        scrollIntoView(header);
        System.out.println("--> Открываем список типов услуг");
        safeClick(header);

        By listVisible = By.xpath(".//ul[contains(@class,'select__list') and contains(@style,'opacity')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(listVisible));

        By option = By.xpath("//li[contains(@class,'select__item')]" +
                "//p[normalize-space(text())='" + typeName + "']");
        WebElement item = dropdown.findElement(option);
        System.out.println("--> Выбираем вариант: " + typeName);
        safeClick(item);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(listVisible));
    }

    // получение плейсхолдеров для всех видимых input
    public Map<String, String> getVisiblePlaceholders() {
        WebElement block = scope();
        Map<String, String> result = new LinkedHashMap<>();
        List<WebElement> inputs = block.findElements(By.xpath(
                ".//input[not(@type='hidden') and not(contains(@style,'display:none'))]"
        ));
        for (WebElement input : inputs) {
            String id = input.getAttribute("id");
            String name = input.getAttribute("name");
            String placeholder = input.getAttribute("placeholder");

            if (placeholder != null && !placeholder.isBlank()) {
                String key = (name != null && !name.isBlank()) ? name : id;
                result.put(key, placeholder.trim());
                System.out.println("--> placeholder [" + key + "] = \"" + placeholder.trim() + "\"");
            }
        }
        return result;
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

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}