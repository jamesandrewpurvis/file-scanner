import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("#####################################################################");
		System.out.println("                                  FILE SCANNER                   ");
		System.out.println("#####################################################################");
		
		System.out.println(" ");
		System.out.println("Please enter the file path of your poem below: ");
		
		Scanner mScanner = new Scanner(System.in);
		
		String mFilePath = mScanner.next();
		
		FileScanner mFileScanner = new FileScanner(mFilePath);
	}

}
