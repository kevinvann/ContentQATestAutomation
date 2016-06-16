package com.kevin.automation.contentauto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

public class TWEInstance {

	private WebDriver driver;
	private String calculationWindow;
	int port;
	String server;
	UpdateFolder testTypeFolder;

	public TWEInstance(String folderPath, String server, int port) throws AWTException {
		new KeyCommands();
		this.port = port;
		this.server = server;
		testTypeFolder = new UpdateFolder(folderPath);
		driver = new InternetExplorerDriver();
		driver.get("http://" + server + ":" + port + "/Twe/welcome.do");
	}

	public void logIn() {
		driver.findElement(By.name("userName")).sendKeys("Admin");
		driver.findElement(By.name("userPassword")).sendKeys("Admin123\n");
	}

	public void goToCalculation() throws InterruptedException {
		Thread.sleep(1300);
		driver.navigate().to("http://" + server + ":" + port + "/Twe/loadComputeTaxAction.do");
		calculationWindow = driver.getWindowHandle();
	}

	public void goToBatchCalculation() {
		driver.switchTo().frame("header");
		driver.findElement(By.xpath("//a[@id='TAX_CALC_BATCH_IMG']")).click();
		driver.findElement(By.xpath("//a[@id='TAX_CALC_BATCH_IMG']")).sendKeys("\n");
	}

	public void runInputTests(int type) throws AWTException, InterruptedException {
		String inputFilePath;
		String inputFileName;
		for (int fileIndex = 0; fileIndex < testTypeFolder.getNumInputItems(); fileIndex++) {
			inputFilePath = testTypeFolder.getInputFilePath(fileIndex);
			inputFileName = testTypeFolder.getInputFileName(fileIndex);
			if (inputFilePath.endsWith(".csv") && !inputFilePath.endsWith("SSTSmokeTest.csv")) {
				driver.switchTo().window(calculationWindow);
				driver.switchTo().frame("body");
				driver.switchTo().frame("content");
				
				Thread.sleep(800);
				
				selectFileUpload();
				
				Thread.sleep(200);
				
				KeyCommands.copyToClipboard(inputFilePath);
				KeyCommands.ctrlV();
				KeyCommands.enter();
				
				Thread.sleep(800);
				
				System.out.println("\nRunning test for " + inputFileName + "...");
				submitAndWait();
				
				Thread.sleep(200);
				
				KeyCommands.a();
				KeyCommands.copyToClipboard(testTypeFolder.convertToOutputFilePath(inputFileName, type));
				
				Thread.sleep(700);
				
				KeyCommands.ctrlV();
				KeyCommands.enter();
				
				Thread.sleep(500);
				
				KeyCommands.robot.keyPress(KeyEvent.VK_Y);
				KeyCommands.robot.keyRelease(KeyEvent.VK_Y);
				
				Thread.sleep(900);
				
				System.out.println("Saved to: "
						+ testTypeFolder.convertToOutputFilePath(inputFileName, type));

			} else {
				System.out.println(inputFileName + " is not an input csv file.");
			}
		}
		driver.quit();
	}
	
	
	
	
	
	


	private void submitAndWait() throws InterruptedException {
		driver.findElement(By.xpath("//input[@class='buttonred']")).click();
		Thread.sleep(500);
		while (driver.getPageSource().length() > 0) {
			Thread.sleep(1000);
		}
	}

	private void selectFileUpload() {
		WebElement csvField = driver.findElement(By.name("csvFile"));
		Actions action = new Actions(driver);
		action.moveToElement(csvField).doubleClick().build().perform();
	}

}
