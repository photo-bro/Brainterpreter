import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Brainterpreter {

	/* Description: Open and read BrainF*** source from file
	 * @param			None
	 * @return	String	String containing BrainF*** source
	 */
	public static String PromptFile() throws IOException{
		// User input
		Scanner in  = new Scanner(System.in);

		// Prompt user for file path
		System.out.printf("Enter path of BrainF*** file to interpret: ");
		String path = in.nextLine();

		// Open file
		BufferedReader br = new BufferedReader(new FileReader(path));
		String s = "";

		// Try to read from file
		try {
			StringBuilder sb = new StringBuilder();
			String l = br.readLine();
			// Build file into string
			while (l != null){
				sb.append(l);
				l = br.readLine();
			}
			s = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			br.close();
		}

		// Return contents of file as string
		return s;
	}


	public static void main(String[] args) throws IOException {

		Interpreter BF_Interpret = new Interpreter();

		String hw = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";


		BF_Interpret.InterpretTape(PromptFile());

	}

}
