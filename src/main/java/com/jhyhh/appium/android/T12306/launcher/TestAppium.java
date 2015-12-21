package com.jhyhh.appium.android.T12306.launcher;

import io.appium.java_client.android.AndroidDriver;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.jhyhh.appium.AndroidDriverManager;
import com.jhyhh.appium.AndroidDriverWait;
import com.jhyhh.appium.ExpectedCondition;

public class TestAppium {

	public static void main(String[] args) {
		AndroidDriverManager app = new AndroidDriverManager();
		AndroidDriver<WebElement> driver = app
				.getAndroidDriver("LGD857d51963f0");

		app.waitTime(20000);
		// 如果有升级执行升级动作
		WebElement el = new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElementByAndroidUIAutomator("new UiSelector().text(\"升级\")");
					}
				});
		el.click();

		new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"温馨提示\")");
					}
				});
		// 等待加载完成后点目的站文本框，选择目的站。
		List<WebElement> editTexts = new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(AndroidDriver d) {
						return d.findElementsByClassName("android.widget.EditText");
					}
				});
		editTexts.get(1).click();
		// 选择车站列表Tab
		List<WebElement> mudiButtons = new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(AndroidDriver d) {
						return d.findElementsByClassName("android.widget.Button");
					}
				});
		mudiButtons.get(3).click();
		app.waitTime(5000);
		// 切换到J 开头的List
		new AndroidDriverWait(driver, 60).until(
				new ExpectedCondition<WebElement>() {
					public WebElement apply(AndroidDriver d) {
						return d.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"j\")");
					}
				}).click();
		// 根据下标找到佳木斯view
		new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(AndroidDriver d) {
						return d.findElementsByClassName("android.view.View");
					}
				}).get(9).click();

		List<WebElement> buttons = new AndroidDriverWait(driver, 60)
				.until(new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(AndroidDriver d) {
						return d.findElementsByClassName("android.widget.Button");
					}

				});
		buttons.get(buttons.size() - 1).click();
		System.out.println("end");
	}

}