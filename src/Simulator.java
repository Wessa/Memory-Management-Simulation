import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Simulator {

	public static ArrayList<MemoryPartition> blocks=new ArrayList<MemoryPartition>();

	/**
	 * @param policy
	 */
	public static int Allocate( String policy , double size ){
		
		if ( policy.equals("First Fit") ){
			
			for ( int i=0 ;i<blocks.size() ; i++ ){
				
				if ( blocks.get(i).size >= size && !blocks.get(i).isOccupied ){
					
					MemoryPartition newBlock = new MemoryPartition ( (blocks.get(i).size - size) , 
																	 (int)(blocks.get(i).memoryAddress + size) , false) ;
					
					blocks.get(i).size = size ;
					blocks.get(i).isOccupied = true ;
					
					if ( newBlock.size > 0 )
						blocks.add( i , newBlock ) ;
					
					return blocks.get(i).memoryAddress ;
				}
			}
			
			return -1 ;
		}
		
		if ( policy.equals("Best Fit") ){
			
			int index = -1 ;
			double minSize  = 10000.0 ;
			
			for ( int i=0 ; i<blocks.size() ; i++ ){
				
				if ( blocks.get(i).size <= minSize && blocks.get(i).size >= size && !blocks.get(i).isOccupied ){
					
					minSize = blocks.get(i).size ;
					index = i ;
					
					if ( blocks.get(i).size == size )
						break ;
				}												
			}
			
			if ( index == -1 )
				return -1 ;
			
			MemoryPartition newBlock = new MemoryPartition ( (blocks.get(index).size - size) , 
					 (int)(blocks.get(index).memoryAddress + size) , false) ;

			blocks.get(index).size = size ;
			blocks.get(index).isOccupied = true ;
			
			if ( newBlock.size > 0 )
				blocks.add( index , newBlock ) ;

			return blocks.get(index).memoryAddress ;
			
		}
		else {
			
			int index = -1 ;
			double maxSize  = 0.0 ;
			
			for ( int i=0 ; i<blocks.size() ; i++ ){
				
				if ( blocks.get(i).size > maxSize && blocks.get(i).size >= size && !blocks.get(i).isOccupied ){
					
					maxSize = blocks.get(i).size ;
					index = i ;
					
					/*if ( blocks.get(i).size == size )
						break ;*/
				}												
			}
			
			if ( index == -1 )
				return -1 ;
				
			MemoryPartition newBlock = new MemoryPartition ( (blocks.get(index).size - size) , 
												             (int)(blocks.get(index).memoryAddress + size) , false) ;
			
			blocks.get(index).size = size ;
			blocks.get(index).isOccupied = true ;
				
			blocks.add( index , newBlock ) ;
			
			return blocks.get(index).memoryAddress ;
			
		}
		
	}

	public static void DeAllocate() {

	}
	
	public static void Defragment() {
		
		Collections.sort(blocks, new Comparator<MemoryPartition>() {

			public int compare(MemoryPartition x, MemoryPartition y) {

				return (int) (x.getisOccupied() - y.getisOccupied());
			}
		});
		//System.out.println(blocks);
		int previousend=0,i=0;
		for(;blocks.get(i).isOccupied==true;i++)
		{
			blocks.get(i).memoryAddress=previousend;
			previousend+=blocks.get(i).size;
		}
		blocks.get(i).memoryAddress=previousend;
		for(int j=i+1;j<blocks.size();j++)
		{
			blocks.get(i).size+=blocks.get(j).size;
			blocks.remove(j);
			j--;
		}
	//	System.out.println(blocks);
	}

	public static void main(String[] args) {
		
		MemoryPartition x=new MemoryPartition(100, 0, false);
		MemoryPartition y=new MemoryPartition(30, 30, true);
		MemoryPartition z=new MemoryPartition(30 , 60, false);
		MemoryPartition xx=new MemoryPartition(90, 90, true);
		MemoryPartition xy=new MemoryPartition(100, 180 , false);
		
		blocks.add(x); 
		blocks.add(y); 
		blocks.add(z); 
		blocks.add(xx); 
		blocks.add(xy);
		
		Allocate ( "First Fit" , 20 ) ;
		Allocate ( "Best Fit" , 20 ) ;
		Allocate ( "Worst Fit" , 20 ) ;
		
		Collections.sort(blocks, new Comparator<MemoryPartition>() {

			public int compare(MemoryPartition x, MemoryPartition y) {

				return (int)( x.memoryAddress - y.memoryAddress );
			}
		});
		
		for ( int i=0 ; i<blocks.size() ; i++ )
			System.out.println( blocks.get(i) );
		
		//Defragment();
		
	}
}
