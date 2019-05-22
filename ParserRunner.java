package chap;
import diamond.ast.ASTree;
import diamond.*;
public class ParserRunner {

	public static void main(String[] args) throws ParseException{
		// TODO Auto-generated method stub
		Lexer l=new Lexer(new CodeDialog());
		BasicParser bp=new BasicParser();
		while(l.peek(0)!=Token.EOF) {
			ASTree ast=bp.parse(l);
			System.out.println("=> "+ast.toString());
		}
	}

}
