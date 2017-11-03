package com.innolux.R2R.handler;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.ConfigBase;
import com.innolux.R2R.common.base.GlassDataBase;
import com.innolux.R2R.common.base.PAParamBase;
import com.innolux.R2R.common.base.PDSBase;
import com.innolux.R2R.common.base.RegulationCollection;
import com.innolux.R2R.handler.dataProvider.Config;
import com.innolux.R2R.handler.dataProvider.GlassData;

public class PALR2R {
	private Logger logger = Logger.getLogger(this.getClass());
	private ToolUtility tools = new ToolUtility();
	private PDSBase _PDS = null;
	private Config ConfigTool = new Config();
	private String R2R_ID = "";
	private int SampleSize = 0;
	private int WaitCount = 0;
	private boolean WaitingForConfirm = false;
	private PAParamBase currentPdsValues = new PAParamBase();

	public PALR2R(PDSBase pds) {
		this._PDS = pds;
	}

	public RegulationCollection Excute() {

		boolean ResetAll = false;

		PAParamBase GlassValues = new PAParamBase();
		RegulationCollection result = new RegulationCollection();
		this.R2R_ID = this._PDS.Equipment_ID + "_" + this._PDS.SubEquipment_ID;
		try {
			this.initial(this.ConfigTool.Lookup(this.R2R_ID));

			if (this.WaitingForConfirm) {

				this.WaitCount++;
				if (this.WaitCount >= 10) {
					this.WaitingForConfirm = false;
					this.WaitCount = 0;
				}

			} else {
				if (!PdsDataConvert(currentPdsValues, this._PDS)) {
					return result;
				}
				Vector<GlassDataBase> GlassDatas = new GlassData().Lookup(this.R2R_ID, this.SampleSize);
				if (GlassDatas.size() != 0) {

					if (GlassDataConvert(GlassValues, GlassDatas.get(0))) {
						if (currentPdsValues.C_OFFSET_X != GlassValues.C_OFFSET_X) {
							ResetAll = true;
						}
						if (currentPdsValues.C_OFFSET_Y != GlassValues.C_OFFSET_Y) {
							ResetAll = true;
						}
						if (currentPdsValues.C_OFFSET_£c != GlassValues.C_OFFSET_£c) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_X != GlassValues.T_OFFSET_X) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_Y != GlassValues.T_OFFSET_Y) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_£c != GlassValues.T_OFFSET_£c) {
							ResetAll = true;
						}

						if (ResetAll) {
							logger.info(" Run to run ID:" + this.R2R_ID + " Reset all data: current:"
									+ currentPdsValues.toString());
							logger.info(" Run to run ID:" + this.R2R_ID + " Reset all data: last:"
									+ GlassValues.toString());
							new GlassData().DeleteAll(R2R_ID);
						}
					} else {
						return result;
					}
				}
				// PreFilter

