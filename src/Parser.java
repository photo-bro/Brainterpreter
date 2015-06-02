import java.util.Scanner;
import java.util.Stack;


public class Parser {

	// Static class instance
	private static Parser c_Instance;

	// Class data
	private String m_Tape;		// Tape, program
	private byte[] m_Mem;		// Program memory
	private Scanner m_Scanner;	// For user input


	// Default constructor
	private Parser(){
		m_Mem = new byte[30000];
		for (int i = 0; i < m_Mem.length; i++) m_Mem[i] = 0;
		// Setup input
		m_Scanner = new Scanner(System.in);

	}

	// Static instance getter
	public static Parser getInstance(){
		if (c_Instance == null)
			c_Instance = new Parser();
		return c_Instance;	
	}

	public void ParseTape(String tape){

		m_Tape = tape;		
		int dp = 0;			// data pointer
		int ip = 0;			// instruction pointer
		Stack<Integer> loopStack = new Stack<Integer>();  // For keeping track of starting loop indexes

		// Parse through each item in tape
		for(; ip < m_Tape.length(); ++ip){

			switch(m_Tape.charAt(ip)){
			case '>':
				// Increment data pointer
				++dp;
				break;
			case '<':
				// Decrement data pointer
				--dp;
				break;
			case '+':
				// Increment value at data pointer
				++m_Mem[dp];
				//System.out.printf("%d", p);
				break;
			case '-':
				// Decrement value at data pointer
				--m_Mem[dp];
				break;
			case '.':
				// Output value at data pointer
				System.out.printf("%s", (char) m_Mem[dp]);
				break;	
			case ',':
				// Input value at data pointer
				m_Mem[dp] = m_Scanner.nextByte();
				break;
			case '[':
				// Begin loop
				
				// Wikipedia entry:
				//  if the byte at the data pointer is zero, then instead of moving the 
				// instruction pointer forward to the next command, jump it forward to 
				// the command after the matching ] command.
				if (m_Mem[dp] == 0){
					++ip; // point to instruction after '['
					while (m_Tape.charAt(ip) != ']'){
						// nested loops
						if (m_Tape.charAt(ip) == '[') 
							loopStack.push(ip); // Save ip to go back to
						++ip;
					}
				}
				else
					loopStack.push(ip); // Save ip to go back to

				break;
			case ']':
				// End loop
				// Jump backward to the matching [ unless the byte at the pointer is zero.
				if (m_Mem[dp] != 0)
					ip = loopStack.peek();
				else
					loopStack.pop();
				break;
			default:
				// ERROR: Invalid input
				// Skip and continue
			} // Switch
			// System.out.printf("\np=%d\n", p); // trace
			//++m_Pt;
		} // For
	} // ParseTape
}
