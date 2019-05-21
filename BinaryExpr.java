package diamond.ast;

import java.util.List;

//双目运算符节点，非叶子节点
public class BinaryExpr extends ASTList{
	public BinaryExpr(List<ASTree> c) {super(c);}//c初始化容器使其储存表达式
	public ASTree left() {return child(0);}//返回当前节点左子树的值
	public String operator() {
		return ((ASTLeaf)child(1)).token().getText();//提取出操作符
	}
	public ASTree right() {return child(2);}//返回当前节点右子树的值
}
