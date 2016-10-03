package com.kevin.automation.contentauto;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

public class FileShrinker {

	UpdateFolder outputFolder;
	String folderPath;

	public FileShrinker(String folderPath) throws AWTException {
		new KeyCommands();
		outputFolder = new UpdateFolder(folderPath);
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
		KeyCommands.tab(2);

		for (int i = 0; i < outputFolder.getNumOutputItems(); i++) {
			outputFilePath = outputFolder.getOutputFilePath(i);
			if (outputFilePath.endsWith(".csv") && !outputFilePath.endsWith("_Reduced.csv")) {

				

				if (new File(outputFolder.convertToReducedFilePath(outputFolder.getOutputFileName(i)).replaceFirst(".csv", "_Reduced.csv")).exists()) {
					System.out.println(outputFolder.convertToReducedFilePath(outputFolder.getOutputFileName(i))
							+ " already exists");
				} else {
					KeyCommands.enter();
					KeyCommands.copyToClipboard(outputFilePath);

					Thread.sleep(500);

					KeyCommands.ctrlV();

					Thread.sleep(500);

					KeyCommands.enter();
					KeyCommands.tab(1);
					KeyCommands.enter();

					Thread.sleep(2000);

					KeyCommands.enter();
					KeyCommands.tab(7);

				}
			}
		}
		KeyCommands.altF4();

	}

}
