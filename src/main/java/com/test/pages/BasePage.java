// BasePage.java
package com.test.pages;

import com.test.utils.PopupHandler;
import com.test.utils.ScrollUtils;
import com.test.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waitUtils;
    protected final ScrollUtils scrollUtils;
    protected final PopupHandler popupHandler;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.scrollUtils = new ScrollUtils(driver);
        this.popupHandler = new PopupHandler(driver);
        PageFactory.initElements(driver, this);
    }
}
