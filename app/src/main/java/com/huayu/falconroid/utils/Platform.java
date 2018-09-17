package com.huayu.falconroid.utils;

import android.util.Log;

import com.huayu.io.EMgpio;


public class Platform {
	private static final int PIN_BARCODE_POWER = 85;
	private static final int PIN_BARCODE_CTS = 84;
	private static final int PIN_BARCODE_TRIG = 86;
	
	private static final int PIN_RFID_POWER = 70;
	private static final int PIN_RFID_BOOT = 76;
	   
	private static final int PIN_UHF_POWER = 76;
	
	
	private static final int PIN_TAG_POWER = 70;
	
	private static boolean mIoInit = false;
	
	
	private static String strPhoneModel_PD805 = "PD805";
	private static String strPhoneModel_PD805S = "PD805S";
	private static String strPhoneModel_PD805S_ = "PD805S_";
	private static String strPhoneModel_PE900S = "pe900S";
	private static String strPhoneModel_PE900_4G = "PE900-4G";
	
	
	public static int Power_125K;
	public static int Port_125K;
	
	static {
		
		if (android.os.Build.MODEL.equalsIgnoreCase(strPhoneModel_PD805)) {
			Power_125K = 70;
			Port_125K = 1;
		}else if(android.os.Build.MODEL.trim().equalsIgnoreCase(strPhoneModel_PD805S)) {
			Power_125K = 14;
			Port_125K = 0;
		}else if(android.os.Build.MODEL.trim().equalsIgnoreCase(strPhoneModel_PE900S)) {
			Power_125K = 139;
			Port_125K = 1;
		}else if(android.os.Build.MODEL.trim().toLowerCase().contains(strPhoneModel_PD805S_.toLowerCase())) {
			Power_125K = 98;
			Port_125K = 0;
		}else if(android.os.Build.MODEL.trim().equalsIgnoreCase(strPhoneModel_PE900_4G)) {
			Power_125K = 99;
			Port_125K = 0;
		}
	}
		
	public static void initIO(){
		if(!mIoInit){			
			if(EMgpio.GPIOInit()){
				Log.d("test", "InitIO succ");
				mIoInit = true;
				return;
			}else{
				Log.d("test", "InitIO fail");
				return;
			}
		}
	}
	
	public static void deInitIO(){
		if(mIoInit){
			if(EMgpio.GPIOUnInit()){
				mIoInit = false;
			}
		}
	}
	
	//barcode io
/*	public static void openBarcodePower(){
		
		EMgpio.SetGpioOutput(PIN_BARCODE_POWER);
		EMgpio.SetGpioOutput(PIN_BARCODE_CTS);
		EMgpio.SetGpioOutput(PIN_BARCODE_TRIG);

		EMgpio.SetGpioDataHigh(PIN_BARCODE_POWER);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_CTS);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_TRIG);
	}
	
	public static void closeBarcodePower(){
		EMgpio.SetGpioOutput(PIN_BARCODE_POWER);
		EMgpio.SetGpioOutput(PIN_BARCODE_CTS);
		EMgpio.SetGpioOutput(PIN_BARCODE_TRIG);

		EMgpio.SetGpioDataLow(PIN_BARCODE_POWER);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_CTS);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_TRIG);
		
	}
	
	public static void trigOn(){

		EMgpio.SetGpioOutput(PIN_BARCODE_TRIG);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_TRIG);

		for(int i=0; i<100000;i++){;}
	
		EMgpio.SetGpioDataLow(PIN_BARCODE_TRIG);
	}
	
	public static void trigOff(){
		EMgpio.SetGpioOutput(PIN_BARCODE_TRIG);
		EMgpio.SetGpioDataHigh(PIN_BARCODE_TRIG);		
	}
	
	//boot mode
	//boot mode
	private static void setBoot(){
		EMgpio.SetGpioOutput(PIN_RFID_BOOT);
		EMgpio.SetGpioDataLow(PIN_RFID_BOOT);
	}
	
	private static void cleanBoot(){
		EMgpio.SetGpioOutput(PIN_RFID_BOOT);
		EMgpio.SetGpioDataHigh(PIN_RFID_BOOT);
	}

	public static void enterBootMode(){
		setBoot();
		EMgpio.SetGpioOutput(PIN_RFID_POWER);
		EMgpio.SetGpioDataHigh(PIN_RFID_POWER);
	}
	
	//tag125k io
	public static void openTagPower(){		
		EMgpio.SetGpioOutput(PIN_TAG_POWER);
		EMgpio.SetGpioDataHigh(PIN_TAG_POWER);
	}
	
	public static void closeTagPower(){
		EMgpio.SetGpioOutput(PIN_TAG_POWER);
		EMgpio.SetGpioDataLow(PIN_TAG_POWER);
	}
	
	//rfid io
	public static void openRfidPower(){
		cleanBoot();
		
		EMgpio.SetGpioOutput(PIN_RFID_POWER);
		EMgpio.SetGpioDataHigh(PIN_RFID_POWER);		
	}
	
	public static void closeRfidPower(){
		EMgpio.SetGpioOutput(PIN_RFID_POWER);
		EMgpio.SetGpioDataLow(PIN_RFID_POWER);
		cleanBoot();
	}
		
	//uhf io
	public static void openUhfPower(){
		EMgpio.SetGpioOutput(PIN_UHF_POWER);
		EMgpio.SetGpioDataHigh(PIN_UHF_POWER);
	}
	
	public static void closeUhfPower(){
		EMgpio.SetGpioOutput(PIN_UHF_POWER);
		EMgpio.SetGpioDataLow(PIN_UHF_POWER);
	}  */
	
	public static boolean SetGpioInput(int gpio_index){
		return EMgpio.SetGpioInput(gpio_index);
	}
	
	public static boolean SetGpioOutput(int gpio_index){
		return EMgpio.SetGpioOutput(gpio_index);
	}
	
	public static boolean SetGpioDataHigh(int gpio_index){
		return EMgpio.SetGpioDataHigh(gpio_index);
	}
	
	public static boolean SetGpioDataLow(int gpio_index){
		return EMgpio.SetGpioDataLow(gpio_index);
	}  
}
