package com.innolux.R2R;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.rvHandler.TibcoRvSender;
import com.innolux.R2R.handler.ArrayExp;
import com.innolux.R2R.handler.RvDispatch;



public class R2R_Main {
	public static void main(String[] args) {
		//System.out.print(Integer.valueOf(String.valueOf(-5), 16).toString());
//		ReportMonitor();
//		
		//new RvDispatch().StartListen("tcp:8585", "INNOLUX.T2.PROD.PDS.PDSGLASSSEND.CELL.2CPAL100", "8585", "");
		
		//new ArrayExp("C:\\AryExp\\");
	}

	private static void ReportMonitor() {
		ToolUtility tools = new ToolUtility();
		Timer timer = new Timer();
		TibcoRvSender Monitor_RV = new TibcoRvSender("172.20.8.13:8585",
				"INNOLUX.T2.PROD.SYSTEMMONITOR.CF.SPCY", "8585", "");

		long period = 120 * 1000;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				
				String content = ">>L FwEapComplexTxn msgTag=FwEapComplexTxn sysId=\"R2R\" apId=\"R2R\" eapAction={ class=MonitorReport tId=\""
						+ tools.getTxnID("R2R", "MonitorReport")
						+ "\" userId=\"R2R\" status=\"OK\" Data=\"Please check host 172.20.8.136\" timestamp=\"\" }";

				Monitor_RV.sendMessage(content);
			}
		};

		timer.scheduleAtFixedRate(task, new Date(), period);
	}
}
