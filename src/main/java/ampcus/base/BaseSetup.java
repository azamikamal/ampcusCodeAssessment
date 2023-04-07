package ampcus.base;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ReadYamlFiles;

public class BaseSetup {

	private static WebDriver webDriver;
	private final ReadYamlFiles environmentVariables;

	public BaseSetup() {
		String filePath = System.getProperty("user.dir") + "/src/main/resources/env_config.yml";
		try {
			environmentVariables = ReadYamlFiles.getInstance(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Failed for Load environment context. check possible file path errors");
			e.printStackTrace();
			throw new RuntimeException("Failed for Load environment context with message " + e.getMessage());

		}
	}

	public WebDriver getDriver() {
		return webDriver;
	}

	public void setupBrowser() {
		HashMap uiProperties = environmentVariables.getYamlProperty("ui");
		String url = uiProperties.get("url").toString();
		switch (uiProperties.get("browser").toString().toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver ();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			webDriver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Unknown Browser check environment properties");
		}
		webDriver.get(url);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(20, ChronoUnit.SECONDS));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

	}
	
	public void quitBrowser() {
			webDriver.close();
	}

}
