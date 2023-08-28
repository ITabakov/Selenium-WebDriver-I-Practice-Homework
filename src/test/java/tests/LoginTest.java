package tests;

import basesetup.BaseTestSetup;
import enums.BrowserType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.*;

public class LoginTest extends BaseTestSetup {

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void userAuthenticated_when_validCredentialsProvided(BrowserType browserType){

        startWithBrowser(browserType);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get(BASE_URL);

        authenticateWithUser(USERNAME, PASSWORD);

        assertCurrentPageUrl(INVENTORY_PAGE_URL, driver.getCurrentUrl());
        assertPageTitle("//div[@class='app_logo']");

    }

}
