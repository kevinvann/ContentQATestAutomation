package com.kevin.automation.contentauto;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TWEGenerator {
	private String folderPath;
	private Robot robot;

	public TWEGenerator(String folderPath) throws AWTException {
		this.folderPath = folderPath;
		robot = new Robot();
	}

	public void openGenerator() {
		try {
			Runtime.getRuntime().exec(new String[] { folderPath + "\\TWE Generator V.4.exe" });
		} catch (IOException e) {

		}
	}

	public void createCriterionTests(String date, String version) {

		ctrlTab();

		keyPressTab(4);
		keyPressSpace();
		keyPressTab(4);
		keyPressSpace();
		keyPressTab(1);
		keyPressSpace();
		keyPressTab(12);
		copyString("1000.00");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		paste();
		keyPressTab(13);
		keyPressEnter();
		copyString(date.toUpperCase() + "_TWE" + version + "_Criterion.in.csv");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		paste();
		keyPressEnter();
		keyPressTab(1);
		keyPressEnter();
		

	}

	private void keyPressEnter() {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	private void keyPressTab(int x) {
		for (int i = 0; i < x; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}
	}

	private void keyPressSpace() {
		robot.keyPress(KeyEvent.VK_SPACE);
		robot.keyRelease(KeyEvent.VK_SPACE);
	}

	private void paste() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	private void ctrlTab() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	private void copyString(String string) {
		StringSelection copyOutput = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copyOutput, null);
	}

}
