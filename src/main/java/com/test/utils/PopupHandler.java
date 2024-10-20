// PopupHandler.java
package com.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;


public class PopupHandler {
    private static final Logger logger = LoggerFactory.getLogger(PopupHandler.class);
    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WaitUtils waitUtils;

    public PopupHandler(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void acceptCookies() {
        waitUtils.waitForPageLoad();
        waitUtils.sleep(2);

        try {
            String result = (String) js.executeScript(
                    "// Function to create and set cookies\n" +
                            "function setCookie(name, value, days) {\n" +
                            "    const date = new Date();\n" +
                            "    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));\n" +
                            "    document.cookie = name + '=' + value + '; expires=' + date.toUTCString() + '; path=/';\n" +
                            "}\n" +
                            "\n" +
                            "// Set common consent cookies\n" +
                            "setCookie('consent-banner', 'true', 365);\n" +
                            "setCookie('twitch.gdpr.consent', 'true', 365);\n" +
                            "\n" +
                            "// Try multiple methods to remove the banner\n" +
                            "try {\n" +
                            "    // Method 1: Find and trigger the original click handler\n" +
                            "    const acceptButton = document.querySelector('button[data-a-target=\"consent-banner-accept\"]');\n" +
                            "    if (acceptButton) {\n" +
                            "        const handlers = getEventListeners(acceptButton);\n" +
                            "        if (handlers && handlers.click) {\n" +
                            "            handlers.click.forEach(handler => handler.listener.call(acceptButton));\n" +
                            "        }\n" +
                            "    }\n" +
                            "\n" +
                            "    // Method 2: Direct banner removal\n" +
                            "    const banner = document.querySelector('.consent-banner');\n" +
                            "    if (banner) {\n" +
                            "        banner.style.display = 'none';\n" +
                            "        banner.remove();\n" +
                            "    }\n" +
                            "\n" +
                            "    // Method 3: Find parent containers and remove\n" +
                            "    const containers = document.querySelectorAll('[class*=\"consent\"], [class*=\"cookie\"]');\n" +
                            "    containers.forEach(container => {\n" +
                            "        if (container.innerHTML.toLowerCase().includes('cookie') || \n" +
                            "            container.innerHTML.toLowerCase().includes('consent')) {\n" +
                            "            container.remove();\n" +
                            "        }\n" +
                            "    });\n" +
                            "\n" +
                            "    // Method 4: Modify local storage\n" +
                            "    localStorage.setItem('consent-banner', 'true');\n" +
                            "    localStorage.setItem('twitch.gdpr.consent', 'true');\n" +
                            "\n" +
                            "    return 'Attempted all banner removal methods';\n" +
                            "} catch (e) {\n" +
                            "    return 'Error: ' + e.message;\n" +
                            "}"
            );

            logger.info("Direct manipulation result: {}", result);


            // Verify banner removal and attempt final cleanup if needed
            verifyAndCleanup();

        } catch (Exception e) {
            logger.error("Error in cookie consent handling: {}", e.getMessage());
            attemptReactBypass();
        }
    }

    private void verifyAndCleanup() {
        String verificationResult = (String) js.executeScript(
                "const selectors = ['.consent-banner', '[class*=\"consent-banner\"]', " +
                        "'[class*=\"cookie-banner\"]', '[class*=\"gdpr\"]'];\n" +
                        "\n" +
                        "for (const selector of selectors) {\n" +
                        "    const elements = document.querySelectorAll(selector);\n" +
                        "    if (elements.length > 0) {\n" +
                        "        elements.forEach(el => {\n" +
                        "            el.style.display = 'none';\n" +
                        "            el.remove();\n" +
                        "        });\n" +
                        "    }\n" +
                        "}\n" +
                        "\n" +
                        "return 'Cleanup completed';"
        );
        logger.info("Verification and cleanup result: {}", verificationResult);
    }

    private void attemptReactBypass() {
        String bypassResult = (String) js.executeScript(
                "const root = document.querySelector('[class*=\"Layout-sc\"]')?.__react_root;\n" +
                        "if (!root) return 'No React root found';\n" +
                        "\n" +
                        "const button = document.querySelector('button[data-a-target=\"consent-banner-accept\"]');\n" +
                        "if (!button) return 'No button found';\n" +
                        "\n" +
                        "const event = new Event('click', { bubbles: true, cancelable: true });\n" +
                        "Object.defineProperty(event, '_reactName', { value: 'onClick' });\n" +
                        "button.dispatchEvent(event);\n" +
                        "\n" +
                        "return 'Attempted React event bypass';"
        );
        logger.info("React bypass result: {}", bypassResult);
    }
}