package gti310.tp2;

import java.io.FileNotFoundException;

import gti310.tp2.audio.ConcreteAudioFilter;
import gti310.tp2.io.FileSink;
import gti310.tp2.io.FileSource;

public class Application {

	/**
	 * Launch the application
	 * @param args This parameter is ignored
	 */
	public static void main(String args[]) {
		System.out.println("Audio Resample project!");


		String soundFile = args[0];
		String newSoundFile = args[1];
		int delai = Integer.parseInt(args[2]);
		Float facteur = Float.parseFloat(args[3]);

		FileSource source = null;
		try {
			source = new FileSource(soundFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileSink dest = null;
		try {
			dest = new FileSink(newSoundFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConcreteAudioFilter filter = new ConcreteAudioFilter(delai,facteur);

		try {
			while(true){
				dest.push(filter.process(source.pop(4)));
			}
		} catch (Exception e) {
			System.out.println("Fini");
		}	
	}



}
