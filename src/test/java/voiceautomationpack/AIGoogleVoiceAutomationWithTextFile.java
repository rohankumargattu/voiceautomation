package voiceautomationpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AIGoogleVoiceAutomationWithTextFile
{
	public static void main(String[] args) throws Exception
	{
		//Open text file for reading
		File f1=new File("voiceautomationtestdata.txt");
		FileReader fr=new FileReader(f1);
		BufferedReader br=new BufferedReader(fr);
		//Open text file for writing
		File f2=new File("voiceautomationtestresult.txt");
		FileWriter fw=new FileWriter(f2);
		BufferedWriter bw=new BufferedWriter(fw);
		//Data Driven testing
		ChromeDriver driver=null;
		String l="";
		while((l=br.readLine())!=null)
		{
			try
			{
				//Launch google site and avoid notifications from browser
				System.setProperty("webdriver.chrome.driver","E:\\Automation\\chromedriver\\chromedriver.exe");
				ChromeOptions co=new ChromeOptions();
				co.addArguments("--use-fake-ui-for-media-stream=1");
				driver=new ChromeDriver(co);
				driver.get("http://www.google.com");
				driver.manage().window().maximize();
				WebDriverWait wait=new WebDriverWait(driver,20);
				//Click on Mic icon
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='hb2Smf']")));
				driver.findElement(By.xpath("//*[@class='hb2Smf']")).click();
				Thread.sleep(4000);
				//Convert into voice
				System.setProperty("mbrola.base","E:\\Automation\\mbrola");
				VoiceManager vm=VoiceManager.getInstance();
				Voice v=vm.getVoice("mbrola_us1"); //or kevin16
				v.allocate();
				v.speak(l);
				v.deallocate();
				//validate results
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='All']")));
				String result=driver.getTitle();
				l=l.toLowerCase();
				result=result.toLowerCase();
				if(result.contains(l))
				{
					bw.write("Test passed for - "+l);
					bw.newLine();
				}
				else
				{
					bw.write("Test failed for - "+l);
					bw.newLine();
				}
			}	
			catch(Exception ex)
			{
				bw.write(ex.getMessage());
				bw.newLine();
			}
			//Close site
			driver.close();
		}
		//Save text files
		bw.close();
		fw.close();
		br.close();
		fr.close();
	}
}
