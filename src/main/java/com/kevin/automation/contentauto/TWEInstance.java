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

	Robot robot;
	TestFolders inputFolder;

	public TWEInstance(int port, String folderPath) throws AWTException {
		inputFolder = new TestFolders(folderPath);
		robot = new Robot();
		driver = new InternetExplorerDriver();
		driver.get("http://twecontentdev1:" + port + "/Twe/welcome.do");
	}


	public void logIn() {
		driver.findElement(By.name("userName")).sendKeys("Admin");
		driver.findElement(By.name("userPassword")).sendKeys("Admin123\n");
	}

	public void goToCalculation() throws InterruptedException {
		Thread.sleep(800);
		driver.navigate().to("http://twecontentdev1:8380/Twe/loadComputeTaxAction.do");
		calculationWindow = driver.getWindowHandle();
	}

	public void goToBatchCalculation() {
		driver.switchTo().frame("header");
		driver.findElement(By.xpath("//a[@id='TAX_CALC_BATCH_IMG']")).click();
		driver.findElement(By.xpath("//a[@id='TAX_CALC_BATCH_IMG']")).sendKeys("\n");
	}

	public void runInputTests() throws AWTException, InterruptedException {
		String inputFilePath;
		for (int fileIndex = 0; fileIndex < inputFolder.getNumInputItems(); fileIndex++) {
			inputFilePath = inputFolder.getInputFilePath(fileIndex);
			if (inputFolder.isCsvFile(inputFilePath)) {
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
				System.out.println("\nRunning test for " + inputFolder.getInputFileName(fileIndex) + "...");
				submitAndWait();
				Thread.sleep(200);
				keyPressA();
				copyOutputFilePathFrom(inputFolder.getInputFileName(fileIndex));
				Thread.sleep(700);
				paste();
				keyPressEnter();
				Thread.sleep(500);
				robot.keyPress(KeyEvent.VK_Y);
				robot.keyRelease(KeyEvent.VK_Y);
				Thread.sleep(900);
				System.out.println("Saved to: " + inputFolder.convertToOutputFilePath(inputFolder.getInputFileName(fileIndex)));

			} else {
				System.out.println(inputFolder.getInputFileName(fileIndex) + " is not an input csv file.");
			}
		}
		driver.quit();
	}

	private void keyPressA() {
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
	}

	private void copyOutputFilePathFrom(String inputFileName) {
		StringSelection copyOutput = new StringSelection(inputFolder.convertToOutputFilePath(inputFileName));
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
