package utils;

public class Constants {

    public static final String USERNAME = "standard_user";
    public static final String PASSWORD = "secret_sauce";
    public static final String BASE_URL = "https://www.saucedemo.com/";
    public static final String INVENTORY_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    public static final String SHOPPING_CART_PAGE_URL = "https://www.saucedemo.com/cart.html";
    public static final String USER_DETAILS_PAGE_URL = "https://www.saucedemo.com/checkout-step-one.html";
    public static final String OVERVIEW_PAGE_URL = "https://www.saucedemo.com/checkout-step-two.html";
    public static final String BURGER_MENU_BTN = "//button[@id='react-burger-menu-btn']";
    public static final String RESET_SIDEBAR_LINK = "//a[@id='reset_sidebar_link']";
    public static final String PAGE_ERROR_MESSAGE = "Wrong page navigated";
    public static final String INPUT_USERNAME_XPATH = "//input[@data-test='username']";
    public static final String INPUT_PASSWORD_XPATH = "//input[@data-test='password']";
    public static final String LOGIN_BUTTON_XPATH = "//input[@data-test='login-button']";
    public static final String ADD_TO_CART_SAUCE_LABS_BACKPACK_XPATH = "//button[@data-test='add-to-cart-sauce-labs-backpack']";
    public static final String ADD_TO_CART_SAUCE_LABS_BOLT_T_SHIRT_XPATH = "//button[@data-test='add-to-cart-sauce-labs-bolt-t-shirt']";
    public static final String INVENTORY_ITEM_NAME_CLASS = "inventory_item_name";
    public static final String PRODUCT_1_TITLE_XPATH = "(//div[@class='inventory_item_name'])[1]";
    public static final String PRODUCT_2_TITLE_XPATH = "(//div[@class='inventory_item_name'])[2]";
    public static final String PRODUCT_1_PRICE_XPATH = "(//div[@class='inventory_item_price'])[1]";
    public static final String PRODUCT_2_PRICE_XPATH = "(//div[@class='inventory_item_price'])[2]";
    public static final String SHOPPING_CART_LINK_XPATH = "//a[@class='shopping_cart_link']";
    public static final String CHECKOUT_BUTTON_XPATH = "//button[@data-test='checkout']";
    public static final String INPUT_FIRST_NAME_XPATH = "//input[@data-test='firstName']";
    public static final String INPUT_LAST_NAME_XPATH = "//input[@data-test='lastName']";
    public static final String INPUT_POSTAL_CODE_XPATH = "//input[@data-test='postalCode']";
    public static final String FIRST_NAME = "Ivailo";
    public static final String LAST_NAME = "Tabakov";
    public static final String POSTAL_CODE = "1233";
    public static final String CONTINUE_BUTTON_XPATH = "//input[@data-test='continue']";
    public static final String SAUCE_LABS_BACKPACK = "Sauce Labs Backpack";
    public static final String SAUCE_LABS_BOLT_T_SHIRT = "Sauce Labs Bolt T-Shirt";
    public static final String ITEM_1_NAME_XPATH = "//a[@id='item_4_title_link']/div[@class='inventory_item_name']";
    public static final String ITEM_2_NAME_XPATH = "//a[@id='item_1_title_link']/div[@class='inventory_item_name']";
    public static final String PRODUCT_1_OVERVIEW_PRICE_XPATH = "//*[@id='checkout_summary_container']/div/div[1]/div[3]/div[2]/div[2]/div";
    public static final String PRODUCT_2_OVERVIEW_PRICE_XPATH = "//*[@id='checkout_summary_container']/div/div[1]/div[4]/div[2]/div[2]/div";
    public static final double PRODUCT_1_EXPECTED_PRICE = 29.99;
    public static final double PRODUCT_2_EXPECTED_PRICE = 15.99;
    public static final double PRODUCTS_EXPECTED_TOTAL_PRICE_WITHOUT_TAX = 45.98;
    public static final String TOTAL_SUM_XPATH = "//div[@class='summary_subtotal_label']";
    public static final String TAX_XPATH = "//div[@class='summary_tax_label']";
    public static final String FINISH_BUTTON_XPATH = "//button[@data-test='finish']";
    public static final String CHECKOUT_COMPLETE_URL = "https://www.saucedemo.com/checkout-complete.html";
    public static final String COMPLETE_HEADER_XPATH = "//h2[@class='complete-header']";
    public static final String SUMMARY_SUBTOTAL_LABEL_XPATH = "//div[@class='summary_subtotal_label']";
    public static final String TAX_LABEL_XPATH = "//div[@class='summary_tax_label']";
    public static final String EXPECTED_TOTAL_TEXT = "Total: $49.66";
    public static final String TOTAL_TEXT_XPATH = "//div[@id='checkout_summary_container']/div/div[2]/div[8]";

}
