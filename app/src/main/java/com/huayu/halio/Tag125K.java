package com.huayu.halio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.util.Log;

import com.huayu.falconroid.utils.FalconException;
import com.huayu.falconroid.utils.Platform;
import com.huayu.io.EMgpio;
import com.huayu.io.SerialPort;


//125K和134.2K的区别
//硬件模块的区别:  134.2k模块没有晶振       发送命令时，需要发一次AA，收一次数据；再发一次AA，在收一次。。。
//            125k模块有晶振     只需要发送一次命令AA，就可以一直接受数据

public class Tag125K {
	// TAG 125K
	private SerialPort mSerialPort = null;
	protected OutputStream mOutputStream = null;
	private InputStream mInputStream = null;


	byte[] mBuffer = new byte[1024];
	int mBufferLength = 0;



	static {
		EMgpio.GPIOInit();
	}

	public void openTag125KPower() {
		Platform.SetGpioOutput(Platform.Power_125K);
		Platform.SetGpioDataHigh(Platform.Power_125K);
	}

	public void closeTag125KPower() {
		Platform.SetGpioOutput(Platform.Power_125K);
		Platform.SetGpioDataLow(Platform.Power_125K);
	}

	public void openTag125KPort() throws FalconException {
		deInitComm();

		initComm(Platform.Port_125K, 19200);

	}

	public void closeTag125KPort() {
		deInitComm();
		Log.d("test", "closeTag125KPort");
	}

	public byte[] readTag125KUid() throws IOException{
		int iAvailable = 0;

//		if(mOutputStream == null){
//			throw new FalconException("outputstream null");
//		}
		byte[] send = {(byte) 0xAA};
		mOutputStream.write(send);
		try {
			Thread.sleep(300);   //Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	/*	if(mInputStream == null){
			throw new FalconException("mInputStream null");
		} */

		while(iAvailable <= 0) //修改
		{
			iAvailable = mInputStream.available();
			Log.d("test", "返回   "+iAvailable+"   "+mInputStream);
		}


	/*	  早期版本
	   iAvailable = mInputStream.available();
	   if(iAvailable <= 0){
			Log.d("test", "返回   "+iAvailable+"   "+mInputStream);
			return null;
		}  */

		mBufferLength = mInputStream.read(mBuffer, 0,iAvailable);
		Log.d("test", "返回length   "+mBufferLength);

		if (mBufferLength <= 0){
			Log.d("test", "mBufferLength <= 0");
			return null;
		}

		//read again
		iAvailable = mInputStream.available();

		if(iAvailable > 0){
			int iReaded = mInputStream.read(mBuffer, mBufferLength,iAvailable);

			if(iReaded > 0){
				mBufferLength += iReaded;
			}
		}


		//check crc
		if(mBufferLength < 6)
		{
			Log.d("test", "mBufferLength < 6");

			send[0] = (byte) 0xBB;
			mOutputStream.write(send);
			try {
				Thread.sleep(300);   //Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}else {  //crc

			if((mBuffer[0]^mBuffer[1]^mBuffer[2]^mBuffer[3]^mBuffer[4]) == mBuffer[5])
			{
				mBufferLength =6;  //只返回前 6 个
				byte[] bUid = new byte[mBufferLength];

				for(int i=0; i<mBufferLength; i++){
					bUid[i] = mBuffer[i];
				}

				return bUid;
			}else {
				Log.d("RfidQueryDemo", "crc fail");
				return null;
			}
		}


	}

	// open comm port: baudrate+port
	private void initComm(int iPort, int iBaudrate) throws FalconException {
		try {
			// mSerialPort = new SerialPort(new File("/dev/ttyMT1"),115200, 0);
			mSerialPort = new SerialPort(new File("/dev/ttyMT" + iPort),
					iBaudrate, 0);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
		} catch (SecurityException e) {
			throw new FalconException("SecurityException:" + e.getMessage());
		} catch (IOException e) {
			throw new FalconException("IOException:" + e.getMessage());
		} catch (InvalidParameterException e) {
			throw new FalconException("InvalidParameterException:"
					+ e.getMessage());
		}
	}



	// close comm port
	private void deInitComm() {
		if (mOutputStream != null) {
			try {
				mOutputStream.close();
			} catch (java.io.IOException ioe) {
			}

			//	mOutputStream = null;
		}

		if (mInputStream != null) {
			try {
				mInputStream.close();
			} catch (java.io.IOException ioe) {
			}
			//	mInputStream = null;
		}

		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}
}
