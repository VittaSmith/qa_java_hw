package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CookieBanner;
import pages.OnlineTopUpBlock;
import pages.PaymentModal;
import io.qameta.allure.*;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.*;

@Epic("MTS BY")
@Feature("Онлайн пополнение без комиссии")
public class MtsOnlineTopUpTests extends BaseTest {
    private final String BASE_URL = "https://www.mts.by/";

    @Story("UI: titleIsCorrect")
    @Owner("Виктория")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка названия блока «Онлайн пополнение без комиссии»")
    public void titleIsCorrect() {
        driver.get(BASE_URL);
        new CookieBanner(driver).decline();

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        String title = block.getTitle();
        assertTrue(title.contains("онлайн пополнение без комиссии"),
                "Заголовок блока некорректный: " + title);
    }

    @Story("UI: paymentLogosVisible")
    @Owner("Виктория")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка наличия логотипов платёжных систем")
    public void paymentLogosVisible() {
        driver.get(BASE_URL);
        new CookieBanner(driver).decline();

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        assertTrue(block.hasKnownPaymentLogos(), "Логотипы платёжных систем не найдены");
    }

    @Story("UI: moreDetailsWorks")
    @Owner("Виктория")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка перехода по ссылке «Подробнее о сервисе»")
    public void moreDetailsWorks() {
        driver.get(BASE_URL);
        new CookieBanner(driver).decline();

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        String before = block.currentUrl();
        block.clickMoreDetails();
        String after = block.currentUrl();

        assertNotEquals(after, before, "URL не изменился после клика по ссылке «Подробнее о сервисе»");
    }

    @Story("UI: checkAllServiceTypesPlaceholders")
    @Owner("Виктория")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Поиск различных услуг")
    public void checkAllServiceTypesPlaceholders() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        block.openTab(OnlineTopUpBlock.PaymentTab.MOBILE);

        String[] serviceTypes = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};
        for (String type : serviceTypes) {
            System.out.println("Проверяем тип услуги: " + type);
            block.selectServiceType(type);
            Map<String, String> placeholders = block.getVisiblePlaceholders();
            assertFalse(placeholders.isEmpty(), "Не найдены плейсхолдеры для типа: " + type);
        }
    }

    @Story("UI: fillAndContinue")
    @Owner("Виктория")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка варианта «Услуги связи»: заполнение полей, нажатие «Продолжить», окно оплаты")
    public void fillAndContinue() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        block.openTab(OnlineTopUpBlock.PaymentTab.MOBILE);
        block.setPhone("297777777");
        block.setAmount("250");
        Set<String> before = driver.getWindowHandles();
        block.clickContinue();
        switchToCheckoutContext(driver, before, 30);

        PaymentModal modal = new PaymentModal(driver);

        //телефон
        String phoneRaw = modal.getDisplayedPhone();
        String phoneDigits = phoneRaw.replaceAll("\\D", "");
        Assert.assertTrue(phoneDigits.contains("375297777777"),
                "Телефон отображается неверно: " + phoneRaw);

        //сумма
        String amountText = modal.getDisplayedAmount();
        String amountNum = amountText.replaceAll("[^\\d.,]", "").replace(",", ".");
        Assert.assertTrue(amountNum.startsWith("250"),
                "Сумма отображается неверно: " + amountText);

        //кнопка оплаты
        String buttonText = modal.getContinueButtonText();
        Assert.assertTrue(buttonText.toLowerCase().contains("оплат"),
                "Кнопка оплаты не найдена или неверна: " + buttonText);

        //плейсхолдеры карты
        Map<String, String> placeholders = modal.getCardPlaceholders();
        Assert.assertFalse(placeholders.isEmpty(), "Плейсхолдеры полей карты не найдены");

        //иконки платежных систем
        Assert.assertTrue(modal.hasPaymentIcons(), "Нет иконок платёжных систем");
    }

    private void switchToCheckoutContext(org.openqa.selenium.WebDriver driver,
                                         Set<String> beforeHandles,
                                         int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));

        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[src*='checkout'],iframe[src*='bepaid'],iframe[id*='pay'],iframe[name*='pay']")));
            driver.switchTo().frame(iframe);
            System.out.println("--> Переключились в iframe оплаты");
            return;
        } catch (Exception ignored) {}

        try {
            wait.until(d -> {
                Set<String> now = d.getWindowHandles();
                if (now.size() > beforeHandles.size()) {
                    for (String h : now) if (!beforeHandles.contains(h)) {
                        d.switchTo().window(h);
                        break;
                    }
                    System.out.println("--> Переключились в новую вкладку оплаты");
                    return true;
                }
                return false;
            });
            return;
        } catch (Exception ignored) {}

        wait.until(d -> {
            String url = d.getCurrentUrl().toLowerCase();
            return url.contains("checkout") || url.contains("bepaid") || url.contains("payment");
        });
        System.out.println("--> Оплата открыта в текущем окне (редирект)");
    }
}