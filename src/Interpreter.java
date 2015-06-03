import java.util.Scanner;
import java.util.Stack;


public class Interpreter {

	// Class data
	private String m_Tape;		// Tape, program
	private byte[] m_Mem;		// Program memory
	private Scanner m_Scanner;	// For user input

	/* Description: Default constructor. 
	 * @param		N/A
	 * @return		N/A
	 */
	public Interpreter(){
		m_Mem = new byte[30000];
		for (int i = 0; i < m_Mem.length; i++) m_Mem[i] = 0;
		// Setup input
		m_Scanner = new Scanner(System.in);

	}

	/* Description: Parses and interprets BrainF*** code. Print commands are
	 *   sent to command line interface
	 * @param	tape	A String containing proper BrainF*** source code
	 * @return			None. Output is sent to command line
	 */
	public void InterpretTape(String tape){

		m_Tape = tape;		
		int dp = 0;			// data pointer
		int ip = 0;			// instruction pointer
		// For keeping track of starting loop indexes
		Stack<Integer> loopStack = new Stack<Integer>(); 

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

				// Code inspiration and help:
				// https://github.com/fabianm/brainfuck-java/blob/master/src/main
				//  /java/org/fabianm/brainfuck/BrainfuckEngine.java
				
				if(m_Mem[dp] == 0){
					int bc = 1;			// bracket count
					while (bc > 0){
						char c = (char) m_Tape.charAt(++ip);	

						if (c == '[') bc++;
						else if (c == ']') bc--;
					}
				}

				//				if (m_Mem[dp] == 0){
				//					++ip; // point to instruction after '['
				//					while (m_Tape.charAt(ip) != ']'){
				//						// nested loops
				//						if (m_Tape.charAt(ip) == '[') 
				//							loopStack.push(ip); // Save ip to go back to
				//						++ip;
				//					}
				//				}
				//				else
				//					loopStack.push(ip); // Save ip to go back to
				break;
			case ']':
				// End loop

				// Wikipedia entry:
				//  if the byte at the data pointer is nonzero, then instead of moving the 
				// instruction pointer forward to the next command, jump it back to the 
				// command after the matching [ command.
				
				// Code inspiration and help:
				// https://github.com/fabianm/brainfuck-java/blob/master/src/main
				//  /java/org/fabianm/brainfuck/BrainfuckEngine.java
				int bc = 1;			// bracket count
				while (bc > 0){
					//System.out.printf("\n%s\n", ); // trace
					char c = (char) m_Tape.charAt(--ip);	

					if (c == '[') bc--;
					else if (c == ']') bc++;
				}
				--ip;


				//				if (m_Mem[dp] != 0)
				//					ip = loopStack.peek();
				//				else
				//					loopStack.pop();
				break;
			default:
				// Ignore all other input
			} // Switch
		} // For
	} // ParseTape
} // Interpreter
