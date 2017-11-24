package com.example.humencheckworkattendance.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionUtils {
	
	public final static int PERMISSION_GROUP_CALENDAR = 2015091400;
	public final static int PERMISSION_GROUP_CAMERA = 2015091401;
	public final static int PERMISSION_GROUP_CONTACTS = 2015091402;
	public final static int PERMISSION_GROUP_LOCATION = 2015091403;
	public final static int PERMISSION_GROUP_MICROPHONE = 2015091404;
	public final static int PERMISSION_GROUP_PHONE = 2015091405;
	public final static int PERMISSION_GROUP_SENSORS = 2015091406;
	public final static int PERMISSION_GROUP_SMS = 2015091407;
	public final static int PERMISSION_GROUP_STORAGE = 2015091408;
	public final static int PERMISSION_GROUP_STORAGE_WRITE = 2015091409;
	
	
	/**
	 * 日历组权限
	 * @param context
	 * @return
	 */
	public static boolean grantCalendarGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.READ_CALENDAR)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, PERMISSION_GROUP_CALENDAR);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * Camera组权限
	 * @param context
	 * @return
	 */
	public static boolean grantCameraGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_GROUP_CAMERA);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * 联系人组权限
	 * @param context
	 * @return
	 */
	public static boolean grantContactsGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_GROUP_CONTACTS);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * 位置组权限
	 * @param context
	 * @return
	 */
	public static boolean grantLocationGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GROUP_LOCATION);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * 录音组权限
	 * @param context
	 * @return
	 */
	public static boolean grantMicrophoneGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_GROUP_MICROPHONE);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * 电话组权限
	 * @param context
	 * @return
	 */
	public static boolean grantPhoneGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_GROUP_PHONE);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	
	/**
	 * 重力感应组权限
	 * @param context
	 * @return
	 */
	public static boolean grantSensorsGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.BODY_SENSORS)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.BODY_SENSORS},PERMISSION_GROUP_SENSORS);
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * 短信组权限
	 * @param context
	 * @return
	 */
	public static boolean grantSmsGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_GROUP_SMS);
				return false;
			}
			return true;
		}else{
			return true;
		}
	}
	

	/**
	 * sd卡组读权限
	 * @param context
	 * @return
	 */
	public static boolean grantStorageGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_GROUP_STORAGE);
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * sd卡组写权限
	 * @param context
	 * @return
	 */
	public static boolean grantStorageWriteGroup(Activity context){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
			if(context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
				context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_GROUP_STORAGE_WRITE);
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	
	
	
}

