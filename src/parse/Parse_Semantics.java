package parse;

import java.util.ArrayList;
import java.util.List;

import analyize_error.Error;

public class Parse_Semantics {
	Exp_func exp_func;
	Symbol_Table st=null;
	Error error=null;
	int count=0,Lab=0 ,s=1; //count�������, s ά��
	List<Exp_Operate> list_op=null;
	public Parse_Semantics(Exp_func func){
		this.exp_func=func;
		error=new Error();
		list_op=new ArrayList<Exp_Operate>();
		st=new Symbol_Table();  //�������ű�
		parse_se();
		if(count==0){
		int i=0;
		while(i<list_op.size()){
			System.out.println(list_op.get(i).op.toString()+"---"+list_op.get(i).s+"----"+list_op.get(i).d+"-----"+list_op.get(i).result);
			i++;
			}
		}
		
		
		
	}
	public void parse_se(){
		int index=0;
		if(exp_func!=null){
			if(exp_func.list_exp!=null){
				while(index<exp_func.list_exp.size()){
					Exp exp=exp_func.list_exp.get(index);
					switch(exp.type){
					case WHI : parse_sema_whi((Exp_whi)exp);break;
					case IFE :parse_sema_ife((Exp_ife)exp);;break;
					case NUM :
					case STRING:
					case BOOL: parse_sema_typedef((Exp_type)exp,s); break;
					default:parse_sema_mark((Exp_mark)exp);break;
					}
					index++;
				}
				
			}
		}
	}
	public void parse_sema_whi(Exp_whi whi){
		s++;
		Exp_Operate opter=new Exp_Operate();
		opter.op=type_op.LABEL;
		opter.result=getLabel();
		list_op.add(opter);
		
		Exp_Operate opt=new Exp_Operate();
		Exp_cond condition=whi.condition;
		Exp_mark m1=(Exp_mark)condition.left;
		Exp_mark m2=(Exp_mark)condition.right;
		
		opt.op=get_compare_mark(condition.exp_comp);
		opt.s=backString(m1, type_cal.NUM);
		opt.d=backString(m2, type_cal.NUM);
		
		opt.result=getLabel();  //��ת��ǩ
		list_op.add(opt);
		
		Exp_B exp=(Exp_B) whi.next;
		exec_exp_B(exp,null);
		
		Exp_Operate op1=new Exp_Operate();
		op1.op=type_op.JMP;
		op1.result=opter.result;
		list_op.add(op1);
		
		Exp_Operate op2=new Exp_Operate();
		op2.op=type_op.LABEL;
		op2.result=opt.result;
		list_op.add(op2);
		s--;
		
		st.check_Dimension(s);
	}
	public void parse_sema_ife(Exp_ife ife){
		s++;
		Exp_Operate opt=new Exp_Operate();
		
		Exp_cond condition=ife.condition;
		Exp_mark m1=(Exp_mark)condition.left;
		Exp_mark m2=(Exp_mark)condition.right;
		
		opt.op=get_compare_mark(condition.exp_comp);
		if(m1!=null){
		opt.s=backString(m1, type_cal.NUM);
		}
		if(m2!=null){
		opt.d=backString(m2, type_cal.NUM);
		}
		
		opt.result=getLabel();  //��ת��ǩ
		list_op.add(opt);
		
		Exp_B exp_left=(Exp_B) ife.left;
		Exp_B exp_right=(Exp_B)ife.right;
		if(exp_left!=null){
		exec_exp_B(exp_left, null);
		}
//		�����else
		if(exp_right!=null){
		exec_exp_B(exp_right, opt.result);
		}else{
			Exp_Operate operate=new Exp_Operate();
			operate.op=type_op.LABEL;
			operate.result=opt.result;
			list_op.add(operate);
		}
		s--;
		
		List<Integer>list=st.check_Dimension(s);  //������Щ������ĿΪ0�ı���
//		�Ż�����
		if(list!=null){
			int i=0;
			while(i<list.size()){
				list_op.remove(list.get(i));
				i++;
			}
		}
	}
	
