import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LCS_Prashanth_Chandrashekar_pc74 {

	public static void main(String[] args) {
		FileReader fileReader;
		String line1 = null, line2 = null;
		String inFile = "input.txt";
		String outFile = "output.txt";
		int i,j,n,m;
		

		try {
			fileReader = new FileReader(inFile);
			BufferedReader br = new BufferedReader(fileReader);
			line1 = br.readLine();
			line2 = br.readLine();
			n = line1.length();
			m = line2.length();
			char A[] = line1.toCharArray();
			char B[] = line2.toCharArray();
			int opt[][] = new int[n+1][m+1];
			int p[][] = new int[n+1][m+1];
//			System.out.println(A[n-1]);
			
			for (j=0;j<m;j++){
				opt[0][j]=0;
			}
			
			for (i=1;i<=n;i++){
				opt[i][0]=0;
				for(j=1;j<=m;j++){
//					if (A[i]==B[j]){
					if (A[i-1]==B[j-1]){
						opt[i][j]=opt[i-1][j-1]+1;
						p[i][j]=1;
					}
					else if (opt[i][j-1]>=opt[i-1][j]){
						opt[i][j]=opt[i][j-1];
						p[i][j]=2;
					}
					else{
						opt[i][j]=opt[i-1][j];
						p[i][j]=3;
					}
				}
			}
			br.close();
			
			//recover seq:
			i=n;j=m;String S="";
			int count=0;
			while (i>=0 && j>=0){
				if (p[i][j]==1){
					S=S+A[i-1];
					count++;
					i--;
					j--;
				}else if (p[i][j]==3){
					i--;
				}else{
					j--;
				}
				
			}
			
			String reverse = new StringBuffer(S).reverse().toString();

			
//			System.out.println(count);
//			System.out.println(reverse);
//			System.out.println("Count:"+count);
//			System.out.println("LCS:"+reverse);
			
			try (PrintWriter out = new PrintWriter(outFile))
			{
				out.println(count);
				out.println(reverse);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			
			System.out.println("done");
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Please have the input file as input.txt in the location of the program and rerun.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	
}
