package voiceautomationpack;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AIGoogleVoiceAutomation 
{
	public static void main(String[] args) throws Exception
	{
		//Get data to be searched as voice in google site
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter text to be converted into voice");
		String text=sc.nextLine();
		sc.close();
		//Open google site and avoid notifications from browser
		System.setProperty("webdriver.chrome.driver","E:\\Automation\\chromedriver\\chromedriver.exe");
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--use-fake-ui-for-media-stream=1");
		ChromeDriver driver=new ChromeDriver(co);
		driver.get("http://www.google.com");
		driver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(driver,20);
		//Click on Mic icon
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='Search by voice']")));
		driver.findElement(By.xpath("//*[@aria-label='Search by voice']")).click();
		Thread.sleep(4000);
		//Convert into voice
		System.setProperty("mbrola.base","E:\\Automation\\mbrola");
		VoiceManager vm=VoiceManager.getInstance();
		Voice v=vm.getVoice("mbrola_us1"); //or kevin16
		v.allocate();
		v.speak(text);
		v.deallocate();
		//validate results
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='All']")));
		String result=driver.getTitle();
		text=text.toLowerCase();
		result=result.toLowerCase();
		if(result.contains(text))
		{
			System.out.println("Test Passed");
		}
		else
		{
			System.out.println("Test Failed");
		}
		//Close site
		driver.close();
	}
}
