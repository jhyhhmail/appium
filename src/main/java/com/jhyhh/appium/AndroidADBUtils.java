package com.jhyhh.appium;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class AndroidADBUtils {

	synchronized static public List<String> getDevices() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("adb devices");
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(process.getInputStream());
		Scanner sc = new Scanner(isr);
		List<String> devices = new ArrayList<String>();
		while (sc.hasNext()) {
			String device = sc.nextLine();
			if(StringUtils.isEmpty(device)){
				continue;
			} 
			if(device.endsWith("device")){
				device = device.replace("device", "").trim();
				devices.add(device);
			}
		}
		sc.close();
		return devices;
	}

	public static void main(String[] args) {
		System.out.println(AndroidADBUtils.getDevices());
	}
}
