package com;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGmail {

	String driverPath = "driver\\chromedriver.exe";
	WebDriver driver;
	LoginPage login;
	ComposeAndSendEmail email;
	String username;
	String pass;
	
	@BeforeTest
	public void setup() throws IOException {
		String path=System.getProperty("user.dir")+"\\Resource";
		System.out.println(path);
		File file=new File(path+"\\TestData.properties");
		FileInputStream fis=new FileInputStream(file);
		Properties prop=new Properties();
		prop.load(fis);
		username=prop.getProperty("EmailUsername");
		pass=prop.getProperty("EmailPass");
		System.setProperty("webdriver.chrome.driver", driverPath);	
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://accounts.google.com/AccountChooser/identifier?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&flowName=GlifWebSignIn&flowEntry=AccountChooser");
		driver.manage().window().maximize();
	}

	@Test(priority = 0)
	public void login() throws InterruptedException, AWTException {
		login = new LoginPage(driver);
		login.loginToGmail(username,pass);
		login.executeAssert();
		email = new ComposeAndSendEmail(driver);
		email.ComposeGmail("abcdemoautomation@gmail.com", "Automation", "TestDemo");
		email.sendEmail();
		email.executeAssert();
	}

}