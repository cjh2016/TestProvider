package com.boll.xirilib.Pcm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WaveHeader {
	public int AvgBytesPerSec;
	public short BitsPerSample;
	public short BlockAlign;
	public short Channels;
	public char[] DataHdrID = { 100, 97, 116, 97 };
	public int DataHdrLeth;
	public char[] FmtHdrID = { 102, 109, 116, 32 };
	public int FmtHdrLeth;
	public short FormatTag;
	public int SamplesPerSec;
	public final char[] fileID = { 82, 73, 70, 70 };
	public int fileLength;
	public char[] wavTag = { 87, 65, 86, 69 };

	private void WriteChar(ByteArrayOutputStream paramByteArrayOutputStream,
			char[] paramArrayOfChar) {
		int i = 0;
		while (i < paramArrayOfChar.length) {
			paramByteArrayOutputStream.write(paramArrayOfChar[i]);
			i += 1;
		}
	}

	private void WriteInt(ByteArrayOutputStream paramByteArrayOutputStream,
			int paramInt) throws IOException {
		byte i = (byte) (paramInt >> 24);
		byte j = (byte) (paramInt << 8 >> 24);
		byte k = (byte) (paramInt << 16 >> 24);
		paramByteArrayOutputStream.write(new byte[] {
				(byte) (paramInt << 24 >> 24), k, j, i });
	}

	private void WriteShort(ByteArrayOutputStream paramByteArrayOutputStream,
			int paramInt) throws IOException {
		byte i = (byte) (paramInt << 16 >> 24);
		paramByteArrayOutputStream.write(new byte[] {
				(byte) (paramInt << 24 >> 24), i });
	}

	public byte[] getHeader() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		WriteChar(localByteArrayOutputStream, this.fileID);
		WriteInt(localByteArrayOutputStream, this.fileLength);
		WriteChar(localByteArrayOutputStream, this.wavTag);
		WriteChar(localByteArrayOutputStream, this.FmtHdrID);
		WriteInt(localByteArrayOutputStream, this.FmtHdrLeth);
		WriteShort(localByteArrayOutputStream, this.FormatTag);
		WriteShort(localByteArrayOutputStream, this.Channels);
		WriteInt(localByteArrayOutputStream, this.SamplesPerSec);
		WriteInt(localByteArrayOutputStream, this.AvgBytesPerSec);
		WriteShort(localByteArrayOutputStream, this.BlockAlign);
		WriteShort(localByteArrayOutputStream, this.BitsPerSample);
		WriteChar(localByteArrayOutputStream, this.DataHdrID);
		WriteInt(localByteArrayOutputStream, this.DataHdrLeth);
		localByteArrayOutputStream.flush();
		byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
		localByteArrayOutputStream.close();
		
		return arrayOfByte;
	}
}