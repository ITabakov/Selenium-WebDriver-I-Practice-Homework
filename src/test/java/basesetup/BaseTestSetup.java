package basesetup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import enums.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.*;

public abstract class BaseTestSetup {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @AfterEach
    public void classTearDown() {

        resetAppState();

        driver.close();

    }

    private static void resetAppState() {
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='reset_sidebar_link']"))).click();
    }

    protected static void startWithBrowser(BrowserType browserType) {
        switch (browserType) {
            case EDGE:
                driver = new EdgeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
                driver = new ChromeDriver();
                break;
            case EDGE_HEADLESS:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless-new");
                driver = new EdgeDriver(edgeOptions);
                break;
            case FIREFOX_HEADLESS:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case CHROME_HEADLESS:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new");
                driver = new ChromeDriver(chromeOptions);
                break;
            case CHROME_FOR_TESTING:
                ChromeOptions chromeForTestingOptions = new ChromeOptions();
                chromeForTestingOptions.setBinary("C:\\Chrome for testing\\chrome-win64");
                driver = new ChromeDriver(chromeForTestingOptions);

        }
    }

    protected void addTwoProductsToShoppingCart(BrowserType browserType) {
        startWithBrowser(browserType);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get(BASE_URL);

        authenticateWithUser(USERNAME, PASSWORD);

        assertCurrentPageUrl(INVENTORY_PAGE_URL, driver.getCurrentUrl());

        addBackpackAndTshirtToShoppingCart();

        goToShoppingCart();

        assertCurrentPageUrl(SHOPPING_CART_PAGE_URL, driver.getCurrentUrl());

        assertItemsAndPrices();
    }

    protected static void authenticateWithUser(String username, String password) {

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//input[@data-test='username']"))));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@data-test='password']"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//input[@data-test='login-button']"));
        loginButton.click();

    }

    protected static void addBackpackAndTshirtToShoppingCart() {

        // Add Backpack and T -shirt to shopping cart
        WebElement backpackAddToCardButton = driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-backpack']"));
        backpackAddToCardButton.click();
        WebElement tShirtAddToCardButton = driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-bolt-t-shirt']"));
        tShirtAddToCardButton.click();

    }

    protected void assertItemsAndPrices() {
        // Assert Items
        assertProductTitle("(//div[@class='inventory_item_name'])[1]", "Sauce Labs Backpack");
        assertProductTitle("(//div[@class='inventory_item_name'])[2]", "Sauce Labs Bolt T-Shirt");
        // Assert Products Prices
        assertProductPrice("(//div[@class='inventory_item_price'])[1]", 29.99);
        assertProductPrice("(//div[@class='inventory_item_price'])[2]", 15.99);
    }

    protected void goToShoppingCart() {
        // Click on Shopping Cart
        WebElement shoppingCartIcon = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        shoppingCartIcon.click();
    }

    protected void checkout() {
        // Click on Checkout
        WebElement checkoutButton = driver.findElement(By.xpath("//button[@data-test='checkout']"));
        checkoutButton.click();

        // Assert Fill Contact Details Page
        assertCurrentPageUrl(USER_DETAILS_PAGE_URL, driver.getCurrentUrl());

        fillUserDetails();

        goToOverviewPage();

        // Assert Overview Page
        assertCurrentPageUrl(OVERVIEW_PAGE_URL, driver.getCurrentUrl());

        assertOverviewPage();
    }

    protected void fillUserDetails() {
        // Fill User Details
        WebElement firstNameInput = driver.findElement(By.xpath("//input[@data-test='firstName']"));
        firstNameInput.sendKeys("Ivailo");
        WebElement lastNameInput = driver.findElement(By.xpath("//input[@data-test='lastName']"));
        lastNameInput.sendKeys("Tabakov");
        WebElement postalCodeInput = driver.findElement(By.xpath("//input[@data-test='postalCode']"));
        postalCodeInput.sendKeys("1233");
    }

    protected void goToOverviewPage() {
        // Click on Continue
        WebElement continueButton = driver.findElement(By.xpath("//input[@data-test='continue']"));
        continueButton.click();
    }

    protected void assertOverviewPage() {
        //Assert Items
        Assertions.assertEquals("Sauce Labs Backpack",
                driver.findElement(By.xpath("//a[@id='item_4_title_link']/div[@class='inventory_item_name']")).getText(),
                "Wrong product title");
        Assertions.assertEquals("Sauce Labs Bolt T-Shirt",
                driver.findElement(By.xpath("//a[@id='item_1_title_link']/div[@class='inventory_item_name']")).getText(),
                "Wrong product title");
        // Assert Products prices
        assertProductPrice("//*[@id='checkout_summary_container']/div/div[1]/div[3]/div[2]/div[2]/div", 29.99);
        assertProductPrice("//*[@id='checkout_summary_container']/div/div[1]/div[4]/div[2]/div[2]/div",
                15.99);
        // Assert Total Price and Tax
        String[] productPricesXpaths = {"//*[@id='checkout_summary_container']/div/div[1]/div[3]/div[2]/div[2]/div",
                "//*[@id='checkout_summary_container']/div/div[1]/div[4]/div[2]/div[2]/div"};
        assertTotalprice(45.98, productPricesXpaths);
        assertTotalPriceWithTax("//div[@class='summary_subtotal_label']",
                "//div[@class='summary_tax_label']", 45.98);
    }

    protected void finishOrder() {
        WebElement finishButton = driver.findElement(By.xpath("//button[@data-test='finish']"));
        finishButton.click();
    }

    protected void assertFinishedOrder() {
        // Assert Checkout complete page
        assertCurrentPageUrl("https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());
        // Assert Complete Header Message
        Assertions.assertEquals("Thank you for your order!",
                driver.findElement(By.xpath("//h2[@class='complete-header']")).getText(),
                "Wrong complete header");
    }

    protected static void assertCurrentPageUrl(String expectedUrl, String currentUrl) {

        Assertions.assertEquals(expectedUrl, currentUrl,PAGE_ERROR_MESSAGE);

    }

    protected static void assertProductTitle(String xpath, String expectedTitle) {
        Assertions.assertEquals(expectedTitle, driver.findElement(By.xpath(xpath)).getText(), "Wrong product title");
    }

    protected static double getProductPrice(String xpath) {
        return Double.parseDouble(driver.findElement(By.xpath(xpath)).getText().substring(1));
    }

    protected static void assertProductPrice(String xpath, double expectedPrice) {
        Assertions.assertEquals(expectedPrice, getProductPrice(xpath), "Wrong product price");
    }

    protected static void assertTotalprice(double expectedTotalSum, String... productXpaths) {
        double productsSum = 0;
        for (int i = 0; i < productXpaths.length; i++) {
            productsSum += Double.parseDouble(driver.findElement(By.xpath(productXpaths[i])).getText().substring(1));
        }
        Assertions.assertEquals(expectedTotalSum, productsSum, "Wrong total sum");
    }

    protected static void assertTotalPriceWithTax(String totalSumXpath, String taxXpath, double expectedTotalSum) {
        double expectedTax = expectedTotalSum * 0.08;
        double totalPrice = Double.parseDouble(
                driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"))
                        .getText().substring(13));
        double tax = Double.parseDouble(
                driver.findElement(By.xpath("//div[@class='summary_tax_label']")).getText().substring(6));
        Assertions.assertEquals(String.format("%.2f", expectedTotalSum + expectedTax),
                String.valueOf(totalPrice + tax), "Wrong total price plus tax");
    }

}
