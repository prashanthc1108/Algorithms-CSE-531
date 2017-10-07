import java.io.BufferedReader;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;

class priorityQueue {
	HashMap<Integer,Integer> minHeap;//A[i]
	int s=0;
	HashMap<Integer,Integer> heapIndex;//p(v)
	HashMap<Integer,Integer> keyVal;//key(v)

	
	public priorityQueue(){
		s=0;
		minHeap = new HashMap<Integer,Integer>();
		heapIndex = new HashMap<Integer,Integer>();
		keyVal = new HashMap<Integer,Integer>();

	}
	
	public void insert(int v, int key_val){
//		System.out.println("Inserting "+v+" at end position "+(s+1)+" with weight "+key_val);

		s++;

		if(s==1){
			minHeap.put(1, v);
			heapIndex.put(v, 1);
			keyVal.put(v, key_val);
		}else{
			minHeap.put(s, v);
			heapIndex.put(v,s);
			keyVal.put(v, key_val);
			heapifyUP(s);
		}
	}
	
	private void heapifyUP(int i){
		int j;
		int temp;
		while (i>1){
			j= (int)(i/2);

//			System.out.println("child pos: "+i+" parent: "+j+"\nchild: "+minHeap.get(i)+" parent: "+minHeap.get(j));
				if(keyVal.get(minHeap.get(i))<keyVal.get(minHeap.get(j))){
					temp=minHeap.get(i);minHeap.put(i,minHeap.get(j));minHeap.put(j,temp);	//swap
					heapIndex.put(minHeap.get(i),i);heapIndex.put(minHeap.get(j), j);
//					temp=keyVal.get(i);keyVal.put(i,keyVal.get(j));keyVal.put(j,temp);	//swap

					i=j;
			}
			
			else{
				break;
			}

		}
	}
	
	public int extract_min(){
//		System.out.println("HSize:"+minHeap.size());
//		System.out.println("v=A[i]:"+minHeap.toString());
//		System.out.println("p(v):"+heapIndex.toString());
//		System.out.println("key(v):"+keyVal.toString());

		int ret=minHeap.get(1);
		minHeap.put(1,minHeap.get(s));
		heapIndex.put(minHeap.get(1), 1);
		s--;
		minHeap.remove(s+1);

		if(s>=1){
			heapifyDown(1);
		}
//		System.out.println("Next to come:"+minHeap.get(1));

		return ret;
	}
	
	private void heapifyDown(int i){
		int j;
		int temp;
		while ((2*i)<=s){
			if(((2*i) == s) ||( keyVal.get(minHeap.get((2*i))) <= keyVal.get(minHeap.get(((2*i)+1)))) ){
				j=2*i;
			}else{
				j=(2*i)+1;
			}
			if( keyVal.get(minHeap.get(j)) < keyVal.get(minHeap.get(i)) ){
				temp=minHeap.get(i);minHeap.put(i,minHeap.get(j));minHeap.put(j,temp);
				heapIndex.put(minHeap.get(i), i); heapIndex.put(minHeap.get(j), j);
//				temp=keyVal.get(i);keyVal.put(i,keyVal.get(j));keyVal.put(j,temp);	//swap

				i=j;
			}
			else{
				break;
			}
		}
	}
	
	public void decrease_key(int v, int new_key_val){
		keyVal.put(v, new_key_val);
		heapifyUP(heapIndex.get(v));
//		System.out.println("After decrease key and heapify up of:"+v);
//		System.out.println("v=A[i]:"+minHeap.toString());
//		System.out.println("p(v):"+heapIndex.toString());
//		System.out.println("key(v):"+keyVal.toString());
	}
	

}


public class mst {    
	

	
	

