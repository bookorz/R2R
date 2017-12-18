package com.innolux.R2R.handler;

import org.apache.log4j.Logger;

import com.innolux.R2R.cf_coater.model.Coater_PDS_Data;
import com.innolux.R2R.cf_coater.model.Coater_PDS_Data_CRUD;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.PDSBase;
import com.innolux.R2R.common.rvHandler.TibcoRvListener;
import com.innolux.R2R.handler.PALR2R;
import com.innolux.R2R.interfaces.ITibRvListener;
import com.innolux.R2R.parser.PDS;

public class RvDispatch implements ITibRvListener {

	private Logger logger = Logger.getLogger(this.getClass());
	

	public void StartListen(String _daemon, String _subject, String _service, String _network) {
		TibcoRvListener rv = new TibcoRvListener(_daemon, _subject, _service, _network);
		rv.SetService(this);
		rv.StartService();
	}

	public void onRvMsg(String msg) {

		try {
			if (msg.indexOf("class=PDSGlassSend") != -1) {
				PDSBase pds = new PDSBase();
				if (new PDS().Parse(msg, pds)) {

					

					switch (pds.SubEquipment_ID) {
					case "2CPAL130":
					case "2CPAL230":
					case "2CPAL330":
						new PALR2R(pds).Excute();

						break;
					case "2FRCT310":
					case "2FBCT110" :
					case "2FBCT210" :
					case "2FBCT310" :
					case "2FMCT110" :
					case "2FMCT210" :
					case "2FMCT310" :
					case "2FMCT410" :
					case "2FGCT110" :
					case "2FGCT210" :
					case "2FGCT310" :
					case "2FVCT110" :
					case "2FPCT110" :
					case "2FPCT210" :
					case "2FPCT310" :
					case "2FRCT110" :
					case "2FRCT210" :				
						
						Coater_PDS_Data CoaterPds  = Coater_PDS_Data_CRUD.read(pds.SubEquipment_ID, pds.PPID);
						
						if(CoaterPds==null){
							CoaterPds = new Coater_PDS_Data();
							CoaterPds.setEqpId(pds.SubEquipment_ID);
							CoaterPds.setPPID(pds.PPID);
							CoaterPds.setAfterDis_T3(Double.parseDouble(pds.GetParameter("AfterDis_T3")));
							CoaterPds.setCoating_W(Double.parseDouble(pds.GetParameter("Coating_W")));
							CoaterPds.setCoatSpd_V1(Double.parseDouble(pds.GetParameter("CoatSpd_V1")));
							CoaterPds.setDis_Spd_Q2(Double.parseDouble(pds.GetParameter("Dis_Spd_Q2")));
							CoaterPds.setPumpDecel_T(Double.parseDouble(pds.GetParameter("PumpDecel_T")));						
							CoaterPds.setSqueegee_L(Double.parseDouble(pds.GetParameter("Squeegee_L")));
							Coater_PDS_Data_CRUD.create(CoaterPds);
						}else{
							CoaterPds.setAfterDis_T3(Double.parseDouble(pds.GetParameter("AfterDis_T3")));
							CoaterPds.setCoating_W(Double.parseDouble(pds.GetParameter("Coating_W")));
							CoaterPds.setCoatSpd_V1(Double.parseDouble(pds.GetParameter("CoatSpd_V1")));
							CoaterPds.setDis_Spd_Q2(Double.parseDouble(pds.GetParameter("Dis_Spd_Q2")));
							CoaterPds.setPumpDecel_T(Double.parseDouble(pds.GetParameter("PumpDecel_T")));						
							CoaterPds.setSqueegee_L(Double.parseDouble(pds.GetParameter("Squeegee_L")));
							Coater_PDS_Data_CRUD.update(CoaterPds);
						}
						
						break;
					}
 
					

				} else {
					logger.error("PDS parse error.");
				}
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

}
