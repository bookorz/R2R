package com.innolux.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
			}
			
		}
	}
	
	public static List<MeasureFileDataBase> GetAllFile(String EqpId,String SubEqpId, String Recipe,String PreEqpId,String PreSubEqpId, String PreRecipe){
		List<MeasureFileDataBase> result = new ArrayList<MeasureFileDataBase>();
		try{
			Map<String,MeasureFileDataBase> tmp = new HashMap<String,MeasureFileDataBase>();
			List<MeasureFileData> rowDataList = MeasureFileData_CRUD.read(EqpId, SubEqpId, Recipe, PreEqpId, PreSubEqpId, PreRecipe);
			for(MeasureFileData eachRow:rowDataList){
				String key = eachRow.getFileName();
				if(tmp.containsKey(key)){
					tmp.get(key).Store(eachRow.getHeaderName(),eachRow.getRowIndex(),eachRow.getRowData());
					
				}else{
					MeasureFileDataBase mFile = new MeasureFileDataBase();
					mFile.Store(eachRow.getHeaderName(),eachRow.getRowIndex(),eachRow.getRowData());
					tmp.put(key, mFile);
				}
			}
			
		}catch(Exception e){
			logger.error("GetAllFile error:" + ToolUtility.StackTrace2String(e));
		}
		return result;
	}

}
