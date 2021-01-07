package voiceautomationpack;

import java.util.Scanner;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AITextToSpeech 
{
	public static void main(String[] args) throws Exception
	{
		//Take data from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a word to convert into voice");
		String text=sc.nextLine();
		sc.close();
		//Register Voice Library
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		//Choose Voice
		VoiceManager vm=VoiceManager.getInstance();
		Voice v=vm.getVoice("kevin"); //kevin16
		v.allocate();	//Take Speaker into control
		v.speak(text);	//Generate given text into voice
		v.deallocate();	//Release speakers and mics
	}
}
