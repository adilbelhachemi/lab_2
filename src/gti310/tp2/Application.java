package gti310.tp2;

import java.io.FileNotFoundException;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
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
		
		Details details = new Details();
		details.Details(soundFile);
		
		System.out.println("required size :"+details.getRequiredSize());
		System.out.println("buffred size :"+details.getBufferSize(delai));
		details.getInfos();

		
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

		ConcreteAudioFilter filter = new ConcreteAudioFilter(delai,facteur, details.getBufferSize(delai));

		/*try {
			System.out.println(""+source.getChannels());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		try {
			while(true){
				dest.push(filter.process(source.pop(details.getRequiredSize()))); // mono 1oct / streo 2 oct / 8bit 1 oct / 16 bit 2oct 
			}
		} catch (Exception e) {
			System.out.println("Fini");
		}	
	}



}