	public void parse_sema_typedef(Exp_type typedef,int Dimension){
		Exp_Operate exp_op=new Exp_Operate();
		exp_op.op=type_op.DEFINE;
//		List<Table> list_tab=new ArrayList<Table>();
		Table tab=new Table();
		tab.Dimension=Dimension;
		if(typedef.type==type_cal.NUM){
			exp_op.s="num";
//			tab.apoint=0;
			tab.type=type_cal.NUM;
		}else if(typedef.type==type_cal.STRING){
			tab.type=type_cal.STRING;
			exp_op.s="string";
		}else{
			exp_op.s="bool";
			tab.type=type_cal.BOOL;
		}
//		list_tab.add(tab);
		Exp_mark mark=(Exp_mark) typedef.Id;
		
		exp_op.result=mark.value;
		tab.code_index=list_op.size();
		list_op.add(exp_op);
//		System.out.println("define var.."+mark.value);
		st.put(mark.value, clone_tab(tab));
		if(mark.list_value!=null){
			int i=0;
			while(i<mark.list_value.size()){
				Exp_mark m=(Exp_mark) mark.list_value.get(i);
				Table t=clone_tab(tab);
				
				t.code_index=list_op.size();
				
				st.put(m.value,t);
		
				Exp_Operate operate=clone_Operate(exp_op);
				operate.result=m.value;
				list_op.add(operate);
//				System.out.println("define var.."+m.value);
				i++;
			}
		}	
	}
	public void parse_sema_mark(Exp_mark exp_mark){
		//a=a+b  �Ⱥ���ߵı����Ǳ����������ǳ�����������Ҫƥ�����ͣ��ұߵĿ���ʹ������������ƥ������
		if(exp_mark.type==type_cal.ID){
			if(check_define(exp_mark.value)==type_cal.NUM ){
				Exp_Operate operate=null;
				operate=new Exp_Operate();
			    if(exp_mark.got!=null){

					Exp_Operate op_send=new Exp_Operate();
				
					Exp_calcul c=(Exp_calcul)exp_mark.got;
					operate.s=
						parse_sema_mark_((Exp_calcul)c.left);				
					while(c.right!=null){
						c=(Exp_calcul) c.right;
					if(c.type==type_cal.ADD){
					    operate.op=type_op.ADD;
//					    System.out.println("+");
				    }else if(c.type==type_cal.SUB){
					    operate.op=type_op.SUB;
//					    System.out.println("-");
				    }
				     operate.d=
						parse_sema_mark_((Exp_calcul)c.left);
				operate.result="$";
				list_op.add(operate);
				operate=clone_Operate(operate);
				operate.s=operate.result;
				}
				op_send.op=type_op.SEND;
				
				Exp_calcul cal=(Exp_calcul)exp_mark.got;
				op_send.s=operate.s;
				
				op_send.result=exp_mark.value;
				list_op.add(op_send);					
			}
		}else if(check_define(exp_mark.value)==type_cal.STRING){
			count++;
			error.error_print(9, "string", exp_mark.line, exp_mark.row);
			
		}else if(check_define(exp_mark.value)==type_cal.BOOL){
			count++;
			error.error_print(9, "bool", exp_mark.line, exp_mark.row);
		}else{
			count++;
			error.error_print(9, null, exp_mark.line, exp_mark.row);
		}
	}else{
		count++;
		error.error_print(8, exp_mark.type.toString(), exp_mark.line, exp_mark.row);
	}
	}
	
	
	public String parse_sema_mark_(Exp_calcul exp_cal){
//		Exp_calcul exp_c1=(Exp_calcul) exp_cal.left;
		Exp_Operate operate=null;
		Exp_mark mark=(Exp_mark)exp_cal.left;
		operate=new Exp_Operate();
//		if(check_define(mark.value)!=type_cal.NUM && mark.type!=type_cal.NORMAL_NUM){
//			error.error_print(9, null, mark.line, mark.row);
//			count++;
//		}
		//���ͼ��
		backString(mark, type_cal.NUM);
		
//		System.out.println(mark.value);
		operate.s=mark.value; 
		
		while(exp_cal.right!=null){
			Exp_calcul cal=((Exp_calcul)exp_cal.right);
			Exp_mark m=(Exp_mark) cal.left; 
//			if(m.type!=type_cal.NORMAL_STRING){
//			   if(check_define(m.value)!=type_cal.NUM && m.type!=type_cal.NORMAL_NUM){
//				error.error_print(9,m.type.toString(), m.line, m.row);
//				count++;
//			   }else{
//				   
//			   }
//			}else{
//				count++;
//				error.error_print(9, m.type.toString(), m.line, m.row);
//			}
//			���m�Ķ��塣
			backString(m, type_cal.NUM);
			
			if(cal.type==type_cal.MUL){
				operate.op=type_op.MUL;
//				System.out.println("*"+m.value);
			}else if(cal.type==type_cal.DIV){
				operate.op=type_op.DIV;
//				System.out.println("/"+m.value);
			}
			operate.d=m.value;
			list_op.add(operate);
			operate.result="$";
			operate=clone_Operate(operate);
			operate.s=operate.result;
			exp_cal=(Exp_calcul) exp_cal.right;
		}
		String s=operate.result;
		if(operate.result==null){
			s=operate.s;
		}
		return s;   //���ճ˳���������
			
	}
	public void exec_exp_B(Exp_B exp_b,String label){
		Exp_Operate operate=null;
		if(exp_b.list_exp!=null){
			if(label!=null){
				operate=new Exp_Operate();
				operate.op=type_op.LABEL;
				operate.result=label;
				list_op.add(operate);
				
			}
			int index=0;
			while(index<exp_b.list_exp.size()){
				Exp exp=exp_b.list_exp.get(index);
//				System.out.println(exp.type.toString());
				switch(exp.type){
					case WHI : parse_sema_whi((Exp_whi)exp);break;
					case IFE :parse_sema_ife((Exp_ife)exp);;break;
					case NUM :
					case STRING:
					case BOOL:parse_sema_typedef((Exp_type)exp,s); break;
					default:parse_sema_mark((Exp_mark)exp);break;
				}
				
				index++;
			}
		}
	}
	
