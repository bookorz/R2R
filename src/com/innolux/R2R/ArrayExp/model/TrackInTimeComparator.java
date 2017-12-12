package com.innolux.R2R.ArrayExp.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.innolux.R2R.common.GlobleVar;

public class TrackInTimeComparator implements Comparator<MES_fwamtcomptrackhistory> {
	@Override
	public int compare(MES_fwamtcomptrackhistory o1, MES_fwamtcomptrackhistory o2) {
		SimpleDateFormat simDateFormat = new SimpleDateFormat();
		simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
		try {
			Date txtTime1 = simDateFormat.parse(o1.getTxnTimeStamp());
			Date txtTime2 = simDateFormat.parse(o2.getTxnTimeStamp());
			return txtTime2.compareTo(txtTime1);
		} catch (Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "TrackInTimeComparator Error");
		}
		return 0;
	}

}
