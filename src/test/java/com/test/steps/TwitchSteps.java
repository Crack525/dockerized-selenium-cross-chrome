// TwitchSteps.java
package com.test.steps;

import com.test.hooks.TestHooks;
import com.test.pages.TwitchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchSteps {
    private static final Logger logger = LoggerFactory.getLogger(TwitchSteps.class);
    private final TwitchPage twitchPage;

    public TwitchSteps() {
        this.twitchPage = new TwitchPage(TestHooks.getDriver());
    }

    @Given("the user is on the Twitch mobile website")
    public void theUserIsOnTheTwitchMobileWebsite() {
        logger.info("Navigating to Twitch mobile website");
        twitchPage.navigateToMobileTwitch();
    }

    @Then("the user click on search icon")
    public void theUserClickOnSearchIcon() {
        logger.info("Clicking search icon");
        twitchPage.clickSearchIcon();
    }

    @Then("the user enters StarCraft II")
    public void theUserEntersStarCraftII() {
        logger.info("Entering search term: StarCraft II");
        twitchPage.searchForStarcraft();
    }

    @Then("the user scrolls {int} times")
    public void theUserScrollsTimes(int scrollCount) {
        logger.info("Scrolling {} times", scrollCount);
        twitchPage.scrollPage(scrollCount);
    }

    @Then("the user selects starcraft ii - stream")
    public void theUserSelectsStarcraftIIStream() {
        logger.info("Selecting StarCraft II stream");
        twitchPage.selectStarcraftStream();
    }

    @Then("on the streamer page wait until all is load")
    public void onTheStreamerPageWaitUntilAllIsLoad() {
        logger.info("Waiting for stream page to load");
        twitchPage.waitForStreamPageLoad();
    }
}