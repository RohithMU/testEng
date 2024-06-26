package Third;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ThirdScript 
{
public static WebDriver driver;
@BeforeMethod
public void Launchbrowser()
{
	driver =new ChromeDriver();
	driver.get("https://demowebshop.tricentis.com/");
}
@Test(priority=1)
public void register()
{
	SoftAssert softassert =new SoftAssert();
	driver.findElement(By.xpath("//a[@class='ico-register']")).click();
	driver.findElement(By.xpath("//input[@id='gender-male']")).click();
	driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Rohith");
	driver.findElement(By.xpath("//input[@id='LastName']")).sendKeys("M U");
	driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("rohithpatilmu@gmail.com");
	driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Rohithmu@123");
	driver.findElement(By.xpath("//input[@id='ConfirmPassword']")).sendKeys("Rohithmu@123");
	driver.findElement(By.xpath("//input[@id='register-button']")).click();
	Reporter.log("Successfully Registerd");
	String expectedText = "The specified email already exists";
	String originalText = driver.findElement(By.xpath("//*[.='The specified email already exists']")).getText();
	softassert.assertEquals(originalText, expectedText,"Text of the error message do not match");
}
@Test(priority=2)
public void login()
{
	driver.findElement(By.xpath("//a[@class='ico-login']")).click();
	driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("rohithpatilmu@gmail.com");
	driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Rohithmu@123");
	driver.findElement(By.xpath("//input[@id='RememberMe']")).click();
	driver.findElement(By.xpath("//input[@value='Log in']")).click();
	Reporter.log("Successfully Logged In");
	String expectedText = "rohithpatilmu@gmail.com";
	String originalText = driver.findElement(By.xpath("//a[.='rohithpatilmu@gmail.com']")).getText();
    Assert.assertEquals(originalText, expectedText,"Text of the email do not match");
}
@Test(priority=3)
public void logout()
{
	login();
	driver.findElement(By.xpath("//a[@class='ico-logout']")).click();
	Reporter.log("Successfully Logged Out");
}
static class Screenshot
{
	public static void getphoto(WebDriver driver) throws IOException
	{
		String photo="./photos/";
		Date d=new Date();
		String d1=d.toString();
		String d2=d1.replaceAll("-", ":");
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File("C:/Users/rohit/OneDrive/Desktop/Screenshots/.jpeg");
		FileHandler.copy(src, dest);
		
	}
}
@AfterMethod
public void closebrowser(ITestResult res) throws IOException
{
	if(ITestResult.FAILURE==res.getStatus())
	{
		Screenshot.getphoto(driver);
	}
	driver.close();
}
}
