package gti310.tp2.audio;



public class ConcreteAudioFilter implements AudioFilter{

	byte[] buffer;
	int pos=0;
	private Float facteur;
	private boolean echo = false;
	private int bufferSize = 0;
	
	
	
	public ConcreteAudioFilter(int delai, Float facteur, int bufferSize) {
		//determinerTaille();
		this.facteur = facteur;
		this.bufferSize = bufferSize;
		buffer = new byte[bufferSize];
	}

	public byte[] process(byte[] bs){
		byte[] result = new byte[bs.length];
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
		
	
}
