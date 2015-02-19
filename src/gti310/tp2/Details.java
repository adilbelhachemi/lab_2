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
	int numChannels = 0; 
	long sampleRate = 0;
	int blockAlign = 0; 
	int validBits = 0;
	
	// Buffering
	// Local buffer used for IO
	int bufferPointer; // Points to the current position in local buffer
	int bytesRead; // Bytes read after last read into local buffer
	long frameCounter;

	public void audioDetails(String soundFile) {
		DataInputStream source = null;

		try {

			source = new DataInputStream(new BufferedInputStream(
					new FileInputStream(soundFile)));
/*  
 * CODE EMPRUNTÉ :
 * Les lignes suivant sont basées sur du code exemple provenant du site :
 * http://www.labbookpages.co.uk/audio/javaWavFiles.html
 * J'ai pris les méthodes openWave() et getLE() dans le Download Files "WavFile.tar.gz"
 */
			bytesRead = source.read(buffer, 0, 12);

			long chunkSize = getLE(buffer, 4, 4);

			// Search for the Format and Data Chunks
			while (true) {
				bytesRead = source.read(buffer, 0, 8);
				long chunkID = getLE(buffer, 0, 4);
				chunkSize = getLE(buffer, 4, 4);

				long numChunkBytes = (chunkSize % 2 == 1) ? chunkSize + 1
						: chunkSize;

				if (chunkID == FMT_CHUNK_ID) {

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
					if (numChunkBytes > 0)
						source.skip(numChunkBytes);
				} else if (chunkID == DATA_CHUNK_ID) {
					break;
				} else {
					source.skip(numChunkBytes);
				}
			}
/* FIN DU PREMIER PARTIE DU CODE EMPRUNTÉ */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getRequiredSize() {

		if (validBits == 16 && numChannels == 2) {
			return 4;

		} else if (validBits == 16 && numChannels == 1) {
			return 2; // 3
		} else if (validBits == 8 && numChannels == 2) {
			return 2; // 3
		} else if (validBits == 8 && numChannels == 1) {
			return 1; // 2
		} else
			return -1;
	}

	public int getBufferSize(int delai) {
		return (int) (sampleRate * getRequiredSize() * delai);
	}
/*  
 * CODE EMPRUNTÉ :
 * Les lignes suivant sont basées sur du code exemple provenant du site :
 * http://www.labbookpages.co.uk/audio/javaWavFiles.html
 * J'ai pris les méthodes openWave() et getLE() dans le Download Files "WavFile.tar.gz"
 */
	private static long getLE(byte[] buffer, int pos, int numBytes) {
		numBytes--;
		pos += numBytes;

		long val = buffer[pos] & 0xFF;
		for (int b = 0; b < numBytes; b++)
			val = (val << 8) + (buffer[--pos] & 0xFF);

		return val;
	}
/* FIN DU PREMIER PARTIE DU CODE EMPRUNTÉ */

	public void getInfos() {
		System.out.println("numChannels :" + numChannels);
		System.out.println("sampleRate :" + sampleRate);
		System.out.println("blockAlign :" + blockAlign);
		System.out.println("validBits :" + validBits);
	}
}
