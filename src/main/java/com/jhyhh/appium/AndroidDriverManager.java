package com.jhyhh.appium;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jhyhh.appium.utils.AppiumUtils;
import com.jhyhh.appium.utils.PropertyManager;

public class AndroidDriverManager {

	private String apkName = "";
	private String deviceName = "";

	private final String S = File.separator;
	private AndroidDriver<WebElement> driver = null;

	public AndroidDriverManager() {
		apkName = PropertyManager.getInstance().getString("APK_NAME");
	}

	public AndroidDriver<WebElement> getAndroidDriver(String deviceName) {
		String remoteAddress = PropertyManager.getInstance().getString(
				"APPIUM_SERVER_URL");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		return getAndroidDriver(remoteAddress, capabilities, apkName,
				deviceName);
	}

	public AndroidDriver<WebElement> getAndroidDriver(String remoteAddress,
			String apkName, String deviceName) {
		return getAndroidDriver(remoteAddress, apkName, deviceName);
	}

	public AndroidDriver<WebElement> getAndroidDriver(String remoteAddress,
			DesiredCapabilities capabilities, String deviceName) {
		return getAndroidDriver(remoteAddress, capabilities, apkName,
				deviceName);
	}

	public AndroidDriver<WebElement> getAndroidDriver(String remoteAddress,
			DesiredCapabilities capabilities, String apkName, String deviceName) {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		File app = new File(appDir, apkName);
		this.apkName = apkName;
		this.deviceName = deviceName;
		DesiredCapabilities appcapabilities = new DesiredCapabilities();
		appcapabilities.setCapability("deviceName", deviceName);// "21e6500a"
		appcapabilities.setCapability("app", app.getAbsolutePath());
		String platformVersion = PropertyManager.getInstance().getString(
				"PLATFORM_VERSION");
		appcapabilities.setCapability("platformVersion", platformVersion);
		String appPackage = PropertyManager.getInstance().getString(
				"APP_PACKAGE");
		appcapabilities.setCapability("appPackage", appPackage);
		String appActivity = PropertyManager.getInstance().getString(
				"APP_ACTIVITY");
		appcapabilities.setCapability("appActivity", appActivity);
		boolean autoLaunch = PropertyManager.getInstance().getBoolean(
				"AUTO_LAUNCH", false);
		appcapabilities.setCapability("autoLaunch", autoLaunch); 
		appcapabilities.setCapability("automationName", "Appium");
		appcapabilities.setCapability("useKeystore", false);
		appcapabilities.setCapability("noSign", true);
		appcapabilities.merge(capabilities);
		try {
			driver = new AndroidDriver<WebElement>(new URL(remoteAddress),
					appcapabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * 模拟home键
	 */
	public void goHome() {
		driver.sendKeyEvent(AndroidKeyCode.HOME);
	}

	/**
	 * 获取当前屏幕截图，把截图文件放到 当前目录下的 screenshots文件夹下，以deviceName细分目录
	 */
	public void screenshots() {
		File screen = driver.getScreenshotAs(OutputType.FILE);
		File screenFile = new File("screenshots" + S + deviceName + S
				+ AppiumUtils.getPNGFileName());
		try {
			FileUtils.copyFile(screen, screenFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNetworkStatus() {
		int status = driver.getNetworkConnection().value;
		return status;
	}

	public void setNetworkStatus(int status) {
		driver.setNetworkConnection(new NetworkConnectionSetting(status));
	}

	public void setNetworkStatus() {
		driver.setNetworkConnection(new NetworkConnectionSetting(false, true,
				false));
	}

	/**
	 * 将应用sourceAppName 移到到 targetAppName的位置
	 * 
	 * @param sourceAppName
	 * @param targetAppName
	 */
	public void moveApp(String sourceAppName, String targetAppName) {
		new TouchAction(driver)
				.longPress(driver.findElementByName(sourceAppName))
				.moveTo(driver.findElementByName(targetAppName)).release()
				.perform();
	}

	/**
	 * 获取当前界面的activity,可用于断言是否跳转到预期的activity
	 * 
	 * @return
	 */
	public String currentActivity() {
		return driver.currentActivity();
	}

	/**
	 * 判断当前的activity是不是预期的activityName
	 * 
	 * @param activityName
	 * @return
	 */
	public boolean isCurrentActivity(String activityName) {
		if (StringUtils.isEmpty(activityName)) {
			return false;
		}
		String currentName = driver.currentActivity();
		if (StringUtils.isEmpty(currentName)) {
			return false;
		}
		return activityName.equals(currentName);
	}

	/**
	 * 打开通知栏
	 */
	public void openNotifications() {
		driver.openNotifications();
	}

	public void waitTime(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
