package tests;

import base.BaseTest;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.CookieBanner;
import pages.OnlineTopUpBlock;

import java.time.Duration;

import static org.testng.Assert.*;

public class MtsOnlineTopUpTests extends BaseTest {

    @Test(description = "Заголовок блока корректный")
    public void titleIsCorrect() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.tagName("body")));

        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        assertTrue(block.getTitle().contains("онлайн пополнение без комиссии"));
    }

    @Test(description = "Есть логотипы платёжных систем")
    public void paymentLogosVisible() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();
        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        assertTrue(block.hasKnownPaymentLogos());
    }

    @Test(description = "«Подробнее о сервисе» ведёт на страницу описания/помощи")
    public void moreDetailsWorks() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();
        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        String before = block.currentUrl();
        block.clickMoreDetails();
        String after = block.currentUrl();
        assertNotEquals(after, before, "URL не изменился после клика по «Подробнее о сервисе»");
    }

    @Test(description = "Услуги связи: заполнение и «Продолжить»")
    public void fillAndContinue() {
        driver.get("https://www.mts.by/");
        new CookieBanner(driver).decline();
        OnlineTopUpBlock block = new OnlineTopUpBlock(driver);
        block.chooseMobileServices();
        block.setPhone("297777777");
        block.setAmount("2");
        block.clickContinue();
        assertTrue(block.nextStepVisible(), "Нет признаков перехода на следующий шаг/оплату");
    }
}