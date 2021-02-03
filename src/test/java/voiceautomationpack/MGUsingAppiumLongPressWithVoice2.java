package voiceautomationpack;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.ElementOption;

public class MGUsingAppiumLongPressWithVoice2 
{
	public static void main(String[] args) throws Exception
	{
		//Create an Array of names
		String[] names= {"Gheethaaaa","Priyaa","hemajaa","Rohan","ShwethaPraneeth","Soochi","Raji","Anusha Yaskhi","Ravi","Farheeenn","Rajshekar","Swethaa"};
		//Start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723\"");
		//Get address of appium Server
		URL u=new URL("http://127.0.0.1:4723/wd/hub");
		//Details of app and device(AVD)
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","ce081718334a5b0b05");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","8.0.0");
		dc.setCapability("appPackage","com.vodqareactnative");
		dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		//Create driver object
		AndroidDriver driver;
		while(2>1)
		{
			try
			{
				driver=new AndroidDriver(u,dc);
				break;
			}
			catch(Exception ex)
			{
			}
		}
		//App Automation
		try
		{
			WebDriverWait wait=new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='LOG IN']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Long Press']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Long Press Me']")));
			MobileElement ele=(MobileElement) driver.findElement(By.xpath("//*[@text='Long Press Me']"));
			TouchAction ta=new TouchAction(driver);
			ta.longPress(ElementOption.element(ele)).perform();
			
			//Validations
			try
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='you pressed me hard :P']")));
				for(int i=0;i<names.length;i++)
				{
					String text=driver.findElement(By.xpath("//*[@text='you pressed me hard :P']")).getText();
					text=text.replace(" :P",names[i]);
					//Register Voice Library
					System.setProperty("mbrola.base","E:\\Automation\\mbrola");
					//Choose Voice
					VoiceManager vm=VoiceManager.getInstance();
					Voice v=vm.getVoice("mbrola_us1"); //or kevin16
					//Convert text to voice
					v.allocate();	//Take Speaker into control
					v.speak(text);	//Generate given text into voice
					v.deallocate();	//Release speakers and mics
					Thread.sleep(1000);
				}
				
				if(driver.findElement(By.xpath("//*[@text='you pressed me hard :P']")).isDisplayed())
				{
					System.out.println("Long Press test passed");
					driver.findElement(By.xpath("//*[@text='OK']")).click();
				}
			}
			catch(Exception e)
			{
				System.out.println("Long Press test failed");
				SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
				Date dt=new Date();
				String fname=sf.format(dt);
				File src=driver.getScreenshotAs(OutputType.FILE);
				File dest=new File(fname+".png");
				FileHandler.copy(src, dest);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		//Close app
		driver.closeApp();
		//Stop appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
