package diamond.ast;
import diamond.Token;
public class NumberLiteral extends ASTLeaf{//���ֽڵ�
	public NumberLiteral(Token t) {
		super(t);
	}
	public int value() {return token().getNumber();}
} 
