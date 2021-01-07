package voiceautomationpack;

import java.util.Scanner;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AITextToSpeechmbrola
{
	public static void main(String[] args) throws Exception
	{
		//Take data from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter text to be converted into voice");
		String text=sc.nextLine();
		sc.close();
		//Register Voice Library
		System.setProperty("mbrola.base","E:\\Automation\\mbrola");
		//Choose Voice
		VoiceManager vm=VoiceManager.getInstance();
		Voice v=vm.getVoice("mbrola_us1"); //or kevin16
		//Convert text to voice
		v.allocate();	//Take Speaker into control
		v.speak(text);	//Generate given text into voice
		v.deallocate();	//Release speakers and mics
	}
}
