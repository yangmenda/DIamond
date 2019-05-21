package diamond.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree{
	protected List<ASTree> children;//将拆分的语句放入list中
	public ASTList (List<ASTree> list) {children=list;}
	
	public ASTree child(int i) {return children.get(i);}//输出指定位置孩子节点
	public int numChildren() {return children.size();}//输出孩子节点数量
	public Iterator<ASTree> children(){return children.iterator();}//返回孩子迭代器
	public String toString()//从树中读取tokens，将其转化为字符串
	{
		StringBuilder builder=new StringBuilder();
		builder.append('(');//在第一项前加入左括号
		String sep="";//sep为临时串
		for(ASTree t:children)
		{
			builder.append(sep);
			sep=" ";
			builder.append(t.toString());
		}
		return builder.append(')').toString();
	}
	public String location()//输出当前串的位置
	{
		for(ASTree t:children)
		{
			String s=t.location();
			if(s!=null)return s;
		}
		return null;
	}
	
}
