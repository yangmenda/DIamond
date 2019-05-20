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
	private Pattern pattern=Pattern.compile(regexPat);	//编译正则表达式
	private ArrayList<Token> queue=new ArrayList<Token>();
	private boolean hasmore;	//后面是否有字符串
	private LineNumberReader reader;
	public Lexer(Reader r)
	{
		hasmore=true;
		reader=new LineNumberReader(r);
	}
	public Token read() throws ParseException	//读取语句函数
	{
		if(fillQueue(0))
			return queue.remove(0);
		else
			return Token.EOF;
	}
	public Token peek(int i) throws ParseException	//预读函数
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
	protected void readLine()throws ParseException	//读取函数
	{
		String line;
		try {
			line =reader.readLine();
		}catch(IOException e)
		{
			throw new ParseException(e);
			
		}
		if(line==null)//字符已经读完
		{
			hasmore=false;
			return;
		}
		int lineNo=reader.getLineNumber();//取得行号
		Matcher matcher=pattern.matcher(line);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos=0;
		int endPos=line.length();
		while(pos<endPos)
		{
			matcher.region(pos, endPos);	//规定正则表达式的匹配域
			if(matcher.lookingAt()) {//开始部分匹配
				addToken(lineNo,matcher);//进行词法分类
				pos=matcher.end();
			}
			else {
				throw new ParseException("bad tken at line "+lineNo);
				
			}
		}
		queue.add(new IdToken(lineNo,Token.EOL));
		
	}
	protected  void addToken(int lineNo,Matcher matcher)//词法分类
	{
		String m=matcher.group(1);//从第一个括号开始匹配
		if(m!=null)
			if(matcher.group(2)==null)
			{
				Token token;
				if(matcher.group(3)!=null)//匹配为数字
					token=new NumToken(lineNo,Integer.parseInt(m));
				else if (matcher.group(4)!=null) {//匹配为字符串
					token=new StrToken(lineNo,toStringLiteral(m));
				}
				else {//匹配为标记符
					token=new IdToken(lineNo,m);
				}
				queue.add(token);//储存区加入此句
			}
		
	}
	protected String toStringLiteral(String s)//判断换行符及注释，并采取操作
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
