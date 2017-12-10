package com.innolux.R2R.handler;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.common.base.RegulationCollection;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.models.bc.Line;
import com.innolux.models.bc.Line_CRUD;
import com.innolux.models.bc.Node;
import com.innolux.models.bc.Node_CRUD;
import com.innolux.models.dems.BCIP_CRUD;
import com.innolux.services.MeasureFileReader;

public class CFCoater implements IFileData {
	private static Logger logger = Logger.getLogger(CFCoater.class);

	public CFCoater(String _SourcePatch,String _NgPath) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, _SourcePatch,_NgPath);
		fileService.start();
	}

	@Override
	public void onFileData(MeasureFileDataBase data) {
		try {
			String EqpId = data.FetchInfo("HEADER", "EQ_ID");
			String SubEqpId = data.FetchInfo("HEADER", "SUBEQ_ID");
			
			String PreEqpId1 = EqpId;
			String PreSubEqpId1 = getPreSubEqpId(EqpId,"COT");
			String PPID = data.FetchValue("GLASS_DATA", "RECIPE_ID");
			String RecipeNo = data.FetchValue("EQP_GLASS_DATA", "RECIPE_NO");
			
			
			
			

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	private RegulationCollection Caculation(MeasureFileDataBase data) {
		RegulationCollection result = new RegulationCollection();

		try {



		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	private String getPreSubEqpId(String BCName,String EqpType){
		String result = "";
		try{
			String BCIP = BCIP_CRUD.read(BCName).getIP();
			if(!BCIP.equals("")){
			Line BCLine = Line_CRUD.read(BCName, BCIP);
				if(BCLine != null){
					Node BCNode = Node_CRUD.read(BCLine.getBCNo(), BCLine.getBCLineNo(), BCLine.getBCFabType(), EqpType, BCIP);
					if(BCNode!=null){
						result = BCNode.getSubEqpID();
					}else{
						logger.error("CFCoater getPreSubEqpId BCNode is null.");
					}
				}else{
					logger.error("CFCoater getPreSubEqpId BCLine is null.");
				}
			}else{
				logger.error("CFCoater getPreSubEqpId BCIP is null.");
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private boolean checkCoaterProcessTime() {
		boolean result = false;
		try {
			
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

}
