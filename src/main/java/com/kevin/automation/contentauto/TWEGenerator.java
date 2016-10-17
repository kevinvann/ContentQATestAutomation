// This class deals with controlling the TWE Generator V.4.exe application to create criterion tests

package com.kevin.automation.contentauto;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TWEGenerator {
	private String folderPath;
	File tweFolder;

	public TWEGenerator(String folderPath) throws AWTException {
		new KeyCommands();
		this.folderPath = folderPath;
		tweFolder = new File(folderPath);
	}

	public void openGenerator() {
		try {
			Runtime.getRuntime().exec(new String[] { folderPath + "\\TWE Generator V.4.exe" });
		} catch (IOException e) {

		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void createCriterionTests(String date, String version, String criterionInPath) throws IOException {

		KeyCommands.ctrlTab();
		KeyCommands.tab(4);
		KeyCommands.space();
		KeyCommands.tab(4);
		KeyCommands.space();
		KeyCommands.tab(1);
		KeyCommands.space();
		KeyCommands.tab(12);
		KeyCommands.copyToClipboard("10000.00");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		KeyCommands.ctrlV();
		KeyCommands.tab(13);
		KeyCommands.enter();
		KeyCommands.copyToClipboard(date.toUpperCase() + "_TWE" + version + "_Criterion.in.csv");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		KeyCommands.ctrlV();
		KeyCommands.enter();
		KeyCommands.tab(1);
		KeyCommands.enter();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	// Method to move criterion tests that were made from the Generator folder to the update folder
	public void moveAndRename(String date, String version, String criterionInPath) throws IOException {
		int x = 0;
		for (int i = 0; i < tweFolder.listFiles().length ; i++) {
			System.out.println(tweFolder.list()[i].toString());
			if (tweFolder.list()[i].toString().startsWith(date) && x < 10) {
				System.out.println(x);
				Files.copy(
						Paths.get(tweFolder.listFiles()[i].toString()), Paths.get(criterionInPath + "\\" + date.toUpperCase()
								+ "_TWE" + version + "_Criterion_" + x + ".in.csv"),
						StandardCopyOption.REPLACE_EXISTING);
				x++;
			}
		}
	}

	
	
	
	
}
