package diamond.ast;
import diamond.*;
public class Name extends ASTLeaf{//×ÖÄ¸½Úµã
	public Name(Token t ) {super(t);}
	public String name() {return token().getText();}
}
