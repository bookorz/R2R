package com.innolux.R2R.common.rvHandler;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.interfaces.ITibRvListener;
import com.tibco.tibrv.*;

public class TibcoRvListener extends Thread implements TibrvMsgCallback {
	private Logger logger = Logger.getLogger(this.getClass());
	private String daemon;
	private String subject;
	private String service;
	private String network;
	private ITibRvListener _sourceObj;

	

	public TibcoRvListener(String _daemon, String _subject, String _service, String _network) {
		daemon = _daemon;
		subject = _subject;
		service = _service;
		network = _network;
		
	}
	
	public void SetService(ITibRvListener sourceObj){
		this._sourceObj = sourceObj;
	}

	public void run() {
		try {
			Tibrv.open(Tibrv.IMPL_NATIVE);
		} catch (TibrvException e) {
			System.err.println("Failed to open Tibrv in native implementation:");
			logger.error("Failed to open Tibrv in native implementation:");
			e.printStackTrace();
			// System.exit(0);
		}

		// Create RVD transport
		TibrvTransport transport = null;
		try {
			transport = new TibrvRvdTransport(service, network, daemon);
		} catch (TibrvException e) {
			System.err.println("Failed to create TibrvRvdTransport:");
			logger.error("Failed to create TibrvRvdTransport:");
			e.printStackTrace();
			// System.exit(0);
		}

		// Create listeners for specified subjects

		// create listener using default queue
		try {
			new TibrvListener(Tibrv.defaultQueue(), this, transport, subject, null);
			System.err.println("Listening on: " + subject);
		} catch (TibrvException e) {
			System.err.println("Failed to create listener:");
			logger.error("Failed to create listener:");
			e.printStackTrace();
			// System.exit(0);
		}

		// dispatch Tibrv events
		while (true) {
			try {
				if(Tibrv.defaultQueue().getCount()==0){
					
					//Thread.sleep(10000);
					
				}
				Tibrv.defaultQueue().dispatch();
				logger.info("RV queue count: " + Tibrv.defaultQueue().getCount() + " subject:" + subject);
				
				
			} catch (TibrvException e) {
				logger.error("Exception dispatching default queue: " + e);
				// System.exit(0);
			} catch (InterruptedException ie) {
				// System.exit(0);
			}
		}
	}

	public void onMsg(TibrvListener listener, TibrvMsg message) {
		String data="";
		try {
			// logger.debug("Message="+message.getField("DATA").data);
			TibrvMsgField field = message.getField("DATA");
			if (field.type == TibrvMsg.STRING) {
			    data = (String) field.data;
				logger.debug("RVListener onMsg:" + data);

				//sourceObj.onRvMsg(data);
				
				this._sourceObj.onRvMsg(data);
				
					
			}
		} catch (Exception e) {
			
			logger.error("subject:"+subject+" msg:"+data);
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}
}
