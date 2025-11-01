package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentModal {
    private final WebDriver driver;
    private final WebDriverWait wait;

    //Окно оплаты
    private final By root = By.xpath(
            "//div[contains(@class,'app-wrapper__content')] | //section[contains(@class,'payment')]"
    );

    public PaymentModal(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.until(ExpectedConditions.visibilityOfElementLocated(root));
    }

    private WebElement scope() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(root));
    }

    // ищем отображаемый номер
    public String getDisplayedPhone() {
        By by = By.xpath(".//div[contains(@class,'pay-description__text')]//span");
        return java.util.Optional.ofNullable(scope().findElement(by).getText())
                .orElse("")
                .replace('\u00A0',' ')
                .trim();
    }

    // ищем куда ввести номер
    public String getDisplayedAmount() {
        By by = By.xpath(".//div[contains(@class,'pay-description__cost')]//span");
        return java.util.Optional.ofNullable(scope().findElement(by).getText())
                .orElse("")
                .replace('\u00A0',' ')
                .trim();
    }

    // собираем плейсхолдеры полей карты
    public Map<String, String> getCardPlaceholders() {
        Map<String, String> res = new LinkedHashMap<>();
        By[] locators = new By[] {
                By.xpath(".//input[@formcontrolname='creditCard']"),
                By.xpath(".//input[@formcontrolname='expirationDate']"),
                By.xpath(".//input[@formcontrolname='cvc']"),
                By.xpath(".//input[@formcontrolname='holder']")
        };
        for (By by : locators) {
            List<WebElement> els = scope().findElements(by);
            if (!els.isEmpty()) {
                WebElement in = els.get(0);
                String name = in.getAttribute("formcontrolname");
                String ph = Optional.ofNullable(in.getAttribute("placeholder")).orElse("").trim();
                res.put(name, ph);
                System.out.println("--> placeholder [" + name + "] = \"" + ph + "\"");
            }
        }
        return res;
    }

    // кнопка оплаты
    public String getContinueButtonText() {
        By by = By.xpath(".//button[.//span[contains(text(),'Оплатить')]]");
        return scope().findElement(by).getText().replace('\u00A0',' ').trim();
    }

    //платежные иконки
    public boolean hasPaymentIcons() {
        By iconsContainer = By.xpath(".//div[contains(@class,'icons-container')]");
        try {
            WebElement container = scope().findElement(iconsContainer);
            List<WebElement> icons = container.findElements(By.xpath(
                    ".//img[contains(@src,'payment-icons')]"));
            if (icons.isEmpty()) {
                System.out.println("--> Иконки платёжных систем не найдены в контейнере!");
                return false;
            }
            String[] keywords = {"visa", "mastercard", "Белкарт", "mir", "maestro", "apple", "google"};
            boolean foundAny = false;
            for (WebElement icon : icons) {
                String src = icon.getAttribute("src").toLowerCase();
                for (String key : keywords) {
                    if (src.contains(key)) {
                        System.out.println("--> Найдена иконка платёжной системы: " + key.toUpperCase());
                        foundAny = true;
                        break;
                    }
                }
            }
            return foundAny;
        } catch (NoSuchElementException e) {
            System.out.println("--> Контейнер иконок (.icons-container) не найден");
            return false;
        }
    }
}