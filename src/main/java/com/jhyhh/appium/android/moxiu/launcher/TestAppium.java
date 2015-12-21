package com.jhyhh.appium.android.moxiu.launcher;

import io.appium.java_client.android.AndroidDriver;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.jhyhh.appium.AndroidADBUtils;
import com.jhyhh.appium.AndroidDriverManager;
import com.jhyhh.appium.AndroidDriverWait;
import com.jhyhh.appium.ExpectedCondition;
import com.jhyhh.appium.logger.LogWriter;

public class TestAppium {

	private Logger logger = Logger.getLogger(TestAppium.class);
	private AndroidDriverManager app;
	private AndroidDriver<WebElement> driver;
	private String deviceName;
	public static void main(String[] args) {
		new TestAppium().testIng();
	}

	public void testIng() {

		List<String> devices = AndroidADBUtils.getDevices();
		for (String device : devices) {
			deviceName  = device;
			LogWriter.info(logger, "开始测试设备" + device
					+ "================================");
			try {
				runing();
			} catch (UnreachableBrowserException ex) {
				LogWriter.error(logger, "测试异常: " + device, ex);
			}
			LogWriter.info(logger, "结束测试设备" + device
					+ "================================");
		}
	}

	private void runing() {
		app = new AndroidDriverManager();
		driver = app.getAndroidDriver(deviceName);
		// 启动魔秀桌面
		// driver.startActivity("com.moxiu.launcher", ".Launcher");
		driver.launchApp();

		app.waitTime(14000);
		WebElement el = new AndroidDriverWait(driver)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElement(By
								.id("com.moxiu.launcher:id/mx_set_wallpaper_left"));
					}
				});
		LogWriter.info(logger, deviceName + ":初始化引导页 选择第一个项。。。。。");
		el.click();
		//
		new AndroidDriverWait(driver).until(
				new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElementByAndroidUIAutomator("new UiSelector().text(\"魔秀主题\")");
					}
				}).click();
		LogWriter.info(logger, deviceName + "：查找魔秀主题图标并点击......");

		int netWorkStatus = app.getNetworkStatus();
		boolean hasNetWork = (netWorkStatus != 2 && netWorkStatus != 4) ? false
				: true;
		if (hasNetWork) {
			LogWriter.info(logger, deviceName + ":在有网络连接的情况下，检查页面加载是否正常......");
			hasNetWork();
		} else {
			LogWriter.info(logger, deviceName
					+ ":没有网络连接的情况下，查看是否会跳到我的主题页面，切换到页面后找个主题更换......");
			noNetWork();
		}
		driver.quit();
		// 锁定屏幕
		// driver.lockScreen(2);
	}

	private void hasNetWork() {

	}

	private void noNetWork() {
		WebElement el = new AndroidDriverWait(driver).until(
				new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElementByAndroidUIAutomator("new UiSelector().text(\"我的主题\")");
					}
				});
		if(el==null){ 
			LogWriter.info(logger, deviceName+ ":没有找到我的主题按钮，判断加载失败。。。。。。");
			return ;
		}
		
		el.click();
		
	}
}