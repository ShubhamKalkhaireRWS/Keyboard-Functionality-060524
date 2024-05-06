package RWS.PageObjects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LandingPage {
	WebDriver driver;
	String currentHandle;

	public LandingPage(WebDriver driver) {
		this.driver = driver;
	}

	public void openURL(String URL) throws InterruptedException {
		currentHandle = driver.getWindowHandle();
		driver.findElement(By.cssSelector("#txtUrls")).sendKeys(URL);
		driver.findElement(By.cssSelector("#openUrls")).click();
		WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(50));
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// Switch to the new tab
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(driver.getWindowHandle())) {
				driver.switchTo().window(windowHandle);
			}
		}

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
		// Close the previous tab

		Thread.sleep(2000);
		if (URL.contentEquals("https://www.heart.org/")) {
			if (driver.findElement(By.xpath("(//button[@class='close c-modal--promotion__close btn btn-dark'])[1]"))
					.isDisplayed()) {
				driver.findElement(By.xpath("(//button[@class='close c-modal--promotion__close btn btn-dark'])[1]"))
						.click();
				driver.findElement(By.cssSelector(
						".onetrust-close-btn-handler.onetrust-close-btn-ui.banner-close-button.ot-close-icon")).click();
				Thread.sleep(3000);
				Assert.assertTrue(driver.getCurrentUrl().equals(URL));
			}

		} else {
			driver.findElement(By
					.cssSelector(".onetrust-close-btn-handler.onetrust-close-btn-ui.banner-close-button.ot-close-icon"))
					.click();
			Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		}

	}

	public void goTo(String baseUrl) throws InterruptedException {
		driver.get(baseUrl);
		Thread.sleep(2000);
		if (baseUrl.contentEquals("https://www.heart.org/")) {
			if (driver.findElement(By.xpath("(//button[@class='close c-modal--promotion__close btn btn-dark'])[1]"))
					.isDisplayed()) {
				driver.findElement(By.xpath("(//button[@class='close c-modal--promotion__close btn btn-dark'])[1]"))
						.click();
				driver.findElement(By.cssSelector(
						".onetrust-close-btn-handler.onetrust-close-btn-ui.banner-close-button.ot-close-icon")).click();
				Thread.sleep(3000);
				Assert.assertTrue(driver.getCurrentUrl().equals(baseUrl));
			}
		} else {
			driver.findElement(By
					.cssSelector(".onetrust-close-btn-handler.onetrust-close-btn-ui.banner-close-button.ot-close-icon"))
					.click();
		}

		// Initialize Actions
	}

	public void getAllMissingAltAttributes(String filePath) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			// Find all img elements on the page
			List<WebElement> imgElements = driver.findElements(By.tagName("img"));

			// Iterate through img elements and capture alt attribute values
			for (WebElement imgElement : imgElements) {
				String altAttributeValue = imgElement.getAttribute("alt");
				if (altAttributeValue == null || altAttributeValue.trim().isEmpty()) {
					writer.println("Img Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", imgElement));
					writer.println("----------------------------------------------");
				}
			}

			// Find all map elements on the page
			List<WebElement> mapElements = driver.findElements(By.tagName("map"));

			// Iterate through map elements and capture alt attribute values
			for (WebElement mapElement : mapElements) {
				String altAttributeValue = mapElement.getAttribute("alt");
				if (altAttributeValue == null || altAttributeValue.trim().isEmpty()) {
					writer.println("Map Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", mapElement));
					writer.println("----------------------------------------------");
				}
			}

			// Find all logo elements on the page (you may need to adjust this based on the
			// actual tag name)
			List<WebElement> logoElements = driver.findElements(By.tagName("logo"));

			// Iterate through logo elements and capture alt attribute values
			for (WebElement logoElement : logoElements) {
				String altAttributeValue = logoElement.getAttribute("alt");
				if (altAttributeValue == null || altAttributeValue.trim().isEmpty()) {
					writer.println("Logo Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", logoElement));
					writer.println("----------------------------------------------");
				}
			}

			System.out.println("Elements with empty alt attribute values saved to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inputWithoutPlaceholder(String filePath) {
		// Find all input elements on the page
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			List<WebElement> inputElements = driver.findElements(By.tagName("input"));

			// Iterate through input elements
			for (WebElement inputElement : inputElements) {
				// Check if the input element is displayed and does not have a placeholder
				// attribute
				if (inputElement.isDisplayed() && inputElement.getAttribute("placeholder").isEmpty()) {
					// Print the entire input element without placeholder attribute

					writer.println("Iframe Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", inputElement));
					writer.println("----------------------------------------------");
				}

			}
			System.out.println("Input elements without title attribute values saved to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getFramesWithoutTitle(String filePath) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			// Find all iframe elements on the page
			List<WebElement> iframeElements = driver.findElements(By.tagName("iframe"));

			// Iterate through iframe elements and capture title attribute values
			for (WebElement iframeElement : iframeElements) {
				String titleAttributeValue = iframeElement.getAttribute("title");
				if (titleAttributeValue == null || titleAttributeValue.trim().isEmpty()) {
					writer.println("Iframe Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", iframeElement));
					writer.println("----------------------------------------------");
				}
			}

			System.out.println("Iframe elements without title attribute values saved to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getAllButtonsWithoutDescriptiveValue(String filePath) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			// Find all button elements on the page
			List<WebElement> buttonElements = driver.findElements(By.xpath("//button[@type='button']"));

			for (WebElement buttonElement : buttonElements) {

				// Retrieve text using JavaScript
				String buttonText = (String) ((JavascriptExecutor) driver)
						.executeScript("return arguments[0].textContent;", buttonElement);

				// Check if buttonText is not empty before printing
				if (buttonText.isEmpty()) {
					writer.println("Button Element: " + (String) ((JavascriptExecutor) driver)
							.executeScript("return arguments[0].outerHTML;", buttonElement));
					writer.println("----------------------------------------------");
				}
			}

			System.out.println("Visible text values for non-empty button elements saved to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkKeyboardFunctionality(String filePath) throws InterruptedException, IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(filePath));
		String baseURL = driver.getCurrentUrl();
		List<WebElement> hrefs = driver.findElements(By.tagName("a"));

		int min = 0;
		int max = hrefs.size(); // Change this value to your desired maximum limit

		// Create an instance of Random class
		Random random = new Random();

		// Generate a random number within the specified range
		int randomNumber = random.nextInt(max - min + 1) + min;
		// Initialize Actions class
		Actions actions = new Actions(driver);

		// Simulate pressing the tab button multiple times
		for (int i = 0; i < randomNumber; i++) {
			actions.sendKeys("\t").perform();

		}

		// Get the currently focused element
		WebElement focusedElement = driver.switchTo().activeElement();

		String linkOfFocusedElement = focusedElement.getAttribute("href");
		if (!(linkOfFocusedElement == null)) {
			if (!linkOfFocusedElement.isEmpty()) {
				Thread.sleep(2000);
				focusedElement.sendKeys(Keys.ENTER);
				WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

				if (driver.getCurrentUrl().equals(baseURL)) {

					Thread.sleep(3000);
					if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
						Assert.assertTrue(true);
					} else {
						wait.until(ExpectedConditions.numberOfWindowsToBe(3));
						// Switch to the new tab
						Set<String> windowHandles = driver.getWindowHandles();
						for (String windowHandle : windowHandles) {
							if (!windowHandle.equals(currentHandle)) {
								if (!windowHandle.equals(driver.getWindowHandle())) {
									driver.switchTo().window(windowHandle);
									break;
								}
							}
						}
					}
				}

				if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
					writer.println("Page Functionality Using Keyboard is Working Correctly");
					Assert.assertTrue(true);
				} else {
					writer.println("Page Functionality Using Keyboard is Working Correctly");
					Assert.assertEquals(driver.getCurrentUrl(), linkOfFocusedElement);

				}

			}
		}
		writer.close();
	}

	public void brokenLinks() throws MalformedURLException, IOException {

		List<WebElement> links = driver.findElements(By.tagName("a"));

		for (WebElement link : links) {
			String url = link.getAttribute("href");
			if (url != null && !url.isEmpty()) {
				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("HEAD");
				conn.connect();
				int respCode = conn.getResponseCode();
				if (respCode > 400) {
					System.out.println(
							(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML;", link)
									+ " Code " + respCode);
				}
			} else {
				System.out.println("Empty Links "
						+ (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", link));
			}

		}
	}

	public void reflow() throws InterruptedException {
		zoomPage(driver, 4.0);
		Thread.sleep(2000);
		// Check if the webpage is horizontally scrollable
		boolean isScrollable = isHorizontallyScrollable(driver);

		// Print the result
		if (isScrollable) {
			System.out.println("The webpage is horizontally scrollable.");
		} else {
			System.out.println("The webpage is not horizontally scrollable.");
		}
	}

	public boolean isHorizontallyScrollable(WebDriver driver) {
		// Execute JavaScript to determine if the document is horizontally scrollable
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean isScrollable = (boolean) js
				.executeScript("return document.body.scrollWidth > document.body.clientWidth");
		return isScrollable;
	}

	public void zoomPage(WebDriver driver, double zoomFactor) {
		// Execute JavaScript to set the browser's zoom level
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom = '" + zoomFactor + "'");
	}

	public void characterKey(String filePath) throws InterruptedException {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filePath));
			// Locate a text field on the page
			WebElement searchBtn = driver.findElement(By.xpath("//img[@alt='site search']"));
			WebElement textField = driver.findElement(By.xpath("//input[@placeholder='ex: Heart Attack Symptoms']"));
			// Create Actions class instance
			Actions actions = new Actions(driver);
			searchBtn.click();
			Thread.sleep(1000);
			textField.click();
			// Special characters with shifted keys
			String specialCharacters = "1234567890-=[];',./";

			// Type each special character
			for (char ch : specialCharacters.toCharArray()) {
				actions.keyDown(Keys.SHIFT).sendKeys(String.valueOf(ch)).keyUp(Keys.SHIFT);
				Thread.sleep(100);
			}

			// Perform the actions on the text field
			actions.build().perform();
			String actual = driver.findElement(By.xpath("//input[@placeholder='ex: Heart Attack Symptoms']"))
					.getAttribute("value");
			driver.findElement(By.xpath("(//button[@aria-label='Close'][normalize-space()='×'])[1]")).click();
			String expected = "!@#$%^&*()_+{}:\"<>?";
			Thread.sleep(5000);
			System.out.println(expected.equals(actual));
			if (expected.equals(actual)) {
				writer.println("Character Key Functionality Working Correctly");
				writer.close();
			}
			Assert.assertEquals(actual, expected);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCurrentMethodName() {
		// Get the stack trace
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

		// The current method name is at index 2 in the stack trace array
		String methodName = stackTraceElements[2].getMethodName();

		return methodName;
	}

	public static void mergeTextFiles(String folderPath, String outputFilePath) {
		try {
			File folder = new File(folderPath);
			File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

			if (files != null && files.length > 0) {
				FileWriter writer = new FileWriter(outputFilePath);

				for (File file : files) {
					Path path = Paths.get(file.getAbsolutePath());
					try (Stream<String> lines = Files.lines(path)) {
						lines.forEach(line -> {
							try {
								writer.write(line);
								writer.write(System.lineSeparator());
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
				}

				writer.close();
				System.out.println("Text files merged successfully.");
			} else {
				System.out.println("No text files found in the specified folder.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkKeyboardFunctionality1(String filePath) throws InterruptedException, IOException {
		try {
			Actions actions = new Actions(driver);

			// Move mouse to the right edge of the webpage
//		        actions.moveToElement(driver.findElement(By.cssSelector("input[title='Submit Search']"))).build().perform();
			PrintWriter writer = new PrintWriter(new FileWriter(filePath));
			String baseURL = driver.getCurrentUrl();
			List<WebElement> hrefs = driver.findElements(By.tagName("a"));

			int min = 1;
			int max = hrefs.size(); // Change this value to your desired maximum limit

			// Create an instance of Random class
			Random random = new Random();

			// Generate a random number within the specified range
			int randomNumber = random.nextInt(max - min + 1) + min;
			// Initialize Actions class
			List<String> autoCompleteElementsProcessed = new ArrayList<>();
			List<String> tabbedButton = new ArrayList<>();
			// Simulate pressing the tab button multiple times
			for (int i = 0; i < max; i++) {
				actions.sendKeys("\t").perform();
//				Thread.sleep(1000);
				WebElement focusedElement = driver.switchTo().activeElement();

				if (!(focusedElement.getAttribute("type") == null)) {
					if (focusedElement.getAttribute("type").equalsIgnoreCase("radio")) {
						focusedElement.sendKeys(Keys.DOWN);

					}

					if (focusedElement.getTagName().equalsIgnoreCase("Select")) {
						Thread.sleep(1000);
						Select sel = new Select(driver.findElement(By.name(focusedElement.getAttribute("name"))));
						int options = sel.getOptions().size() - 1;
						int randomOption = random.nextInt(options - min + 1) + min;

						for (int j = 0; j < randomOption; j++) {
							focusedElement.sendKeys(Keys.DOWN);
						}
						System.out.println(sel.getFirstSelectedOption().getText());
						System.out.println(sel.getOptions().get(randomOption).getText());
						Assert.assertEquals(sel.getFirstSelectedOption().getText(),
								sel.getOptions().get(randomOption).getText());

					}

					if (focusedElement.getAttribute("type").equalsIgnoreCase("checkbox")) {
						focusedElement.sendKeys(Keys.SPACE);

						Assert.assertTrue(focusedElement.isSelected());
					}

					if (focusedElement.getAttribute("class").contains("autocomplete")
							&& !autoCompleteElementsProcessed.contains(focusedElement.getAttribute("name"))) {
						focusedElement.sendKeys("in");
						Thread.sleep(3000);
						List<WebElement> autoSuggestOptions = driver
								.findElements(By.cssSelector(".ui-menu.ui-widget.ui-widget-content li"));
						int autoSuggestOptionsCount = autoSuggestOptions.size();

						int randomAutoSuggestOption = random.nextInt(autoSuggestOptionsCount - min + 1) + min;
						String expected = autoSuggestOptions.get(randomAutoSuggestOption - 1).getText();
						for (int k = 0; k < randomAutoSuggestOption; k++) {
							focusedElement.sendKeys(Keys.DOWN);
							Thread.sleep(200);

						}
						Thread.sleep(1000);
						String actual = focusedElement.getAttribute("value");

						System.out.println("Actual : " + focusedElement.getAttribute("value"));
						autoCompleteElementsProcessed.add(focusedElement.getAttribute("name"));
						focusedElement.sendKeys(Keys.ENTER);

						Thread.sleep(2000);
						System.out.println("Expected : " + expected);
						Assert.assertEquals(actual, expected);

					}

				}

				String linkOfFocusedElement = focusedElement.getAttribute("href");
				if (!tabbedButton.contains(linkOfFocusedElement)) {
					linkNavigation(focusedElement, baseURL, writer, linkOfFocusedElement);
					tabbedButton.add(linkOfFocusedElement);
				}

//				// Get the currently focused element
//
//				String linkOfFocusedElement = focusedElement.getAttribute("href");
//				if (!(linkOfFocusedElement == null)) {
//					if (!linkOfFocusedElement.isEmpty()) {
//						Thread.sleep(2000);
//						focusedElement.sendKeys(Keys.ENTER);
//						WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
//						wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
//
//						if (driver.getCurrentUrl().equals(baseURL)) {
//
//							Thread.sleep(3000);
//							if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
//								Assert.assertTrue(true);
//							} else {
//								wait.until(ExpectedConditions.numberOfWindowsToBe(3));
//								// Switch to the new tab
//								Set<String> windowHandles = driver.getWindowHandles();
//								for (String windowHandle : windowHandles) {
//									if (!windowHandle.equals(currentHandle)) {
//										if (!windowHandle.equals(driver.getWindowHandle())) {
//											driver.switchTo().window(windowHandle);
//											break;
//										}
//									}
//								}
//							}
//						}
//						
//
//						if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
//							writer.println("Page Functionality Using Keyboard is Working Correctly");
//							Assert.assertTrue(true);
//						} else {
//							writer.println("Page Functionality Using Keyboard is Working Correctly");
//							Assert.assertEquals(driver.getCurrentUrl(), linkOfFocusedElement);
//
//						}
//
//					}
//				}
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void linkNavigation(WebElement focusedElement, String baseURL, PrintWriter writer,
			String linkOfFocusedElement) throws InterruptedException {
		if (focusedElement.getTagName().equals("a")
				&& !focusedElement.getText().equalsIgnoreCase("Skip to main content")) {
			System.out.println(focusedElement.getText());
			
			focusedElement.sendKeys(Keys.ENTER);
			WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
			String currentURL = null;
			Thread.sleep(2000);

			if (!(driver.getCurrentUrl().equals(baseURL))
					|| driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
			
				if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
					driver.switchTo().frame("__checkout2");
					Thread.sleep(3000);
					driver.findElement(By.xpath("//button[@data-tracking-element-name='closeButton']")).click();
					driver.switchTo().defaultContent();
				
				}
				JavascriptExecutor js = (JavascriptExecutor) driver;
		        boolean isDocumentReady = (boolean) js.executeScript("return document.readyState === 'complete';");

				if(isDocumentReady) {
				currentURL = driver.getCurrentUrl();}
			
				driver.navigate().back();

			} else {
				if (driver.getCurrentUrl().equals(baseURL)) {

					Thread.sleep(3000);
					if (driver.getCurrentUrl().contains("https://www.heart.org/?form=")) {
						Assert.assertTrue(true);
					} else {
						wait.until(ExpectedConditions.numberOfWindowsToBe(3));
						// Switch to the new tab
						Set<String> windowHandles = driver.getWindowHandles();
						for (String windowHandle : windowHandles) {
							if (!windowHandle.equals(currentHandle)) {
								String currentHandle1 = driver.getWindowHandle();
								if (!windowHandle.equals(currentHandle1)) {
									driver.switchTo().window(windowHandle);
									currentURL = driver.getCurrentUrl();
									driver.close();
									driver.switchTo().window(currentHandle1);
									break;
								}
							}
						}
					}
				}
			}
	
			if (driver.getCurrentUrl().contains("https://www.heart.org/?form=") || linkOfFocusedElement.contains("https://mygiving.heart.org/")) {
				writer.println("Page Functionality Using Keyboard is Working Correctly");
				Assert.assertTrue(true);
			} else {
				writer.println("Page Functionality Using Keyboard is Working Correctly");
				System.out.println(currentURL);
				System.out.println(linkOfFocusedElement);
				Assert.assertEquals(currentURL, linkOfFocusedElement);

			}
		}

	}
}
