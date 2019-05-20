package diamond;
import diamond.DiamondException;


public  abstract class Token {
	public static final Token EOF=new Token(-1) {};//����������
	public static final String EOL="\\n";	//���з�
	private int linenumber;	//�к�
	protected Token(int line) {
		linenumber=line; //���캯���������к�
	}
	public int getLineNumber() {return linenumber;}
	public boolean isIdentifier() {return false;}
	public boolean isNumber() {return false;}
	public boolean isString() {return false;}
	public int getNumber() {throw new DiamondException("not number token");}
	public String getText() {return "";}
	
}
