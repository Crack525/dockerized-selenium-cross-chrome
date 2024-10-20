// TestRunner.java
package com.test.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.test.steps", "com.test.hooks"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/reports/cucumber-reports.html"
        },
        monochrome = true,
        dryRun = false,
        publish = true

)
public class TestRunner {
}
