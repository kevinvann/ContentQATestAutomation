package com.kevin.automation.contentauto;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class FileShrinker {

	TestFolders outputFolder;
	Robot robot;
	String folderPath;

	public FileShrinker(String folderPath) throws AWTException {
		robot = new Robot();
		outputFolder = new TestFolders(folderPath);
		this.folderPath = folderPath;
	}

	public void startFileShrinker() {
		try {
			Runtime.getRuntime().exec(new String[] { folderPath + "\\Outputs\\File Shrinker V1.3.exe" });
		} catch (IOException e) {

		}
	}

	public void shrinkFiles() throws AWTException, InterruptedException {
		String outputFilePath;
		Thread.sleep(1000);
		keyPressTab();
		keyPressTab();

		for (int i = 0; i < outputFolder.getNumOutputItems(); i++) {
			outputFilePath = outputFolder.getOutputFilePath(i);
			if (outputFilePath.endsWith(".csv")
					&& !outputFilePath.endsWith("_Reduced.csv")) {
				keyPressEnter();
				copyOutputFilePath(outputFilePath);
				Thread.sleep(500);
				paste();
				Thread.sleep(500);
				keyPressEnter();
				keyPressTab();
				keyPressEnter();
				Thread.sleep(2000);
				keyPressEnter();
				int x = 0;
				while (x < 7) {
					keyPressTab();
					x++;
				}
			}
		}
		keyPressAltF4();
	}

	
	
	private void keyPressEnter() {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	private void keyPressTab() {
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}

	private void keyPressAltF4() {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
	}

	private void copyOutputFilePath(String outputFilePath) {
		StringSelection copyOutput = new StringSelection(outputFilePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyOutput, null);
	}

	private void paste() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

}
