
public class Brainterpreter {

	
	public static Parser c_P = Parser.getInstance();
	
	
	
	public static void main(String[] args) {
		
		String s = "+++.[.-]>"; // should print: 3 3 2 1
		String hw = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
		
		c_P.ParseTape(hw);
		
	}

}
