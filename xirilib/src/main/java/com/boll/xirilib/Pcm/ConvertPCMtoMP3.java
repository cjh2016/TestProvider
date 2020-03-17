package com.boll.xirilib.Pcm;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ConvertPCMtoMP3 {
	public void convertAudioFiles(String pcmFile, String mp3File)
			throws Exception {
		FileInputStream fis = new FileInputStream(pcmFile);
		FileOutputStream fos = new FileOutputStream(mp3File);
		byte[] arrayOfByte = new byte[4096];
//		int j = fis.read(arrayOfByte);		
//		int i = 0;
//		while (j != -1) {
//			i += j;
//			j = fis.read(arrayOfByte);
//		}
//		fis.close();
		int len = fis.available();
		WaveHeader waveHeader = new WaveHeader();
		waveHeader.fileLength = (len + 36);
		waveHeader.FmtHdrLeth = 16;
		waveHeader.BitsPerSample = 16;
		waveHeader.Channels = 1;
		waveHeader.FormatTag = 1;
		waveHeader.SamplesPerSec = 16000;
		waveHeader.BlockAlign = ((short) (waveHeader.Channels
				* waveHeader.BitsPerSample / 8));
		waveHeader.AvgBytesPerSec = (waveHeader.BlockAlign * waveHeader.SamplesPerSec);
		waveHeader.DataHdrLeth = len;
		byte[] header = waveHeader.getHeader();
		fos.write(header, 0, header.length);
		
		while((len = fis.read(arrayOfByte)) != -1) {
			fos.write(arrayOfByte, 0, len);
		}
		fos.flush();
		fos.close();
		fis.close();
		System.out.println("Convert OK!");
	}
}