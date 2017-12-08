package com.innolux.R2R.handler;

import java.util.List;
import org.apache.log4j.Logger;

import com.innolux.R2R.bc.BC;
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
	
	private PDSBase _PDS = null;
	
	private String R2R_ID = "";
	private int SampleSize = 0;
	private int WaitCount = 0;
	private int WaitCountSet = 0;
	private boolean WaitingForConfirm = false;
	private PAParamBase currentPdsValues = new PAParamBase();

	public PALR2R(PDSBase pds) {
		this._PDS = pds;
	}

	public void Excute() {

		boolean ResetAll = false;

		PAParamBase GlassValues = new PAParamBase();
		RegulationCollection result = new RegulationCollection();
		this.R2R_ID = this._PDS.Equipment_ID + "_" + this._PDS.SubEquipment_ID;
		try {
			this.initial();

			if (this.WaitingForConfirm) {

				this.WaitCount++;
				if (this.WaitCount >= this.WaitCountSet) {
					this.WaitingForConfirm = false;
					this.WaitCount = 0;
				}

			} else {
				if (!PdsDataConvert(currentPdsValues, this._PDS)) {
					return;
				}
				List<GlassDataBase> GlassDatas = GlassData.LookupForPdsData(this.R2R_ID, this.SampleSize);
				if (GlassDatas.size() != 0) {

					if (GlassDataConvert(GlassValues, GlassDatas.get(0))) {
						if (currentPdsValues.C_OFFSET_X != GlassValues.C_OFFSET_X) {
							ResetAll = true;
						}
						if (currentPdsValues.C_OFFSET_Y != GlassValues.C_OFFSET_Y) {
							ResetAll = true;
						}
						if (currentPdsValues.C_OFFSET_θ != GlassValues.C_OFFSET_θ) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_X != GlassValues.T_OFFSET_X) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_Y != GlassValues.T_OFFSET_Y) {
							ResetAll = true;
						}
						if (currentPdsValues.T_OFFSET_θ != GlassValues.T_OFFSET_θ) {
							ResetAll = true;
						}

						if (ResetAll) {
							logger.info(" Run to run ID:" + this.R2R_ID + " Reset all data: current:"
									+ currentPdsValues.toString());
							logger.info(" Run to run ID:" + this.R2R_ID + " Reset all data: last:"
									+ GlassValues.toString());
							GlassData.DeleteAll(R2R_ID);
						}
					} else {
						return;
					}
				}
				// PreFilter

				if (PreFilter(currentPdsValues)) {
					if (this.WaitingForConfirm && this.WaitCount >= this.WaitCountSet) {
						this.WaitCount++;
					} else {
						// Insert new glass data
						if (AddSample(this._PDS)) {
							GlassDatas = GlassData.LookupForPdsData(this.R2R_ID, this.SampleSize);
							if (GlassDatas.size() == this.SampleSize) {
								result = Caculation(GlassDatas, this.R2R_ID);
								GlassData.DeleteAll(R2R_ID);
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
					return;
				}
			}
			this.UpdateConfig();
			if (result != null) {
				if (result.GetRegulationCount() != 0) {
					BC bc = new BC(_PDS.Equipment_ID);
					
					if (bc.Excute(result)) {
						logger.info("SendToBC successful.");
					} else {
						logger.error("SendToBC fail.");
					}

				} else {
					logger.error("Regulation is empty.");
				}
			} else {
				// logger.debug("No match for any R2R.");
			}
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
		}
		
	}

	

	private RegulationCollection Caculation(List<GlassDataBase> GlassDatas, String R2RID) {
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
		// θ Way
		double C3Y_Avg = 0;
		double C1Y_Avg = 0;
		double C_OFFSET_θ = 0;
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
		// θ Way
		double T3Y_Avg = 0;
		double T1Y_Avg = 0;
		double T_OFFSET_θ = 0;

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
				result.StoreRegulation(4, ToolUtility.demical2Hex(C_OFFSET_X, 0.01,this.R2R_ID));
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
				result.StoreRegulation(5, ToolUtility.demical2Hex(C_OFFSET_Y, 0.01,this.R2R_ID));
				// θ Way
				C_OFFSET_θ = (C3Y_Avg - C1Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " C3Y_Avg:" + C3Y_Avg + " C1Y_Avg:" + C1Y_Avg + " size()*2:"+(double) GlassDatas.size() * (double) 2.0 );
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_θ:" + C_OFFSET_θ);
				if (Math.abs(C3Y_Avg - C1Y_Avg) <= 0.05) {
					C_OFFSET_θ = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				C_OFFSET_θ += currentPdsValues.C_OFFSET_θ;
				result.StoreRegulation(6, ToolUtility.demical2Hex(C_OFFSET_θ, 0.001,this.R2R_ID));
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
				result.StoreRegulation(1, ToolUtility.demical2Hex(T_OFFSET_X, 0.01,this.R2R_ID));
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
				result.StoreRegulation(2, ToolUtility.demical2Hex(T_OFFSET_Y, 0.01,this.R2R_ID));
				// θ Way
				T_OFFSET_θ = (T1Y_Avg - T3Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_θ:" + T_OFFSET_θ);
				if (Math.abs(T1Y_Avg - T3Y_Avg) <= 0.05) {
					T_OFFSET_θ = 0; // If less than target 0.05 ,do nothing.
				} else {
					this.WaitingForConfirm = true;
				}
				T_OFFSET_θ += currentPdsValues.T_OFFSET_θ;
				result.StoreRegulation(3, ToolUtility.demical2Hex(T_OFFSET_θ, 0.001,this.R2R_ID));
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
				//if (Math.abs(C1X2X_Target - C1X2X_Avg) <= 0.5) {
				//	C_OFFSET_X = 0; // If less than target 0.5 ,do nothing.
				//} else {
					this.WaitingForConfirm = true;
				//}
				C_OFFSET_X += currentPdsValues.C_OFFSET_X;
				result.StoreRegulation(4, ToolUtility.demical2Hex(C_OFFSET_X, 0.01,this.R2R_ID));
				// Y Way
				C1Y3Y_Avg = C1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				C_OFFSET_Y = (C1Y3Y_Target - C1Y3Y_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_Y:" + C_OFFSET_Y);
//				if (Math.abs(C1Y3Y_Avg - C1Y3Y_Target) <= 0.5) {
//					C_OFFSET_Y = 0; // If less than target 0.5 ,do nothing.
//				} else {
					this.WaitingForConfirm = true;
				//}
				C_OFFSET_Y += currentPdsValues.C_OFFSET_Y;
				result.StoreRegulation(5, ToolUtility.demical2Hex(C_OFFSET_Y, 0.01,this.R2R_ID));
				// θ Way
				C_OFFSET_θ = (C1Y_Avg - C3Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " C_OFFSET_θ:" + C_OFFSET_θ);
//				if (Math.abs(C3Y_Avg - C1Y_Avg) <= 0.05) {
//					C_OFFSET_θ = 0; // If less than target 0.05 ,do nothing.
//				} else {
					this.WaitingForConfirm = true;
//				}
				C_OFFSET_θ += currentPdsValues.C_OFFSET_θ;
				result.StoreRegulation(6, ToolUtility.demical2Hex(C_OFFSET_θ, 0.001,this.R2R_ID));
				// CF Side end

				// TFT Side begin
				// X Way
				T1X2X_Avg = T1X2X_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_X = (T1X2X_Target - T1X2X_Avg) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_X:" + T_OFFSET_X);
//				if (Math.abs(T1X2X_Target - T1X2X_Avg) <= 0.5) {
//					T_OFFSET_X = 0; // If less than target 0.5 ,do nothing.
//				} else {
					this.WaitingForConfirm = true;
//				}
				T_OFFSET_X += currentPdsValues.T_OFFSET_X;
				result.StoreRegulation(1, ToolUtility.demical2Hex(T_OFFSET_X, 0.01,this.R2R_ID));
				// Y Way
				T1Y3Y_Avg = T1Y3Y_Avg / ((double) GlassDatas.size() * (double) 2.0);
				T_OFFSET_Y = (T1Y3Y_Avg - T1Y3Y_Target) / (double) 2.0;
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_Y:" + T_OFFSET_Y);
//				if (Math.abs(T1Y3Y_Avg - T1Y3Y_Target) <= 0.5) {
//					T_OFFSET_Y = 0; // If less than target 0.5 ,do nothing.
//				} else {
					this.WaitingForConfirm = true;
//				}
				T_OFFSET_Y += currentPdsValues.T_OFFSET_Y;
				result.StoreRegulation(2, ToolUtility.demical2Hex(T_OFFSET_Y, 0.01,this.R2R_ID));
				// θ Way
				T_OFFSET_θ = (T3Y_Avg - T1Y_Avg) / ((double) GlassDatas.size() * (double) 2.0);
				logger.debug(" Run to run ID:" + this.R2R_ID + " T_OFFSET_θ:" + T_OFFSET_θ);
//				if (Math.abs(T1Y_Avg - T3Y_Avg) <= 0.05) {
//					T_OFFSET_θ = 0; // If less than target 0.05 ,do nothing.
//				} else {
					this.WaitingForConfirm = true;
//				}
				T_OFFSET_θ += currentPdsValues.T_OFFSET_θ;
				result.StoreRegulation(3, ToolUtility.demical2Hex(T_OFFSET_θ, 0.001,this.R2R_ID));
				// TFT Side end

				break;
			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		result.toString();
		return result;
	}

	private boolean AddSample(PDSBase pds) {
		boolean result = false;
		try {

			
			GlassData.Append(R2R_ID, pds.Component_ID, "Glass_ID", pds.Component_ID);

			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_1X", pds.GetParameter("C_ACC_1X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_1X_TG", pds.GetParameter("C_ACC_1X_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_1Y", pds.GetParameter("C_ACC_1Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_1Y_TG", pds.GetParameter("C_ACC_1Y_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_2X", pds.GetParameter("C_ACC_2X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_2X_TG", pds.GetParameter("C_ACC_2X_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_3Y", pds.GetParameter("C_ACC_3Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_ACC_3Y_TG", pds.GetParameter("C_ACC_3Y_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_X", pds.GetParameter("C_OFFSET_X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_Y", pds.GetParameter("C_OFFSET_Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "C_OFFSET_θ", pds.GetParameter("C_OFFSET_θ"));

			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_1X", pds.GetParameter("T_ACC_1X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_1X_TG", pds.GetParameter("T_ACC_1X_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_1Y", pds.GetParameter("T_ACC_1Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_1Y_TG", pds.GetParameter("T_ACC_1Y_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_2X", pds.GetParameter("T_ACC_2X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_2X_TG", pds.GetParameter("T_ACC_2X_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_3Y", pds.GetParameter("T_ACC_3Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_ACC_3Y_TG", pds.GetParameter("T_ACC_3Y_TG"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_X", pds.GetParameter("T_OFFSET_X"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_Y", pds.GetParameter("T_OFFSET_Y"));
			GlassData.Append(R2R_ID, pds.Component_ID, "T_OFFSET_θ", pds.GetParameter("T_OFFSET_θ"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " AddSample " + pds.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
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
			GlassValues.C_OFFSET_θ = Double.parseDouble(GlassDatas.GetParameter("C_OFFSET_θ"));

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
			GlassValues.T_OFFSET_θ = Double.parseDouble(GlassDatas.GetParameter("T_OFFSET_θ"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " GlassDataConvert " + GlassValues.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
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
			PdsValues.C_OFFSET_Y = Double.parseDouble(pds.GetParameter("C_OFFSET_Y"));
			PdsValues.C_OFFSET_θ = Double.parseDouble(pds.GetParameter("C_OFFSET_θ"));

			PdsValues.T_ACC_1X = Double.parseDouble(pds.GetParameter("T_ACC_1X"));
			PdsValues.T_ACC_1X_TG = Double.parseDouble(pds.GetParameter("T_ACC_1X_TG"));
			PdsValues.T_ACC_1Y = Double.parseDouble(pds.GetParameter("T_ACC_1Y"));
			PdsValues.T_ACC_1Y_TG = Double.parseDouble(pds.GetParameter("T_ACC_1Y_TG"));
			PdsValues.T_ACC_2X = Double.parseDouble(pds.GetParameter("T_ACC_2X"));
			PdsValues.T_ACC_2X_TG = Double.parseDouble(pds.GetParameter("T_ACC_2X_TG"));
			PdsValues.T_ACC_3Y = Double.parseDouble(pds.GetParameter("T_ACC_3Y"));
			PdsValues.T_ACC_3Y_TG = Double.parseDouble(pds.GetParameter("T_ACC_3Y_TG"));
			PdsValues.T_OFFSET_X = Double.parseDouble(pds.GetParameter("T_OFFSET_X"));
			PdsValues.T_OFFSET_Y = Double.parseDouble(pds.GetParameter("T_OFFSET_Y"));
			PdsValues.T_OFFSET_θ = Double.parseDouble(pds.GetParameter("T_OFFSET_θ"));

			result = true;
			logger.debug(" Run to run ID:" + this.R2R_ID + " PdsDataConvert " + PdsValues.toString());
		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
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
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
		}
		
		return result;
	}

	private void initial() {
		try {
			ConfigBase cfg = Config.Lookup(this.R2R_ID);
			if (cfg.Get("WaitCountSet").equals("")) {
				this.WaitCountSet = 5;
			} else {
				try {
					this.WaitCountSet = Integer.parseInt(cfg.Get("WaitCountSet"));
				} catch (Exception e) {
					this.WaitCountSet = 5;
				}
			}
			
			if (cfg.Get("SampleSize").equals("")) {
				this.SampleSize = 5;
			} else {
				try {
					this.SampleSize = Integer.parseInt(cfg.Get("SampleSize"));
				} catch (Exception e) {
					this.SampleSize = 5;
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
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
		}
	}

	private void UpdateConfig() {
		try {

			Config.Update(this.R2R_ID, "WaitCount", String.valueOf(this.WaitCount));
			Config.Update(this.R2R_ID, "WaitCountSet", String.valueOf(this.WaitCountSet));

			if (this.WaitingForConfirm == true) {
				Config.Update(this.R2R_ID, "WaitingForConfirm", "T");
			} else {
				Config.Update(this.R2R_ID, "WaitingForConfirm", "F");
			}

		} catch (Exception e) {
			logger.error(" Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
		}
	}
}
