package tests;

import basesetup.BaseTestSetup;
import enums.BrowserType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ProductTests extends BaseTestSetup {

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void productAddedToShoppingCart_when_addToCart(BrowserType browserType) {

        addTwoProductsToShoppingCart(browserType);

    }

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void userDetailsAdded_when_checkoutWithValidInformation(BrowserType browserType) {

        addTwoProductsToShoppingCart(browserType);

        checkout();

    }

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(BrowserType browserType) {

        addTwoProductsToShoppingCart(browserType);

        checkout();

        finishOrder();

        assertFinishedOrder();

    }

}
