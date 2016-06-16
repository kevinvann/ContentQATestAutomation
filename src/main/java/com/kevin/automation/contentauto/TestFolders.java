package com.kevin.automation.contentauto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestFolders {

	File inputFolder;
	File outputFolder;
	File reducedFolder;
	private String inputFolderPath;
	private String outputFolderPath;
	private String reducedFolderPath;
	private File[] listOfInputFiles;
	private File[] listOfOutputFiles;

	public TestFolders(String folderPath) {
		inputFolderPath = folderPath + "\\Inputs";
		outputFolderPath = folderPath + "\\Outputs";
		reducedFolderPath = folderPath + "\\Reduced";

		inputFolder = new File(inputFolderPath);
		outputFolder = new File(outputFolderPath);
		reducedFolder = new File(reducedFolderPath);

		listOfInputFiles = inputFolder.listFiles();
		listOfOutputFiles = outputFolder.listFiles();
	}

	public String getInputPath() {
		return inputFolderPath;
	}

	public String getOutputPath() {
		return outputFolderPath;
	}

	public String getInputFilePath(int inputFileIndex) {
		String inputFilePath = listOfInputFiles[inputFileIndex].getPath();
		return inputFilePath;
	}

	public String getOutputFilePath(int outputFileIndex) {
		String outputFilePath = listOfOutputFiles[outputFileIndex].getPath();
		return outputFilePath;
	}

	public String getInputFileName(int inputFileIndex) {
		return listOfInputFiles[inputFileIndex].getName();
	}

	public String getOutputFileName(int outputFileIndex) {
		return listOfOutputFiles[outputFileIndex].getName();
	}

	public String convertToOutputFilePath(String inputFileName, int type) {
		StringBuffer strBuffer = new StringBuffer(inputFileName);
		String temp;
		String typeString[] = { "", "_READY", "_ENTERED", "_LCS_Inc_84i", "_LCS_Master_84m", "_LCS_SST_Inc_84ssti" };
		int index = 0;
		if (inputFileName.contains("StaticTestSuite")) {
			for (index = 0; index < 3; index++) {
				if (getInputFileName(index).endsWith(".in.csv")
						&& !getInputFileName(index).contains("StaticTestSuite")) {
					break;
				}
			}
			inputFileName = inputFileName.substring(0, 22) + "_" + getInputFileName(index).substring(0, 12) + ".in.csv";
		}

		if (inputFileName.substring(inputFileName.length() - 6, inputFileName.length() - 5).matches(".*\\d+.*")) {
			temp = inputFileName.substring(inputFileName.length() - 6, inputFileName.length() - 4);
			strBuffer.replace(inputFileName.length() - 9, inputFileName.length(), temp + ".in.csv");
			inputFileName = strBuffer.toString();
		}
		if (inputFileName.substring(inputFileName.length() - 5).matches(".*\\d+.*")) {

			temp = inputFileName.substring(inputFileName.length() - 5, inputFileName.length() - 4);
			strBuffer.replace(inputFileName.length() - 8, inputFileName.length(), temp + ".in.csv");
			inputFileName = strBuffer.toString();
		}
		String outputFilePath = outputFolderPath + "\\"
				+ inputFileName.replace(".in.csv", typeString[type] + ".out.csv");
		return outputFilePath;
	}

	public String convertToReducedFilePath(String outputFileName) {
		String reducedFilePath = reducedFolderPath + "\\" + outputFileName;
		return reducedFilePath;
	}

	public int getNumInputItems() {
		int count = 0;
		for (@SuppressWarnings("unused")
		File x : listOfInputFiles) {
			count++;
		}
		return count;
	}

	public int getNumOutputItems() {
		int count = 0;
		for (@SuppressWarnings("unused")
		File x : listOfOutputFiles) {
			count++;
		}
		return count;
	}

	public void moveReducedFiles() throws IOException {
		for (int y = 0; y < getNumOutputItems(); y++) {
			if (getOutputFilePath(y).endsWith("_Reduced.csv")) {
				Files.move(Paths.get(getOutputFilePath(y)), Paths.get(convertToReducedFilePath(getOutputFileName(y))),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

}
