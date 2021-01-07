package voiceautomationpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AIGoogleVoiceAutomationWithExcel
{
	public static void main(String[] args) throws Exception
	{
		//Connect to excel file
		File f=new File("voiceautomation.xlsx");
		//Open excel file for reading
		FileInputStream fi=new FileInputStream(f);
		//Take file as excel
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		//Create results column
		sh.getRow(0).createCell(nouc).setCellValue("Result");
		ChromeDriver driver=null;
		for(int i=1;i<nour;i++)
		{
			try
			{
				DataFormatter df=new DataFormatter();
				String country_name=df.formatCellValue(sh.getRow(i).getCell(0));
				//Launch google site and avoid notifications from browser
				System.setProperty("webdriver.chrome.driver","E:\\Automation\\chromedriver\\chromedriver.exe");
				ChromeOptions co=new ChromeOptions();
				co.addArguments("--use-fake-ui-for-media-stream=1");
				driver=new ChromeDriver(co);
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
				v.allocate();	//Take Speaker into control
				v.speak(country_name);	//Generate given text into voice
				v.deallocate();	//Release speakers and mics
				//validate results
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='All']")));
				String result=driver.getTitle();
				country_name=country_name.toLowerCase();
				result=result.toLowerCase();
				if(result.contains(country_name))
				{
					sh.getRow(i).createCell(nouc).setCellValue("Test passed");
				}
				else
				{
					sh.getRow(i).createCell(nouc).setCellValue("Test failed");
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			//Close site
			driver.close();
		}
		//Save and close excel
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fi.close();
		fo.close();
		wb.close();
	}
}
