package com.deepnighttwo.resourceresolver.ui.views.utils;

import java.io.File;

import org.eclipse.jface.dialogs.IInputValidator;

public class FilePathValidator implements IInputValidator {

	private static final FilePathValidator INSTANCE = new FilePathValidator();

	public static IInputValidator getInstance() {
		return INSTANCE;
	}

	private FilePathValidator() {

	}

	@Override
	public String isValid(String filePath) {
		File path = new File(filePath);
		return path.isDirectory() ? null : filePath
				+ " is not a validated resource root. We need a folder here.";
	}
}