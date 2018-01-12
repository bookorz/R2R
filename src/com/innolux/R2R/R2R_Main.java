package com.innolux.R2R;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.rvHandler.TibcoRvSender;
import com.innolux.R2R.handler.ArrayExp;
import com.innolux.R2R.handler.CFCoater;
import com.innolux.R2R.handler.RvDispatch;



public class R2R_Main {
	public static void main(String[] args) {
	
		ReportMonitor();
//		

		//new RvDispatch().StartListen("tcp:8585", "INNOLUX.T2.PROD.PDS.PDSGLASSSEND.CELL.>", "8585", "");
		new RvDispatch().StartListen("tcp:8585", "INNOLUX.T2.PROD.PDS.PDSGLASSSEND.CF.>", "8585", "");
		new ArrayExp("Y:\\array\\cdol\\r2r\\source\\", "Y:\\array\\cdol\\r2r\\ng\\");
		new RvDispatch().StartListen("tcp:8585", "INNOLUX.T2.PROD.PDS.PDSGLASSSEND.CELL.>", "8585", "");
		//new RvDispatch().StartListen("tcp:8585", "INNOLUX.T2.PROD.PDS.PDSGLASSSEND.CF.>", "8585", "");
		//new ArrayExp("C:\\AryExp\\");
		

	}

	private static void ReportMonitor() {
		
		Timer timer = new Timer();
		TibcoRvSender Monitor_RV = new TibcoRvSender("tcp:8585",
				"INNOLUX.T2.PROD.SYSTEMMONITOR.CF.R2R", "8585", "");

		long period = 120 * 1000;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				
				String content = ">>L FwEapComplexTxn msgTag=FwEapComplexTxn sysId=\"R2R\" apId=\"R2R\" eapAction={ class=MonitorReport tId=\""
						+ ToolUtility.getTxnID("R2R", "MonitorReport")
						+ "\" userId=\"R2R\" status=\"OK\" Data=\"Run to run AP error, please check host 172.20.8.138\" timestamp=\"\" }";

				Monitor_RV.sendMessage(content);
			}
		};

		timer.scheduleAtFixedRate(task, new Date(), period);
	}
}
