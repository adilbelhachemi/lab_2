package gti310.tp2.audio;

public class ConcreteAudioFilter implements AudioFilter{

	byte[] buffer;
	int pos=0;
	private Float facteur;
	private boolean echo = false;
	
	
	
	public ConcreteAudioFilter(int delai, Float facteur) {
		determinerTaille();
		this.facteur = facteur;
	}

	public byte[] process(byte[] bs){
		byte[] result = new byte[4];
		//System.out.println(bs.length);
		for (int i = 0; i < bs.length; i++) {
			if(pos == buffer.length){
				pos = 0;
				echo  = true;
			} 
			if(echo){
				result[i] = (byte) (bs[i] + facteur * buffer[pos]);
			}else{
				result[i] = (byte) (bs[i]);
			}
			
			buffer[pos] = bs[i];
			pos++;		
			
		}	
		
		return result;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
	
	void determinerTaille(){
		buffer = new byte[176400];
	}
	
	
	
}
