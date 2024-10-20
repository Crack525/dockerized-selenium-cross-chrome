// TwitchPage.java
package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(TwitchPage.class);

    private static final String MOBILE_URL = "https://m.twitch.tv/";
    private static final By SEARCH_ICON = By.xpath("//a[@aria-label='Search']");
    private static final By SEARCH_INPUT = By.xpath("//input[@data-a-target='tw-input']");
    private static final By SEARCH_RESULT = By.xpath("//p[contains(text(), 'starcraft ii - stream')]");
    private static final By TOP_SECTION = By.xpath("//li[@role='presentation']//a[@data-index='0']//div[contains(@class, 'ScTitle-sc-iekec1-3') and text()='Top']");

    @FindBy(xpath = "//a[@aria-label='Search']")
    private WebElement searchIcon;

    @FindBy(xpath = "//input[@data-a-target='tw-input']")
    private WebElement searchInput;

    public TwitchPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToMobileTwitch() {
        driver.get(MOBILE_URL);
        popupHandler.acceptCookies();
    }

    public void clickSearchIcon() {
        waitUtils.waitForElementClickable(SEARCH_ICON).click();
        waitUtils.sleep(2);
    }

    public void searchForStarcraft() {
        int maxAttempts = 2;
        boolean searchSuccessful = false;

        for (int attempt = 0; attempt < maxAttempts && !searchSuccessful; attempt++) {
            try {
                WebElement searchBox = waitUtils.waitForElementVisible(SEARCH_INPUT);
                searchBox.clear();
                searchBox.sendKeys("StarCraft II");

                waitUtils.waitForElementVisible(SEARCH_RESULT);
                searchSuccessful = true;
                logger.info("Search successful on attempt {}", attempt + 1);
            } catch (Exception e) {
                logger.warn("Search attempt {} failed. {}", attempt + 1, e.getMessage());
                if (attempt < maxAttempts - 1) {
                    waitUtils.sleep(2);
                } else {
                    throw new RuntimeException("Failed to search after " + maxAttempts + " attempts", e);
                }
            }
        }
    }

    public void scrollPage(int times) {
        scrollUtils.scrollByViewport(times);
    }

    public void selectStarcraftStream() {
        waitUtils.waitForElementClickable(SEARCH_RESULT).click();
        waitUtils.sleep(2);
    }

    public void waitForStreamPageLoad() {
        waitUtils.waitForElementVisible(TOP_SECTION);
        waitUtils.sleep(5);
    }
}