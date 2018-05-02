package testsYandex;

import net.thucydides.core.annotations.Title;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.util.List;
import java.util.Set;

/**
 *1. Открыть браузер и развернуть на весь экран.
 *2. Зайти на yandex.ru.
 *3. Вбить в строку поиска "пылесос dyson"
 *4. Открыть ссылку фирменного магазина
 *5. Проверить, что на открывшейся форме есть пылесос технологией Ball
 *6. В верхнем меню выбрать вкладку Ассортимент и перейти в выпадающем меню на вкладку Насадки и аксессуары
 *7. Проверить, что элементов на странице 15.
 *8. Проверить, что на открывшейся форме есть фильтр для очистителя воздуха
 *9. Закрыть браузер
 */


@Features("ru.testsYandex.TestVacuumCleaner")
@Stories("Yandex. Поиск пылесоса Dyson")
public class TestVacuumCleaner extends BaseSetup {

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    private static class TestData {
        static final String textFindVacuumCleaner = "пылесос dyson";
        static final String vacuumCleaner = "Компактные и мощные пылесосы с технологией Ball.\n" +
                                                "Легко обходят мебель и другие препятствия";
        static final String airPurifierFilter = "Фильтр для очистителя воздуха";
    }

    @Title("Открытие поисковой страницы yandex.ru")
    @Test()
    public void openPageYandex() {
        driver.get("https://yandex.ru");
        System.out.println("Page title is: " + driver.getTitle());
    }

    @Title("Вводим в строку поиска Пылесос Дайсон")
    @Test(dependsOnMethods = "openPageYandex")
    public void inputVacuumCleanerName() {
        driver.findElement(By.cssSelector("#text")).sendKeys(TestData.textFindVacuumCleaner);
        driver.findElement(By.cssSelector("div.search2__button")).click();
    }

    @Title("Открываем ссылку фирменного магазина Dyson")
    @Test(dependsOnMethods = "inputVacuumCleanerName")
    public void openStoreDyson() throws InterruptedException {
        String textLinkDysonShop = driver.findElement(By.xpath("//a[contains(., 'dyson-vacuums')]")).getText();
        System.out.println(textLinkDysonShop);
        Assert.assertEquals("dyson-vacuums", textLinkDysonShop);
        String parentWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[contains(., 'dyson-vacuums')]")).click();
    }

    @Title("Проверить, что на открывшейся форме есть пылесос Dyson Cyclone V10 Animal")
    @Test(dependsOnMethods = "openStoreDyson")
    public void checkPresenceVacuumCleanerOnPage() {
        String parentWindow = driver.getWindowHandle();
        Set<String> handles =  driver.getWindowHandles();
        for(String windowHandle  : handles) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
            }
        }
        String fullVacuumCleanerBall = driver.findElement(By.cssSelector("#ctl00_content_11_InnerCarousel_SlidesRepeater_" +
                "ctl00_standardSlide_blockGroupContainerControl_" +
                "BlockGroupsRepeater_ctl00_ctl00_BlockRepeater_ctl02_RichTextBlock_RichTextBlockInnerDiv")).getText();
        System.out.println(fullVacuumCleanerBall);
        Assert.assertEquals(TestData.vacuumCleaner, fullVacuumCleanerBall);
    }

    @Title("В верхнем меню выбрать вкладку Ассортимент и перейти в выпадающем меню на вкладку Насадки и аксессуары")
    @Test(dependsOnMethods = "checkPresenceVacuumCleanerOnPage")
    public void goTabNozzlesAndAccessories() {
        WebElement assortment = driver.findElement(By.cssSelector(
                "a#ctl00_cphNavigation_topNavigation1_TopNavigationLinksRepeater_ctl02_TopNavigationLink.selectedItemMenu"));
        Actions action = new Actions(driver);
        action.moveToElement(assortment).click().perform();
        driver.findElement(By.cssSelector("#ctl00_cphNavigation_topNavigation1_TopNavigationLinksRepeater_ctl02_" +
                "TopNavigationDetailRepeater_ctl06_SubMenuLink")).click();
    }

    @Title("Проверить, что на странице Насадки и аксессуары отображается 15 элементов")
    @Test(dependsOnMethods = "goTabNozzlesAndAccessories")
    public void checkNumberElements() {
        List<WebElement> element = driver.findElements(By.cssSelector(".list-image-container"));
        if (element.size() == 15) {
            System.out.println("На странице представлено 15 Насадок и акссесуаров");
        } else
            System.out.println("На странице представлено Насадок и акссесуаров не соответствует ожидаемому количеству");
        }

    @Title("Проверить, что на открывшейся форме есть фильтр для очистителя воздуха")
    @Test(dependsOnMethods = "checkNumberElements")
    public void checkAirFilter() {
        String filter = driver.findElement(By.xpath(("//a[contains(.,'Фильтр для очистителя воздуха')]"))).getText();
        System.out.println(filter);
        Assert.assertEquals(TestData.airPurifierFilter, filter);
    }
}

