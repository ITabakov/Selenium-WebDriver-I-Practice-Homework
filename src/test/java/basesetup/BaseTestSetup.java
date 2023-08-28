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
import java.util.ArrayList;

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
        driver.findElement(By.xpath(BURGER_MENU_BTN)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RESET_SIDEBAR_LINK))).click();
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
                driver.findElement(By.xpath(INPUT_USERNAME_XPATH))));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.xpath(INPUT_PASSWORD_XPATH));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        loginButton.click();

    }

    protected void assertPageTitle(String pageTitleXpath) {
        String pageTitle = driver.findElement(By.xpath(pageTitleXpath)).getText();
        Assertions.assertEquals("Swag Labs", pageTitle, "Wrong page title");
    }

    protected static void addBackpackAndTshirtToShoppingCart() {

        // Add Backpack and T -shirt to shopping cart
        WebElement backpackAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BACKPACK_XPATH));
        backpackAddToCardButton.click();
        WebElement tShirtAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BOLT_T_SHIRT_XPATH));
        tShirtAddToCardButton.click();

    }

    protected void assertItemsAndPrices() {
        // Assert Items
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className(INVENTORY_ITEM_NAME_CLASS)));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        assertProductTitle(PRODUCT_1_TITLE_XPATH, "Sauce Labs Backpack");
        assertProductTitle(PRODUCT_2_TITLE_XPATH, "Sauce Labs Bolt T-Shirt");
        // Assert Products Prices
        assertProductPrice(PRODUCT_1_PRICE_XPATH, 29.99);
        assertProductPrice(PRODUCT_2_PRICE_XPATH, 15.99);
    }

    protected void goToShoppingCart() {
        // Click on Shopping Cart
        WebElement shoppingCartIcon = driver.findElement(By.xpath(SHOPPING_CART_LINK_XPATH));
        shoppingCartIcon.click();
    }

    protected void checkout() {
        // Click on Checkout
        WebElement checkoutButton = driver.findElement(By.xpath(CHECKOUT_BUTTON_XPATH));
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
        WebElement firstNameInput = driver.findElement(By.xpath(INPUT_FIRST_NAME_XPATH));
        firstNameInput.sendKeys(FIRST_NAME);
        WebElement lastNameInput = driver.findElement(By.xpath(INPUT_LAST_NAME_XPATH));
        lastNameInput.sendKeys(LAST_NAME);
        WebElement postalCodeInput = driver.findElement(By.xpath(INPUT_POSTAL_CODE_XPATH));
        postalCodeInput.sendKeys(POSTAL_CODE);
    }

    protected void goToOverviewPage() {
        // Click on Continue
        WebElement continueButton = driver.findElement(By.xpath(CONTINUE_BUTTON_XPATH));
        continueButton.click();
    }

    protected void assertOverviewPage() {
        //Assert Items
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className(INVENTORY_ITEM_NAME_CLASS)));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertEquals(SAUCE_LABS_BACKPACK,
                driver.findElement(By.xpath(ITEM_1_NAME_XPATH)).getText(),
                "Wrong product title");
        Assertions.assertEquals(SAUCE_LABS_BOLT_T_SHIRT,
                driver.findElement(By.xpath(ITEM_2_NAME_XPATH)).getText(),
                "Wrong product title");
        // Assert Products prices
        assertProductPrice(PRODUCT_1_OVERVIEW_PRICE_XPATH, PRODUCT_1_EXPECTED_PRICE);
        assertProductPrice(PRODUCT_2_OVERVIEW_PRICE_XPATH, PRODUCT_2_EXPECTED_PRICE);
        // Assert Total Price and Tax
        String[] productPricesXpaths = {PRODUCT_1_OVERVIEW_PRICE_XPATH, PRODUCT_2_OVERVIEW_PRICE_XPATH};
        assertTotalprice(PRODUCTS_EXPECTED_TOTAL_PRICE_WITHOUT_TAX, productPricesXpaths);
        assertTotalPriceWithTax(TOTAL_SUM_XPATH, TAX_XPATH, PRODUCTS_EXPECTED_TOTAL_PRICE_WITHOUT_TAX);
    }

    protected void finishOrder() {
        WebElement finishButton = driver.findElement(By.xpath(FINISH_BUTTON_XPATH));
        finishButton.click();
    }

    protected void assertFinishedOrder() {
        // Assert Checkout complete page
        assertCurrentPageUrl(CHECKOUT_COMPLETE_URL, driver.getCurrentUrl());
        // Assert No Items
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className(INVENTORY_ITEM_NAME_CLASS)));
        Assertions.assertEquals(0, items.size(), "Items count not as expected");
        // Assert Complete Header Message
        Assertions.assertEquals("Thank you for your order!",
                driver.findElement(By.xpath(COMPLETE_HEADER_XPATH)).getText(),
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
                driver.findElement(By.xpath(SUMMARY_SUBTOTAL_LABEL_XPATH))
                        .getText().substring(13));
        double tax = Double.parseDouble(
                driver.findElement(By.xpath(TAX_LABEL_XPATH)).getText().substring(6));
        Assertions.assertEquals(String.format("%.2f", expectedTotalSum + expectedTax),
                String.valueOf(totalPrice + tax), "Wrong total price plus tax");
        Assertions.assertEquals(EXPECTED_TOTAL_TEXT,
                driver.findElement(By.xpath(TOTAL_TEXT_XPATH)).getText(),
                "Wrong Total");
    }

}
