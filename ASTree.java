package diamond.ast;//语法树基本类

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree>{
	public abstract ASTree child(int i);
	public abstract int numChildren();
	public abstract Iterator<ASTree> children();
	public abstract String location();
	public Iterator<ASTree> iterator(){return children();}//遍历所有子节点的迭代器
}
