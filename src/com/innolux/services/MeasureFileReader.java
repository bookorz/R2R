package com.innolux.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;

public class MeasureFileReader extends Thread {

	private static Logger logger = Logger.getLogger(MeasureFileReader.class);

	private IFileData targetObject;
	private String filePath = "";

	public void setFileHandler(IFileData FileHandler, String _filePath) {
		this.targetObject = FileHandler;
		this.filePath = _filePath;
	}

	public void run() {
		while (true) {
			FileReader FileStream;
			BufferedReader BufferedStream=null;
			try {

				FileStream = new FileReader(filePath);

				BufferedStream = new BufferedReader(FileStream);
				MeasureFileDataBase fileData = new MeasureFileDataBase();
				String data;
				

				do {
					data = BufferedStream.readLine();
					if (data == null) {
						break;
					}
					fileData.Store(data);
					

				} while (true);
				BufferedStream.close();
				
				targetObject.onFileData(fileData);
			} catch (Exception e) {
				logger.error(" readFile error:" + ToolUtility.StackTrace2String(e));
				try {
					BufferedStream.close();
				} catch (IOException e1) {
					logger.error(" readFile error:" + ToolUtility.StackTrace2String(e1));
				}
			}
			
		}
	}

}
