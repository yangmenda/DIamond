package diamond.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree{
	protected List<ASTree> children;//����ֵ�������list��
	public ASTList (List<ASTree> list) {children=list;}
	
	public ASTree child(int i) {return children.get(i);}//���ָ��λ�ú��ӽڵ�
	public int numChildren() {return children.size();}//������ӽڵ�����
	public Iterator<ASTree> children(){return children.iterator();}//���غ��ӵ�����
	public String toString()//�����ж�ȡtokens������ת��Ϊ�ַ���
	{
		StringBuilder builder=new StringBuilder();
		builder.append('(');//�ڵ�һ��ǰ����������
		String sep="";//sepΪ��ʱ��
		for(ASTree t:children)
		{
			builder.append(sep);
			sep=" ";
			builder.append(t.toString());
		}
		return builder.append(')').toString();
	}
	public String location()//�����ǰ����λ��
	{
		for(ASTree t:children)
		{
			String s=t.location();
			if(s!=null)return s;
		}
		return null;
	}
	
}
