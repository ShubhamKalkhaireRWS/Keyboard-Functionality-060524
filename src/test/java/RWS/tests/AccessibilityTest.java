package RWS.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import RWS.PageObjects.LandingPage;
import RWS.TestComponents.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AccessibilityTest extends BaseTest {
	LandingPage landingPage;
//	@Test(dataProvider = "getData")
//	public  void Perceivable(String url,String linkName) throws IOException, InterruptedException {
//		 landingPage=openLink();
//		landingPage.openURL(url);
//		landingPage.getAllMissingAltAttributes(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"getAllMissingAltAttributes.txt");
//		landingPage.inputWithoutPlaceholder(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"inputWithoutPlaceholder.txt");
//		landingPage.getFramesWithoutTitle(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"getFramesWithoutTitle.txt");
//		landingPage.getAllButtonsWithoutDescriptiveValue(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"getAllButtonsWithoutDescriptiveValue.txt");
//		
//		LandingPage.mergeTextFiles(".\\Reports\\"+LandingPage.getCurrentMethodName(), ".\\Reports\\"+LandingPage.getCurrentMethodName()+".txt");
//
//		
//		
//	}
	
	@Test(dataProvider = "getData")
	public  void Operable(String url,String linkName) throws IOException, InterruptedException {
		 landingPage=openLink();
		landingPage.openURL(url);
	
//		landingPage.characterKey(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"CharacterKeyFunctionality.txt");
//		landingPage.checkKeyboardFunctionality(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"KeyboardFunctionality.txt");
//		LandingPage.mergeTextFiles(".\\Reports\\"+LandingPage.getCurrentMethodName(), ".\\Reports\\"+LandingPage.getCurrentMethodName()+".txt");
		landingPage.checkKeyboardFunctionality1(".\\Reports\\"+LandingPage.getCurrentMethodName()+"\\"+linkName+"KeyboardFunctionality.txt");
		
	}
	
	
	
	
//	@DataProvider
//	private Object[][] getData() {
//		
//		return new Object[][] {{"https://www.heart.org/","HomePage"},{"https://www.heart.org/en/about-us/heart-attack-and-stroke-symptoms","heart-attack-and-stroke-symptoms"}};
//
//	}


	@DataProvider
	private Object[][] getData() {
		
		return new Object[][] {{"https://cpr.heart.org/en/course-catalog-search","HomePage"}};

	}

}
