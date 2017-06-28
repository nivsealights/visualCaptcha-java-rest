package com.kuhniverse.selenium;

import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;
import org.junit.Before;
import org.junit.Test;

@Config(
        browser = Browser.CHROME,
        url = "http://localhost:8080/"
)
public class SealightsUiTest extends Locomotive {

    @Before
    public void setup() {
        this.driver.manage().window().maximize();
    }

    @Test
    public void testGoToSettingsPage() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.login("admin@sealights.io", "unreal");

        UserMenu userMenu = new UserMenu();
        userMenu.toggle();
        userMenu.clickOnSettingsMenuItem();
    }

    @Test
    public void testExpandFirstItemInDashboard() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.login("admin@sealights.io", "unreal");

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.expandFirstBuild();
    }


    private class LoginPage {
        public static final String TXT_USERNAME_SELECTOR = "#username";
        public static final String TXT_PASSWORD_SELECTOR = "#password";
        public static final String BTN_LOGIN_SELECTOR = ".s-login-button";


        public void login(String username, String password) {
            setText(TXT_USERNAME_SELECTOR, username).
                    setText(TXT_PASSWORD_SELECTOR, password).
                    click(BTN_LOGIN_SELECTOR);
        }
    }

    private class DashboardPage{
        public static final String COLLAPSED_BUILD_SELECTOR = "[data-aid='CollapsedBuild']";

        public void expandFirstBuild(){
            click(COLLAPSED_BUILD_SELECTOR);
        }
    }

    private class UserMenu{
        public static final String SETTINGS_LINK_SELECTOR = "ul.dropdown-menu li:nth-child(2) a";
        public static final String MENU_TOGGLE_SELECTOR = "ul.nav .dropdown-toggle";

        public void toggle(){
            click(MENU_TOGGLE_SELECTOR);
        }

        public void clickOnSettingsMenuItem(){
            click(SETTINGS_LINK_SELECTOR);
        }
    }
}