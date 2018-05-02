package testsYandex;

import net.thucydides.core.annotations.Title;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;


public class BaseSetup {

    public static WebDriver driver;
    public static WebDriverWait wait;
    //private static String fireFoxDr = "webdriver.gecko.driver";
    //private static String pathFireFox = "C:/Users/Public/AllureAutomation/src/test/resources/drivers/geckodriver.exe";
    private static String chromeDr = "webdriver.chrome.driver";
    private static String pathChrome = "C:/Users/Public/AllureAutomation/src/main/resources/drivers/chromedriver.exe";

    @Title("Запуск браузера")
    @BeforeTest
    protected void startBrowser() {
        System.setProperty(chromeDr, pathChrome);
        //System.setProperty(fireFoxDr, pathFireFox);
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
    }

    @Title("Остановка браузера")
    @AfterTest
    protected void stopBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}