	public static void main(String[] args) {
//		System.out.println("test");
		
		FileReader fileReader;
		priorityQueue Q;
		HashMap<Integer,Integer> lightest=new HashMap<Integer,Integer>();//w(u,v)
		HashMap<Integer,Integer> parent=new HashMap<Integer,Integer>();//w(u,v)
		HashMap<Integer,LinkedList<Integer>> incidentE = new HashMap<Integer,LinkedList<Integer>>();

		HashMap<String,Integer> validEdge=new HashMap<String,Integer>();//w(u,v)
		LinkedList<Integer> V= new LinkedList<Integer>();
		HashMap<Integer,Integer> V1= new HashMap<Integer,Integer>();//just to use containsKeys(O(1)) instead of Contains(O(n))
		
		String line = null,edgeString1=null,edgeString2=null;
		int u,v;
//		int lineNo=0;
		String[] uvCount;
//		int edgeCount = 0,vertexCount=0;
		String outFile = "output.txt";
		String inFile = "input.txt";
		
		try {
			fileReader = new FileReader(inFile);
			BufferedReader br = new BufferedReader(fileReader);
			line = br.readLine();
			uvCount=line.split(" ");
//			vertexCount=Integer.parseInt(uvCount[0]);
//			edgeCount=Integer.parseInt(uvCount[1]);
//			int ii=0;

			while ((line = br.readLine()) != null) {
//				if(lineNo == 0){
					
//				}else{
//					if(lineNo>edgeCount+1){
//						System.out.println("Invalid Number of Input edges. Expected Edges: \n"+edgeCount);
//						System.exit(0);
//					}
//				else{
						uvCount=line.split(" ");
//						if(uvCount.length!=3){
//							System.out.println("Invalid Number of Inputs in line: \n"+(lineNo+1));
//							System.exit(0);
//						}
						u=Integer.parseInt(uvCount[0]);
						v=Integer.parseInt(uvCount[1]);
						if(V1.containsKey(u)==false){
							V1.put(u,1);
							V.add(u);
						}
						if(V1.containsKey(v)==false){
							V1.put(v,1);
							V.add(v);
						}
//						ii++;
						edgeString1=u+","+v;
						edgeString2=v+","+u;

						if( (validEdge.containsKey(edgeString1)==false) ){	//&& (validEdge.containsKey(edgeString2)==false) ){
							validEdge.put(edgeString1, Integer.parseInt(uvCount[2]));
							validEdge.put(edgeString2, Integer.parseInt(uvCount[2]));
						}
						
						if(incidentE.containsKey(v)==true){
							LinkedList<Integer> connectedV= new LinkedList<Integer>();
							connectedV=incidentE.get(v);
//							if(connectedV.contains(u)==false){
								connectedV.add(u);
								incidentE.put(v, connectedV);
//							}
						}else{
							LinkedList<Integer> connectedV= new LinkedList<Integer>();
							connectedV.add(u);
							incidentE.put(v, connectedV);
						}
						if(incidentE.containsKey(u)==true){
							LinkedList<Integer> connectedV= new LinkedList<Integer>();
							connectedV=incidentE.get(u);
//							if(connectedV.contains(v)==false){
								connectedV.add(v);
								incidentE.put(u, connectedV);
//							}
						}else{
							LinkedList<Integer> connectedV= new LinkedList<Integer>();
							connectedV.add(v);
							incidentE.put(u, connectedV);
						}
						
					
//				}
				
//				lineNo++;
			}
			
//			System.out.println(incidentE.toString());
			
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Please have the input file as input.txt in the location of the program and rerun.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error while reading file. Please ensure the input.txt is in the expected format and rerun.");
			e.printStackTrace();
		}
		Q = new priorityQueue(); 

		//we now have V 
//		System.out.println(V.toString());
//		System.exit(0);
		
		int s=V.getFirst();	//arbitrary vertex in V
		LinkedList<Integer> S = new LinkedList<Integer>();	//S will contain MST
		HashMap<Integer,Integer> S1 = new HashMap<Integer,Integer>();	//S1 just to use containsKeys(O(1)) instead of Contains(O(n))
		lightest.put(s, 0);
		ListIterator<Integer> vertexList = V.listIterator();
		int vertex;
		while(vertexList.hasNext()){
			vertex=vertexList.next();
			if(vertex!=s){
				lightest.put(vertex,Integer.MAX_VALUE);		
			}
			Q.insert(vertex, lightest.get(vertex));

		}
		
		System.out.println("Read file, computing MST");


//		System.out.println("HEAPMAP size:"+Q.minHeap.size());


//		System.out.println("\n\nInitial HEAP:"+Q.minHeap.toString());
//		System.out.println("Position in HEAP:"+Q.heapIndex.toString());
//		System.out.println("Wrights in HEAP:"+Q.keyVal.toString());
//		System.out.println("d(v):"+lightest.toString());
//		System.out.println("Valid Edges:"+validEdge.toString());

//System.exit(0);
//		String[] finalE=new String[vertexCount*10];
//		int edgeCounter= 0;

		while( S.size() < V.size() ){

			u = Q.extract_min();
			S.add(u);
			S1.put(u, 1);
//			System.out.println("Current MST:"+S.toString());

//			V.removeFirstOccurrence(u);
//			incidentE.get(u).removeFirstOccurrence(o)
//			System.out.println("S: "+S.size()+" V: "+V.size()+" H: "+Q.minHeap.size());
			String edge=null;
//			vertexList=V.listIterator();
			vertexList=incidentE.get(u).listIterator();
			while(vertexList.hasNext()){
				vertex=vertexList.next();
				edge = u+","+vertex;
//				System.out.println("S.contains(vertex)"+S.contains(vertex)+"validEdge.containsKey(edge)"+validEdge.containsKey(edge)+"vertex:"+vertex);
				if(S1.containsKey(vertex)==false){
//				if(validEdge.containsKey(edge)==true){
//					System.out.println("validEdge.get(edge)"+validEdge.get(edge)+"lightest.get(vertex)"+lightest.get(vertex));
					if (validEdge.get(edge)<lightest.get(vertex)){

						lightest.put(vertex,validEdge.get(edge));
						Q.decrease_key(vertex, lightest.get(vertex));
						parent.put(vertex,u);
//						finalE[edgeCounter++]=edge;
//						System.out.println("u,v,w:"+u+","+vertex+":"+lightest.get(vertex));

//						System.out.println("edgeCounter:"+(edgeCounter-1)+" finalE[edgeCounter]:"+finalE[edgeCounter-1]);
//						vertexList=V.listIterator();
//						System.out.println("\nCurrent Vertex: "+vertex+" from: "+u+" weight: "+validEdge.get(edge));
//						System.out.println("Current HEAP:"+Q.minHeap.toString());
//						System.out.println("Position in HEAP:"+Q.heapIndex.toString());
//						System.out.println("Wrights in HEAP:"+Q.keyVal.toString());
//						System.out.println("Current MST:"+S.toString());
					}
				}
			}
			
		}	
		int xx=0;
		while(xx<Q.minHeap.size()){
			xx++;
//			System.out.println("Vertex: "+Q.minHeap.get(xx)+" weight to MST: "+Q.keyVal.get(Q.minHeap.get(xx))+" Index in array: "+Q.heapIndex.get(Q.minHeap.get(xx)));
		}
		String outputSTR=new String(), tempStr = new String();
		int totalW=0;
		vertexList=S.listIterator();
		while(vertexList.hasNext()){
			vertex=vertexList.next();
			totalW+=lightest.get(vertex);
			tempStr=tempStr+ parent.get(vertex)+" "+(vertex)+" "+lightest.get(vertex)+"\n";
//			tempStr=tempStr+ parent.get(vertex)+" "+(vertex)+"\n";
		}
		outputSTR=totalW+"\n"+tempStr;
//		System.out.println(outputSTR);
		System.out.println("Obtained MST, writing to file");

		try (PrintWriter out = new PrintWriter(outFile))
		{
			String[] printables = outputSTR.split("\n");
			for(int x=0; x<printables.length; x++){
				if(printables[x].contains("null")){
					continue;
				}
			out.println(printables[x]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		System.out.println("Output Written to "+outFile);

		
		
	}
	

}
