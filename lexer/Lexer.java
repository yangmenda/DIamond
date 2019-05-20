package diamond;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	public static String regexPat="\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
+"|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
	private Pattern pattern=Pattern.compile(regexPat);	//����������ʽ
	private ArrayList<Token> queue=new ArrayList<Token>();
	private boolean hasmore;	//�����Ƿ����ַ���
	private LineNumberReader reader;
	public Lexer(Reader r)
	{
		hasmore=true;
		reader=new LineNumberReader(r);
	}
	public Token read() throws ParseException	//��ȡ��亯��
	{
		if(fillQueue(0))
			return queue.remove(0);
		else
			return Token.EOF;
	}
	public Token peek(int i) throws ParseException	//Ԥ������
	{
		if(fillQueue(i))
			return queue.get(i);
		else
			return Token.EOF;
	}
	private boolean fillQueue(int i)throws ParseException
	{
		while(i>=queue.size())
		{
			if(hasmore)
				readLine();
			else
				return false;
			
		}
		return true;
	}
	protected void readLine()throws ParseException	//��ȡ����
	{
		String line;
		try {
			line =reader.readLine();
		}catch(IOException e)
		{
			throw new ParseException(e);
			
		}
		if(line==null)//�ַ��Ѿ�����
		{
			hasmore=false;
			return;
		}
		int lineNo=reader.getLineNumber();//ȡ���к�
		Matcher matcher=pattern.matcher(line);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos=0;
		int endPos=line.length();
		while(pos<endPos)
		{
			matcher.region(pos, endPos);	//�涨������ʽ��ƥ����
			if(matcher.lookingAt()) {//��ʼ����ƥ��
				addToken(lineNo,matcher);//���дʷ�����
				pos=matcher.end();
			}
			else {
				throw new ParseException("bad tken at line "+lineNo);
				
			}
		}
		queue.add(new IdToken(lineNo,Token.EOL));
		
	}
	protected  void addToken(int lineNo,Matcher matcher)//�ʷ�����
	{
		String m=matcher.group(1);//�ӵ�һ�����ſ�ʼƥ��
		if(m!=null)
			if(matcher.group(2)==null)
			{
				Token token;
				if(matcher.group(3)!=null)//ƥ��Ϊ����
					token=new NumToken(lineNo,Integer.parseInt(m));
				else if (matcher.group(4)!=null) {//ƥ��Ϊ�ַ���
					token=new StrToken(lineNo,toStringLiteral(m));
				}
				else {//ƥ��Ϊ��Ƿ�
					token=new IdToken(lineNo,m);
				}
				queue.add(token);//����������˾�
			}
		
	}
	protected String toStringLiteral(String s)//�жϻ��з���ע�ͣ�����ȡ����
	{
		StringBuilder sb=new StringBuilder();
		int len=s.length()-1;
		for(int i=1;i<len;++i)
		{
			char c=s.charAt(i);
			if(c=='\\'&&i+1<len)
			{
				int c2=s.charAt(i+1);
				if(c2=='"'||c2=='\\')
					c=s.charAt(++i);
				else if(c2=='n')
				{
					++i;
					c='\n';
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}
	protected static class NumToken extends Token{
		private int value;
		protected NumToken(int line,int v)
		{
			super(line);
			value=v;
		}
		public boolean isNumber() {return true;}
		public String getText() {return Integer.toString(value);}
		public int getNumber() {return value;}
	}
	protected static class IdToken extends Token
	{
		private String text;
		protected IdToken(int line, String id)
		{
			super(line);
			text=id;
		}
		public boolean isIdentifier() {return true;}
		public String getText() {return text;}
	}
	protected static class StrToken extends Token
	{
		private String literal;
		StrToken(int line,String str) {
			super(line);
			literal=str;
		}
		public boolean isString() {return true;}
		public String getText() {return literal;}
	}
}
