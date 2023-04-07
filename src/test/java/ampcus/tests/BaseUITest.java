package ampcus.tests;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.CommonUtility;

public class BaseUITest extends CommonUtility {

	@Before
	public void setupTests() {
		super.setupBrowser();
	}

	@After
	public void closeTests(Scenario scenario) {
		
			if (scenario.isFailed()) {
				byte[] screenshot = takeScreenShotAsBytes();
				scenario.attach(screenshot, "failed/png", scenario.getName() + " scenario Failed");
			}

			super.quitBrowser();

}}
