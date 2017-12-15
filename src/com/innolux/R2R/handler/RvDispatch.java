package com.innolux.R2R.handler;

import org.apache.log4j.Logger;

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