	//ͳһ����������
	public String backString(Exp_mark mark,type_cal match_type){
//		String s=null;
//		���ȼ���Ƿ��Ǳ��������������ֳ���
		if(mark.type==type_cal.ID || mark.type==type_cal.NORMAL_NUM){
			type_cal t=check_define(mark.value);
			if(t==match_type ||mark.type==type_cal.NORMAL_NUM){
//				s=mark.value;
			}else{
				if(t==null){//����δ����,Ҳ���ǳ���
					error.error_print(3, mark.value, mark.line, mark.row);
				}else{
				count++;
				error.error_print(9, t.toString(), mark.line, mark.row);
				}
			}
			
		}else{
			
		//������ַ�����
				count++;
				error.error_print(9, mark.type.toString(), mark.line, mark.row);
			
		}
		return mark.value;
//		return s;
	}
	//����������ת�ı�ǩ
	public String getLabel(){
		Lab++;
		return "Label"+Lab;
	}
	//ר��ʶ��ȽϷ���
	public type_op get_compare_mark(Exp exp){
		type_op comp_op=null;
		switch (exp.type) {
		case BIG:
			comp_op=type_op.CJMP_B;
			break;
		case LITTLE:comp_op=type_op.CJMP_L;
			break;
		case EQUAL:comp_op=type_op.CJMP_E;
			break;
		case BIG_EQUAL:comp_op=type_op.CJMP_B_E;
			break;
		case LITTLE_EQUAL:comp_op=type_op.CJMP_L_E;
			break;
		default:
			break;
		}
		return comp_op;
	}
	public Table clone_tab(Table tab){
		Table t=new Table();
		t.Dimension=tab.Dimension;
		t.type=tab.type;
		t.code_index=tab.code_index;
		return t;
	}
	public Exp_Operate clone_Operate(Exp_Operate oper){
		Exp_Operate ope=new Exp_Operate();
		ope.op=oper.op;
		ope.s=oper.s;
		ope.d=oper.d;
		ope.result=oper.result;
		return ope;
	}
	
	//�����ļ������Ķ���
	public type_cal check_define(String key){
		return st.check_def(key);
	}
}
//����ĳ��α������Ϊ���
enum type_op{ADD,SUB,MUL,DIV,JMP,LABEL,CJMP_E,CJMP_B,CJMP_L,CJMP_B_E,CJMP_L_E,SEND,DEFINE}
class Exp_Operate{
	type_op op;   //������
	String s;    //Դ������1
	String d;   //Դ������2
	String result;   //������
}
