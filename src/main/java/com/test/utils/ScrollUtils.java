// ScrollUtils.java
package com.test.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrollUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScrollUtils.class);
    private final WebDriver driver;
    private final JavascriptExecutor js;

    public ScrollUtils(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }

    public void scrollByViewport(int numberOfScrolls) {
        for (int i = 0; i < numberOfScrolls; i++) {
            // Get viewport height
            Long viewportHeight = (Long) js.executeScript(
                    "return window.innerHeight"
            );

            // Scroll by viewport height
            js.executeScript("window.scrollBy(0, arguments[0])", viewportHeight);

            // Verify scroll position
            Long newPosition = (Long) js.executeScript("return window.pageYOffset");
            logger.info("Scrolled to position: {} (Scroll #{}/{})", newPosition, i + 1, numberOfScrolls);

            // Small pause to allow content to load
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0)");
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
