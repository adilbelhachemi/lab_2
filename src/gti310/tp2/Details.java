package gti310.tp2;

import gti310.tp2.audio.ConcreteAudioFilter;
import gti310.tp2.io.FileSink;
import gti310.tp2.io.FileSource;



import java.io.*;

public class Details {
	
	private final static int BUFFER_SIZE = 4096;

	private final static int FMT_CHUNK_ID = 0x20746D66;
	private final static int DATA_CHUNK_ID = 0x61746164;
	private final static int RIFF_CHUNK_ID = 0x46464952;
	private final static int RIFF_TYPE_ID = 0x45564157;
	
byte[] buffer = new byte[BUFFER_SIZE];
		int bytesPerSample;
		long numFrames;	
		int numChannels=0;				// 2 bytes unsigned, 0x0001 (1) to 0xFFFF (65,535)
		long sampleRate=0;				// 4 bytes unsigned, 0x00000001 (1) to 0xFFFFFFFF (4,294,967,295)
														// Although a java int is 4 bytes, it is signed, so need to use a long
		int blockAlign = 0;					// 2 bytes unsigned, 0x0001 (1) to 0xFFFF (65,535)
		int validBits=0;					// 2 bytes unsigned, 0x0002 (2) to 0xFFFF (65,535)

		// Buffering
				// Local buffer used for IO
		int bufferPointer;				// Points to the current position in local buffer
		int bytesRead;					// Bytes read after last read into local buffer
		long frameCounter;		
		
		
		
	public void Details(String soundFile) {
		System.out.println("Audio Resample project!");

		
		


		DataInputStream source = null;
		try {
			
			//source = new FileSource(soundFile);
			FileInputStream iStream = new FileInputStream(soundFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {

			source = new DataInputStream(
						new BufferedInputStream(
							new FileInputStream(soundFile)
						)
					);

			// Read the first 12 bytes of the file
			bytesRead = source.read(buffer, 0, 12);
			
			// Extract parts from the header
			long riffChunkID = getLE(buffer, 0, 4);
			long chunkSize = getLE(buffer, 4, 4);
			long riffTypeID = getLE(buffer, 8, 4);

			// Search for the Format and Data Chunks
			while (true)
			{
				bytesRead = source.read(buffer, 0, 8);
				long chunkID = getLE(buffer, 0, 4);
				chunkSize = getLE(buffer, 4, 4);

				long numChunkBytes = (chunkSize%2 == 1) ? chunkSize+1 : chunkSize;

				if (chunkID == FMT_CHUNK_ID)
				{

					// Read in the header info
					bytesRead = source.read(buffer, 0, 16);

					// Extract the format information
					numChannels = (int) getLE(buffer, 2, 2);
					sampleRate = getLE(buffer, 4, 4);
					blockAlign = (int) getLE(buffer, 12, 2);
					validBits = (int) getLE(buffer, 14, 2);

					// Account for number of format bytes and then skip over
					// any extra format bytes
					numChunkBytes -= 16;
					if (numChunkBytes > 0) source.skip(numChunkBytes);
				}
				else if (chunkID == DATA_CHUNK_ID)
				{
					break;
				}
				else
				{
					source.skip(numChunkBytes);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(numChannels);
		System.out.println(sampleRate);
		System.out.println(blockAlign);
		System.out.println(validBits);
	}
	
	
	public int getRequiredSize(){
		
		
		if (validBits == 16 && numChannels == 2) {
			return 4;
			
		}else if (validBits == 16 && numChannels == 1) {
			return 2; //3
		}else if (validBits == 8 && numChannels == 2) {
			return 2; //3
		}else if (validBits == 8 && numChannels == 1) {
			return 1; //2
		}else
			return -1;	
	}
	
	public int getBufferSize(int delai){
		return (int) (sampleRate * getRequiredSize() * delai);
		
		/*if (validBits == 16) {
			return (int) (sampleRate * numChannels * delai * 2);
		}else{
			return (int) (sampleRate * numChannels * delai * 1);
		}	*/		
	}
	
	
	private static long getLE(byte[] buffer, int pos, int numBytes)
	{
		numBytes --;
		pos += numBytes;

		long val = buffer[pos] & 0xFF;
		for (int b=0 ; b<numBytes ; b++) 
			val = (val << 8) + (buffer[--pos] & 0xFF);

		return val;
	}
	
	
	public void getInfos(){
		System.out.println("numChannels :"+numChannels);
		System.out.println("sampleRate :"+sampleRate);
		System.out.println("blockAlign :"+blockAlign);
		System.out.println("validBits :"+validBits);
	}
}
