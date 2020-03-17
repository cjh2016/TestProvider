package com.boll.xirilib.Pcm;


import java.util.logging.Logger;



public class Pcm2Mp3 {

	private static Logger log = Logger.getLogger("Pcm2Mp3");
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		pcm2Mp3("e:\\android_develop\\bak\\tmp\\xxx\\follow.pcm", "e:\\android_develop\\bak\\tmp\\xxx\\follow.mp3");
//	}
	
	
	public static void pcm2Mp3(String pcmFile, String mp3File) {		
		ConvertPCMtoMP3 convertPCMtoMP3 = new ConvertPCMtoMP3();
		try {
			convertPCMtoMP3.convertAudioFiles(pcmFile, mp3File);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
