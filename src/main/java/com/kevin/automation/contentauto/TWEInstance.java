package com.kevin.automation.contentauto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class TWEInstance {

	private WebDriver driver;
	private String calculationWindow;
	int port;
	String server;
	Robot robot;
	TestFolders testFolder;

	public TWEInstance(String folderPath, String server, int port) throws AWTException {
		this.port = port;
		this.server = server;
		testFolder = new TestFolders(folderPath);
		robot = new Robot();
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
		for (int fileIndex = 0; fileIndex < testFolder.getNumInputItems(); fileIndex++) {
			inputFilePath = testFolder.getInputFilePath(fileIndex);
			if (inputFilePath.endsWith(".csv")) {
				driver.switchTo().window(calculationWindow);
				driver.switchTo().frame("body");
				driver.switchTo().frame("content");
				Thread.sleep(800);
				selectFileUpload();
				Thread.sleep(200);
				copyInputFilePath(inputFilePath);
				paste();
				keyPressEnter();
				Thread.sleep(800);
				System.out.println("\nRunning test for " + testFolder.getInputFileName(fileIndex) + "...");
				submitAndWait();
				Thread.sleep(200);
				keyPressA();
				copyOutputFilePathFrom(testFolder.getInputFileName(fileIndex), type);
				Thread.sleep(700);
				paste();
				keyPressEnter();
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_Y);
				robot.keyRelease(KeyEvent.VK_Y);
				Thread.sleep(900);
				System.out.println("Saved to: " + testFolder.convertToOutputFilePath(testFolder.getInputFileName(fileIndex), type));

			} else {
				System.out.println(testFolder.getInputFileName(fileIndex) + " is not an input csv file.");
			}
		}
		driver.quit();
	}
	
	public void runRegressionTests(int type) throws InterruptedException {
		String inputFilePath;
		for (int fileIndex = 0; fileIndex < testFolder.getNumInputItems(); fileIndex++) {
			inputFilePath = testFolder.getInputFilePath(fileIndex);
			if (inputFilePath.endsWith(".in.csv")) {
				driver.switchTo().window(calculationWindow);
				driver.switchTo().frame("body");
				driver.switchTo().frame("content");
				Thread.sleep(800);
				selectFileUpload();
				Thread.sleep(200);
				copyInputFilePath(inputFilePath);
				paste();
				keyPressEnter();
				Thread.sleep(800);
				System.out.println("\nRunning test for " + testFolder.getInputFileName(fileIndex) + "...");
				submitAndWait();
				Thread.sleep(200);
				keyPressA();
				copyOutputFilePathFrom(testFolder.getInputFileName(fileIndex), type);
				Thread.sleep(700);
				paste();
				keyPressEnter();
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_Y);
				robot.keyRelease(KeyEvent.VK_Y);
				Thread.sleep(900);
				System.out.println("Saved to: " + testFolder.convertToOutputFilePath(testFolder.getInputFileName(fileIndex), type));

			} else {
				System.out.println(testFolder.getInputFileName(fileIndex) + " is not an input csv file.");
			}
		}
		driver.quit();
	}
	
	

	private void keyPressA() {
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
	}

	private void copyOutputFilePathFrom(String inputFileName, int type) {
		StringSelection copyOutput = new StringSelection(testFolder.convertToOutputFilePath(inputFileName, type));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyOutput, null);
	}

	private String copyInputFilePath(String inputFile) {
		String inputPathName = inputFile;
		StringSelection copyInput = new StringSelection(inputPathName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyInput, null);
		return inputPathName;
	}

	private void submitAndWait() throws InterruptedException {
		driver.findElement(By.xpath("//input[@class='buttonred']")).click();
		Thread.sleep(500);
		while (driver.getPageSource().length() > 0) {
			Thread.sleep(1000);
		}
	}

	private void keyPressEnter() {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	private void paste() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	private void selectFileUpload() {
		WebElement csvField = driver.findElement(By.name("csvFile"));
		Actions action = new Actions(driver);
		action.moveToElement(csvField).doubleClick().build().perform();
	}

}
