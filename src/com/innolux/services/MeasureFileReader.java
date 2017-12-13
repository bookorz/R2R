package com.innolux.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.R2R.model.MeasureFileData;
import com.innolux.R2R.model.MeasureFileData_CRUD;

public class MeasureFileReader extends Thread {

	private static Logger logger = Logger.getLogger(MeasureFileReader.class);

	private IFileData targetObject;
	private String SourcePath = "";
	private String NgPath = "";

	public void setFileHandler(IFileData FileHandler, String _SourcePath, String _NgPath) {
		this.targetObject = FileHandler;
		this.SourcePath = _SourcePath;
		this.NgPath = _NgPath;
	}

	public void run() {
		while (true) {

			try {
				File folder = new File(this.SourcePath);
				File[] listOfFiles = folder.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {
					try {
						if (listOfFiles[i].isFile()) {
							ReadFile(listOfFiles[i].getPath(),listOfFiles[i].getName());

							// listOfFiles[i].delete(); // for DEBUG use

						} else if (listOfFiles[i].isDirectory()) {

						}
					} catch (Exception e1) {

						logger.error("readFile error:" + ToolUtility.StackTrace2String(e1));
						copyfile(listOfFiles[i].getPath(),this.NgPath+listOfFiles[i].getName());
					}
				}
			} catch (Exception e) {

				logger.error("readFile error:" + ToolUtility.StackTrace2String(e));

			}

		}
	}

	public void ReadFile(String srFile,String fileName) throws Exception {
		FileReader FileStream;
		BufferedReader BufferedStream = null;
		try {

			FileStream = new FileReader(srFile);
			
			BufferedStream = new BufferedReader(FileStream);
			MeasureFileDataBase fileData = new MeasureFileDataBase();
			fileData.setFileName(fileName);
			String data;

			do {
				data = BufferedStream.readLine();
				if (data == null) {
					break;
				}
				fileData.ReadLine(data);

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
			throw new Exception(e.getMessage());
		}
	}

	public void copyfile(String srFile, String dtFile) {

		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			// System.out.println("File copied:" + srFile);
		} catch (Exception e) {
			logger.error("copyfile error:" + ToolUtility.StackTrace2String(e));
		}
	}
	
	

	public static List<MeasureFileDataBase> GetAllFiles(String EqpId, String SubEqpId, String Recipe, String PreEqpId,
			String PreSubEqpId, String PreRecipe) {
		List<MeasureFileDataBase> result = new ArrayList<MeasureFileDataBase>();
		try {
			Map<String, MeasureFileDataBase> tmp = new HashMap<String, MeasureFileDataBase>();
			List<MeasureFileData> rowDataList = MeasureFileData_CRUD.read(EqpId, SubEqpId, Recipe, PreEqpId,
					PreSubEqpId, PreRecipe);
			for (MeasureFileData eachRow : rowDataList) {
				String key = eachRow.getFileName();
				if (tmp.containsKey(key)) {
					tmp.get(key).Store(eachRow.getHeaderName(), eachRow.getRowIndex(), eachRow.getRowData());

				} else {
					MeasureFileDataBase mFile = new MeasureFileDataBase();
					mFile.Store(eachRow.getHeaderName(), eachRow.getRowIndex(), eachRow.getRowData());
					tmp.put(key, mFile);
				}
			}
			result = new ArrayList<MeasureFileDataBase>(tmp.values());

		} catch (Exception e) {
			logger.error("GetAllFile error:" + ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	public static boolean DeleteAllFiles(String EqpId, String SubEqpId, String Recipe, String PreEqpId,
			String PreSubEqpId, String PreRecipe) {
		try {
			return MeasureFileData_CRUD.delete(EqpId, SubEqpId, Recipe, PreEqpId, PreSubEqpId, PreRecipe);
		} catch (Exception e) {
			logger.error("GetAllFile error:" + ToolUtility.StackTrace2String(e));
			return false;
		}
	}

}
