import org.w3c.dom.Element;

public class MemoryPartition {

	public double size;
	public int memoryAddress;
	public boolean isOccupied;

	public MemoryPartition(double size, int memoryAddress, boolean isOccupied){

		this.isOccupied = isOccupied;
		this.size = size;
		this.memoryAddress = memoryAddress;
	}
	
	public MemoryPartition(){

	}
	
	public int getisOccupied(){
		
		if ( isOccupied == true ){ 
			
			return 0;
		}
		else {
			
			return 1;
		}
	}
	
	@Override
	public String toString(){
		
		return size + " " + memoryAddress + " " + isOccupied ;
	}
}
