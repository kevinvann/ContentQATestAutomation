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

	public String convertToOutputFilePath(String inputFileName) {
		StringBuffer strBuffer = new StringBuffer(inputFileName);
		String temp;
		if (inputFileName.substring(inputFileName.length() - 6, inputFileName.length() - 5).matches(".*\\d+.*")) {
			temp = inputFileName.substring(inputFileName.length() - 6, inputFileName.length() - 4);
			strBuffer.replace(inputFileName.length() - 9, inputFileName.length(), temp + ".out.csv");
			inputFileName = strBuffer.toString();
		}
		if (inputFileName.substring(inputFileName.length() - 5).matches(".*\\d+.*")) {

			temp = inputFileName.substring(inputFileName.length() - 5, inputFileName.length() - 4);
			strBuffer.replace(inputFileName.length() - 8, inputFileName.length(), temp + ".out.csv");
			inputFileName = strBuffer.toString();
		}
		String outputFilePath = outputFolderPath + "\\" + inputFileName.replace(".in.csv", ".out.csv");
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

	public Boolean isInputCsvFile(String inputFilePath) {
		if (inputFilePath.endsWith(".in.csv")) {
			return true;
		} else
			return false;
	}

	public Boolean isOutputCsvFile(String outputFilePath) {
		if (outputFilePath.endsWith(".out.csv")) {
			return true;
		} else
			return false;
	}

	public Boolean isCsvFile(String outputFilePath) {
		if (outputFilePath.endsWith(".csv")) {
			return true;
		} else
			return false;
	}

	public Boolean isReducedCsvFile(String outputFilePath) {
		if (outputFilePath.endsWith("_Reduced.csv")) {
			return true;
		} else
			return false;
	}

	public void moveReducedFiles() throws IOException {
		for (int y = 0; y < getNumOutputItems(); y++) {
			if (isReducedCsvFile(getOutputFilePath(y))) {
				Files.move(Paths.get(getOutputFilePath(y)), Paths.get(convertToReducedFilePath(getOutputFileName(y))),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

}
