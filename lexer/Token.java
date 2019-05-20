package diamond;
import diamond.DiamondException;


public  abstract class Token {
	public static final Token EOF=new Token(-1) {};//代码结束标记
	public static final String EOL="\\n";	//换行符
	private int linenumber;	//行号
	protected Token(int line) {
		linenumber=line; //构造函数，传入行号
	}
	public int getLineNumber() {return linenumber;}
	public boolean isIdentifier() {return false;}
	public boolean isNumber() {return false;}
	public boolean isString() {return false;}
	public int getNumber() {throw new DiamondException("not number token");}
	public String getText() {return "";}
	
}