				if (PreFilter(currentPdsValues)) {
					if (this.WaitingForConfirm && this.WaitCount >= 10) {
						this.WaitCount++;
					} else {
						// Insert new glass data
						if (AddSample(this._PDS)) {
							GlassDatas = new GlassData().Lookup(this.R2R_ID, this.SampleSize);
							if (GlassDatas.size() == this.SampleSize) {
								result = Caculation(GlassDatas, this.R2R_ID);
								new GlassData().DeleteAll(R2R_ID);
								this.WaitingForConfirm = true;

							} else {
								logger.info(" Run to run ID:" + this.R2R_ID + " Current sample size:"
										+ GlassDatas.size() + ", config size:" + this.SampleSize);

							}
						} else {
							logger.error(" Run to run ID:" + this.R2R_ID + " Add sample error.");
						}
					}
				} else {
					logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter is not pass.");
					return result;
				}
			}
			this.UpdateConfig(this.ConfigTool);
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
		return result;
	}

	private String demical2Hex(double value, double rate) {
		String result = "";
		int intVal = 0;
		value = value / rate;
		if (value >= 0) {
			intVal = (int) Math.floor(value);
		} else {
			intVal = (int) Math.ceil(value);
		}
		// result = Integer.valueOf(String.valueOf(intVal), 16).toString();

		result = Integer.toHexString(intVal & 0xffff);
		if (result.length() <= 4) {
			while (result.length() < 4) {
				result = "0" + result;
			}
			logger.debug(" Run to run ID:" + this.R2R_ID + " demical2Hex value:" + value + " rate:" + rate + " result:"
					+ result);
		} else {
			logger.error(" Run to run ID:" + this.R2R_ID + " demical2Hex return nothing value:" + value + " rate:"
					+ rate + " result:" + result);
			result = "";
		}

		return result;
	}

	private RegulationCollection Caculation(Vector<GlassDataBase> GlassDatas, String R2RID) {
		RegulationCollection result = new RegulationCollection();
		result.BcName = this._PDS.Equipment_ID;
		result.EqpName = this._PDS.SubEquipment_ID;
		// CF Side begin
		// X Way
		double C1X2X_Avg = 0;
		double C1X2X_Target = currentPdsValues.C_ACC_1X_TG;
		double C_OFFSET_X = 0;
		// Y Way
		double C1Y3Y_Avg = 0;
		double C1Y3Y_Target = currentPdsValues.C_ACC_1Y_TG;
		double C_OFFSET_Y = 0;
		// £c Way
		double C3Y_Avg = 0;
		double C1Y_Avg = 0;
		double C_OFFSET_£c = 0;
		// CF Side end

		// TFT Side begin
		// X Way
		double T1X2X_Avg = 0;
		double T1X2X_Target = currentPdsValues.T_ACC_1X_TG;
		double T_OFFSET_X = 0;
		// Y Way
		double T1Y3Y_Avg = 0;
		double T1Y3Y_Target = currentPdsValues.T_ACC_1Y_TG;
		double T_OFFSET_Y = 0;
		// £c Way
		double T3Y_Avg = 0;
		double T1Y_Avg = 0;
		double T_OFFSET_£c = 0;

		// TFT Side end
		try {
			switch (R2RID) {
			case "2CPAL100_2CPAL130":
			case "2CPAL300_2CPAL330":
				for (GlassDataBase eachGlass : GlassDatas) {
					PAParamBase PdsValues = new PAParamBase();
					if (GlassDataConvert(PdsValues, eachGlass)) {
						C1X2X_Avg += PdsValues.C_ACC_1X + PdsValues.C_ACC_2X;
						C1Y3Y_Avg += PdsValues.C_ACC_1Y + PdsValues.C_ACC_3Y;
						C3Y_Avg += PdsValues.C_ACC_3Y;
						C1Y_Avg += PdsValues.C_ACC_1Y;

						T1X2X_Avg += PdsValues.T_ACC_1X + PdsValues.T_ACC_2X;
						T1Y3Y_Avg += PdsValues.T_ACC_1Y + PdsValues.T_ACC_3Y;
						T3Y_Avg += PdsValues.T_ACC_3Y;
						T1Y_Avg += PdsValues.T_ACC_1Y;
					} else {
						return result;
					}
				}
				
				
				C3Y_Avg = C3Y_Avg / (double) GlassDatas.size();
				C1Y_Avg = C1Y_Avg / (double) GlassDatas.size();

				
				T3Y_Avg = T3Y_Avg / (double) GlassDatas.size();
				T1Y_Avg = T1Y_Avg / (double) GlassDatas.size();
				
				
				// CF Side begin
				// X Way
				C1X2X_Avg = C1X2X_Avg / ((double) GlassDatas.size() * (double) 2.0);
				C_OFFSET_X = (C1X2X_Target - C1X2X_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_X:" + C_OFFSET_X);
				if (Math.abs(C1X2X_Target - C1X2X_Avg) <= 0.05) {
					C_OFFSET_X = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_X += currentPdsValues.C_OFFSET_X;
				result.StoreRegulation(4, demical2Hex(C_OFFSET_X, 0.01));
				// Y Way
				C1Y3Y_Avg = C1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				C_OFFSET_Y = (C1Y3Y_Avg - C1Y3Y_Target) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_Y:" + C_OFFSET_Y);
				if (Math.abs(C1Y3Y_Avg - C1Y3Y_Target) <= 0.05) {
					C_OFFSET_Y = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_Y += currentPdsValues.C_OFFSET_Y;
				result.StoreRegulation(5, demical2Hex(C_OFFSET_Y, 0.01));
				// £c Way
				C_OFFSET_£c = (C3Y_Avg - C1Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " C3Y_Avg:" + C3Y_Avg + " C1Y_Avg:" + C1Y_Avg + " size()*2:"+(double) GlassDatas.size() * (double) 2.0 );
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_£c:" + C_OFFSET_£c);
				if (Math.abs(C3Y_Avg - C1Y_Avg) <= 0.1) {
					C_OFFSET_£c = 0; // If less than target 0.1 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_£c += currentPdsValues.C_OFFSET_£c;
				result.StoreRegulation(6, demical2Hex(C_OFFSET_£c, 0.001));
				// CF Side end

				// TFT Side begin
				// X Way
				T1X2X_Avg = T1X2X_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_X = (T1X2X_Target - T1X2X_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_X:" + T_OFFSET_X);
				if (Math.abs(T1X2X_Target - T1X2X_Avg) <= 0.05) {
					T_OFFSET_X = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_X += currentPdsValues.T_OFFSET_X;
				result.StoreRegulation(1, demical2Hex(T_OFFSET_X, 0.01));
				// Y Way
				T1Y3Y_Avg = T1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_Y = (T1Y3Y_Target - T1Y3Y_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_Y:" + T_OFFSET_Y);
				if (Math.abs(T1Y3Y_Avg - T1Y3Y_Target) <= 0.05) {
					T_OFFSET_Y = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_Y += currentPdsValues.T_OFFSET_Y;
				result.StoreRegulation(2, demical2Hex(T_OFFSET_Y, 0.01));
				// £c Way
				T_OFFSET_£c = (T1Y_Avg - T3Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_£c:" + T_OFFSET_£c);
				if (Math.abs(T1Y_Avg - T3Y_Avg) <= 0.1) {
					T_OFFSET_£c = 0; // If less than target 0.1 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_£c += currentPdsValues.T_OFFSET_£c;
				result.StoreRegulation(3, demical2Hex(T_OFFSET_£c, 0.001));
				// TFT Side end

				break;
			case "2CPAL200_2CPAL230":
				for (GlassDataBase eachGlass : GlassDatas) {
					PAParamBase PdsValues = new PAParamBase();
					if (GlassDataConvert(PdsValues, eachGlass)) {
						C1X2X_Avg += PdsValues.C_ACC_1X + PdsValues.C_ACC_2X;
						C1Y3Y_Avg += PdsValues.C_ACC_1Y + PdsValues.C_ACC_3Y;
						C3Y_Avg += PdsValues.C_ACC_3Y;
						C1Y_Avg += PdsValues.C_ACC_1Y;

						T1X2X_Avg += PdsValues.T_ACC_1X + PdsValues.T_ACC_2X;
						T1Y3Y_Avg += PdsValues.T_ACC_1Y + PdsValues.T_ACC_3Y;
						T3Y_Avg += PdsValues.T_ACC_3Y;
						T1Y_Avg += PdsValues.T_ACC_1Y;
					} else {
						return result;
					}
				}
				// CF Side begin
				// X Way
				C1X2X_Avg = C1X2X_Avg / ((double) GlassDatas.size() * (double) 2.0);
				C_OFFSET_X = (C1X2X_Target - C1X2X_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_X:" + C_OFFSET_X);
				if (Math.abs(C1X2X_Target - C1X2X_Avg) <= 0.5) {
					C_OFFSET_X = 0; // If less than target 0.5 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_X += currentPdsValues.C_OFFSET_X;
				result.StoreRegulation(4, demical2Hex(C_OFFSET_X, 0.01));
				// Y Way
				C1Y3Y_Avg = C1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				C_OFFSET_Y = (C1Y3Y_Target - C1Y3Y_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_Y:" + C_OFFSET_Y);
				if (Math.abs(C1Y3Y_Avg - C1Y3Y_Target) <= 0.5) {
					C_OFFSET_Y = 0; // If less than target 0.5 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_Y += currentPdsValues.C_OFFSET_Y;
				result.StoreRegulation(5, demical2Hex(C_OFFSET_Y, 0.01));
				// £c Way
				C_OFFSET_£c = (C1Y_Avg - C3Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_£c:" + C_OFFSET_£c);
				if (Math.abs(C3Y_Avg - C1Y_Avg) <= 0.1) {
					C_OFFSET_£c = 0; // If less than target 0.1 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_£c += currentPdsValues.C_OFFSET_£c;
				result.StoreRegulation(6, demical2Hex(C_OFFSET_£c, 0.001));
				// CF Side end

				// TFT Side begin
				// X Way
				T1X2X_Avg = T1X2X_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_X = (T1X2X_Target - T1X2X_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_X:" + T_OFFSET_X);
				if (Math.abs(T1X2X_Target - T1X2X_Avg) <= 0.5) {
					T_OFFSET_X = 0; // If less than target 0.5 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_X += currentPdsValues.T_OFFSET_X;
				result.StoreRegulation(1, demical2Hex(T_OFFSET_X, 0.01));
				// Y Way
				T1Y3Y_Avg = T1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_Y = (T1Y3Y_Avg - T1Y3Y_Target) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_Y:" + T_OFFSET_Y);
				if (Math.abs(T1Y3Y_Avg - T1Y3Y_Target) <= 0.5) {
					T_OFFSET_Y = 0; // If less than target 0.5 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_Y += currentPdsValues.T_OFFSET_Y;
				result.StoreRegulation(2, demical2Hex(T_OFFSET_Y, 0.01));
				// £c Way
				T_OFFSET_£c = (T3Y_Avg - T1Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_£c:" + T_OFFSET_£c);
				if (Math.abs(T1Y_Avg - T3Y_Avg) <= 0.1) {
					T_OFFSET_£c = 0; // If less than target 0.1 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_£c += currentPdsValues.T_OFFSET_£c;
				result.StoreRegulation(3, demical2Hex(T_OFFSET_£c, 0.001));
				// TFT Side end

				break;
			}

		} catch (Exception e) {
			logger.error(tools.StackTrace2String(e));
		}
		result.toString();
		return result;
	}

	private boolean AddSample(PDSBase pds) {
		boolean result = false;
		try {

			GlassData gData = new GlassData();
			gData.Append(R2R_ID, pds.Component_ID, "Glass_ID", pds.Component_ID);

			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_1X", pds.GetParameter("C_ACC_1X"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_1X_TG", pds.GetParameter("C_ACC_1X_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_1Y", pds.GetParameter("C_ACC_1Y"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_1Y_TG", pds.GetParameter("C_ACC_1Y_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_2X", pds.GetParameter("C_ACC_2X"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_2X_TG", pds.GetParameter("C_ACC_2X_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_3Y", pds.GetParameter("C_ACC_3Y"));
			gData.Append(R2R_ID, pds.Component_ID, "C_ACC_3Y_TG", pds.GetParameter("C_ACC_3Y_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_X", pds.GetParameter("C_OFFSET_X"));
			gData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_Y", pds.GetParameter("C_OFFSET_¢ç"));
			gData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_£c", pds.GetParameter("C_OFFSET_£c"));

			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_1X", pds.GetParameter("T_ACC_1X"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_1X_TG", pds.GetParameter("T_ACC_1X_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_1Y", pds.GetParameter("T_ACC_1Y"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_1Y_TG", pds.GetParameter("T_ACC_1Y_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_2X", pds.GetParameter("T_ACC_2X"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_2X_TG", pds.GetParameter("T_ACC_2X_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_3Y", pds.GetParameter("T_ACC_3Y"));
			gData.Append(R2R_ID, pds.Component_ID, "T_ACC_3Y_TG", pds.GetParameter("T_ACC_3Y_TG"));
			gData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_X", pds.GetParameter("T_OFFSET_X"));
			gData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_Y", pds.GetParameter("T_OFFSET_¢ç"));
			gData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_£c", pds.GetParameter("T_OFFSET_£c"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " AddSample " + gData.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
		return result;
	}

	private boolean GlassDataConvert(PAParamBase GlassValues, GlassDataBase GlassDatas) {
		boolean result = false;
		try {
			GlassValues.Glass_ID = GlassDatas.Glass_ID;
			GlassValues.C_ACC_1X = Double.parseDouble(GlassDatas.GetParameter("C_ACC_1X"));
			GlassValues.C_ACC_1X_TG = Double.parseDouble(GlassDatas.GetParameter("C_ACC_1X_TG"));
			GlassValues.C_ACC_1Y = Double.parseDouble(GlassDatas.GetParameter("C_ACC_1Y"));
			GlassValues.C_ACC_1Y_TG = Double.parseDouble(GlassDatas.GetParameter("C_ACC_1Y_TG"));
			GlassValues.C_ACC_2X = Double.parseDouble(GlassDatas.GetParameter("C_ACC_2X"));
			GlassValues.C_ACC_2X_TG = Double.parseDouble(GlassDatas.GetParameter("C_ACC_2X_TG"));
			GlassValues.C_ACC_3Y = Double.parseDouble(GlassDatas.GetParameter("C_ACC_3Y"));
			GlassValues.C_ACC_3Y_TG = Double.parseDouble(GlassDatas.GetParameter("C_ACC_3Y_TG"));
			GlassValues.C_OFFSET_X = Double.parseDouble(GlassDatas.GetParameter("C_OFFSET_X"));
			GlassValues.C_OFFSET_Y = Double.parseDouble(GlassDatas.GetParameter("C_OFFSET_Y"));
			GlassValues.C_OFFSET_£c = Double.parseDouble(GlassDatas.GetParameter("C_OFFSET_£c"));

			GlassValues.T_ACC_1X = Double.parseDouble(GlassDatas.GetParameter("T_ACC_1X"));
			GlassValues.T_ACC_1X_TG = Double.parseDouble(GlassDatas.GetParameter("T_ACC_1X_TG"));
			GlassValues.T_ACC_1Y = Double.parseDouble(GlassDatas.GetParameter("T_ACC_1Y"));
			GlassValues.T_ACC_1Y_TG = Double.parseDouble(GlassDatas.GetParameter("T_ACC_1Y_TG"));
			GlassValues.T_ACC_2X = Double.parseDouble(GlassDatas.GetParameter("T_ACC_2X"));
			GlassValues.T_ACC_2X_TG = Double.parseDouble(GlassDatas.GetParameter("T_ACC_2X_TG"));
			GlassValues.T_ACC_3Y = Double.parseDouble(GlassDatas.GetParameter("T_ACC_3Y"));
			GlassValues.T_ACC_3Y_TG = Double.parseDouble(GlassDatas.GetParameter("T_ACC_3Y_TG"));
			GlassValues.T_OFFSET_X = Double.parseDouble(GlassDatas.GetParameter("T_OFFSET_X"));
			GlassValues.T_OFFSET_Y = Double.parseDouble(GlassDatas.GetParameter("T_OFFSET_Y"));
			GlassValues.T_OFFSET_£c = Double.parseDouble(GlassDatas.GetParameter("T_OFFSET_£c"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " GlassDataConvert " + GlassValues.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
		return result;
	}

	private boolean PdsDataConvert(PAParamBase PdsValues, PDSBase pds) {
		boolean result = false;
		try {
			PdsValues.Glass_ID = pds.Component_ID;

			PdsValues.C_ACC_1X = Double.parseDouble(pds.GetParameter("C_ACC_1X"));
			PdsValues.C_ACC_1X_TG = Double.parseDouble(pds.GetParameter("C_ACC_1X_TG"));
			PdsValues.C_ACC_1Y = Double.parseDouble(pds.GetParameter("C_ACC_1Y"));
			PdsValues.C_ACC_1Y_TG = Double.parseDouble(pds.GetParameter("C_ACC_1Y_TG"));
			PdsValues.C_ACC_2X = Double.parseDouble(pds.GetParameter("C_ACC_2X"));
			PdsValues.C_ACC_2X_TG = Double.parseDouble(pds.GetParameter("C_ACC_2X_TG"));
			PdsValues.C_ACC_3Y = Double.parseDouble(pds.GetParameter("C_ACC_3Y"));
			PdsValues.C_ACC_3Y_TG = Double.parseDouble(pds.GetParameter("C_ACC_3Y_TG"));
			PdsValues.C_OFFSET_X = Double.parseDouble(pds.GetParameter("C_OFFSET_X"));
			PdsValues.C_OFFSET_Y = Double.parseDouble(pds.GetParameter("C_OFFSET_¢ç"));
			PdsValues.C_OFFSET_£c = Double.parseDouble(pds.GetParameter("C_OFFSET_£c"));

			PdsValues.T_ACC_1X = Double.parseDouble(pds.GetParameter("T_ACC_1X"));
			PdsValues.T_ACC_1X_TG = Double.parseDouble(pds.GetParameter("T_ACC_1X_TG"));
			PdsValues.T_ACC_1Y = Double.parseDouble(pds.GetParameter("T_ACC_1Y"));
			PdsValues.T_ACC_1Y_TG = Double.parseDouble(pds.GetParameter("T_ACC_1Y_TG"));
			PdsValues.T_ACC_2X = Double.parseDouble(pds.GetParameter("T_ACC_2X"));
			PdsValues.T_ACC_2X_TG = Double.parseDouble(pds.GetParameter("T_ACC_2X_TG"));
			PdsValues.T_ACC_3Y = Double.parseDouble(pds.GetParameter("T_ACC_3Y"));
			PdsValues.T_ACC_3Y_TG = Double.parseDouble(pds.GetParameter("T_ACC_3Y_TG"));
			PdsValues.T_OFFSET_X = Double.parseDouble(pds.GetParameter("T_OFFSET_X"));
			PdsValues.T_OFFSET_Y = Double.parseDouble(pds.GetParameter("T_OFFSET_¢ç"));
			PdsValues.T_OFFSET_£c = Double.parseDouble(pds.GetParameter("T_OFFSET_£c"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " PdsDataConvert " + PdsValues.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
		return result;
	}

	private boolean PreFilter(PAParamBase pds) {
		boolean result = true;
		try {
			if (Math.abs(pds.C_ACC_1X - pds.C_ACC_1X_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.C_ACC_1X - pds.C_ACC_1X_TG) = "
						+ Math.abs(pds.C_ACC_1X - pds.C_ACC_1X_TG) + " > 0.3");
			}
			if (Math.abs(pds.C_ACC_1Y - pds.C_ACC_1Y_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.C_ACC_1Y - pds.C_ACC_1Y_TG) = "
						+ Math.abs(pds.C_ACC_1Y - pds.C_ACC_1Y_TG) + " > 0.3");
			}
			if (Math.abs(pds.C_ACC_2X - pds.C_ACC_2X_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.C_ACC_2X - pds.C_ACC_2X_TG) = "
						+ Math.abs(pds.C_ACC_2X - pds.C_ACC_2X_TG) + " > 0.3");
			}
			if (Math.abs(pds.C_ACC_3Y - pds.C_ACC_3Y_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.C_ACC_3Y - pds.C_ACC_3Y_TG) = "
						+ Math.abs(pds.C_ACC_3Y - pds.C_ACC_3Y_TG) + " > 0.3");
			}
			if (Math.abs(pds.T_ACC_1X - pds.T_ACC_1X_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.T_ACC_1X - pds.T_ACC_1X_TG) = "
						+ Math.abs(pds.T_ACC_1X - pds.T_ACC_1X_TG) + " > 0.3");
			}
			if (Math.abs(pds.T_ACC_1Y - pds.T_ACC_1Y_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.T_ACC_1Y - pds.T_ACC_1Y_TG) = "
						+ Math.abs(pds.T_ACC_1Y - pds.T_ACC_1Y_TG) + " > 0.3");
			}
			if (Math.abs(pds.T_ACC_2X - pds.T_ACC_2X_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.T_ACC_2X - pds.T_ACC_2X_TG) = "
						+ Math.abs(pds.T_ACC_2X - pds.T_ACC_2X_TG) + " > 0.3");
			}
			if (Math.abs(pds.T_ACC_3Y - pds.T_ACC_3Y_TG) > 0.3) {
				result = false;
				logger.info(" Run to run ID:" + this.R2R_ID + " PreFilter Math.abs(pds.T_ACC_3Y - pds.T_ACC_3Y_TG) = "
						+ Math.abs(pds.T_ACC_3Y - pds.T_ACC_3Y_TG) + " > 0.3");
			}
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
		
		return result;
	}

	private void initial(ConfigBase cfg) {
		try {
			if (cfg.Get("SampleSize").equals("")) {
				this.SampleSize = 10;
			} else {
				try {
					this.SampleSize = Integer.parseInt(cfg.Get("SampleSize"));
				} catch (Exception e) {
					this.SampleSize = 10;
				}
			}

			if (cfg.Get("WaitCount").equals("")) {
				this.WaitCount = 0;
			} else {
				try {
					this.WaitCount = Integer.parseInt(cfg.Get("WaitCount"));
				} catch (Exception e) {
					this.WaitCount = 0;
				}
			}

			if (cfg.Get("WaitingForConfirm").equals("")) {
				this.WaitingForConfirm = false;
			} else {
				if (cfg.Get("WaitingForConfirm").equals("T")) {
					this.WaitingForConfirm = true;
				} else {
					this.WaitingForConfirm = false;
				}
			}
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
	}

	private void UpdateConfig(Config cfg) {
		try {

			cfg.Update(this.R2R_ID, "WaitCount", String.valueOf(this.WaitCount));

			if (this.WaitingForConfirm == true) {
				cfg.Update(this.R2R_ID, "WaitingForConfirm", "T");
			} else {
				cfg.Update(this.R2R_ID, "WaitingForConfirm", "F");
			}

		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + tools.StackTrace2String(e));
		}
	}
}
