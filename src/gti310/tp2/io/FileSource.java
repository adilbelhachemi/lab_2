package gti310.tp2.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * A FileSource object opens a handle on a file and sends the data in
 * sequential order to a caller. When the data reaches the end-of-file marker,
 * it will return nothing.
 * 
 * @author Fran�ois Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class FileSource {
	
	/* The file's handler */
	private DataInputStream _reader;

	/**
	 * Create a new FileSource. The instanciation will be cancelled if the
	 * specified path is not valid.
	 * 
	 * @param location The complete path to the file
	 * @throws FileNotFoundException If the path does not lead to a real file.
	 */
	public FileSource(String location) throws FileNotFoundException {
		try {
			/* open the handler on the specified file */
			_reader = new DataInputStream(
						new BufferedInputStream(
							new FileInputStream(location)));
		} catch (FileNotFoundException e) {
			/* the path is not valid */
			throw e;
		}
	}
	
	/**
	 * Unreference the file and close it cleanly.
	 */
	public void close() {
		try {
			/* close the handler */
			_reader.close();
		} catch (IOException e) {
			/* something went wrong */
		}
	}
	
	/**
	 * Query the handler for some bytes from the file. If the size is larger
	 * than the amount left to read in the file, it will return the number of
	 * bytes left in the file.
	 *  
	 * @param size The number of bytes to read.
	 * @return An array of bytes read in the file.
	 * @throws Exception 
	 */
	public byte[] pop(int size) throws Exception {
		try {
			/* create a new byte array for the number of bytes asked */
			byte[] buffer = new byte[size];
			
			/* read the number of bytes asked for, or the amount left in the
			 * file */
			if (_reader.read(buffer) == -1){
				throw new Exception("Fini");				
			}
			
			
			/* return what was read */
			return buffer;
		} catch (IOException e) {
			/* something went wrong, or EOF reached */
			return null;
		}
	}
	
	
/*	
	private byte[] getBytes(int deca, int size)  throws Exception {
		try {
			 create a new byte array for the number of bytes asked 
			byte[] buffer = new byte[size];
			_reader.skip(deca);
			 read the number of bytes asked for, or the amount left in the
			 * file 
			if (_reader.read(buffer) == -1){
				throw new Exception("Fini");				
			}
			
			
			 return what was read 
			return buffer;
		} catch (IOException e) {
			 something went wrong, or EOF reached 
			return null;
		}
		
	}
	
	
	
	public int getChannels() throws Exception{
		
		return new BigInteger(getBytes(0,3)).intValue();
		
	}*/
	
	
	
	
	
	
	
}
