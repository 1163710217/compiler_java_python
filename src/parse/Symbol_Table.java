package parse;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*
 * ���ű���Ҫ���ڹ������������ͬʱ��˶�����
 */

public class Symbol_Table {
	Map<String, Table> m=null;
	public Symbol_Table(){
	  m=new HashMap<String,Table>();
  }
	public void put(String key,Table value){
//		boolean flag=flase;
		if(m!=null){
			
			if(m.get(key)!=null){
				value.nexttable=m.get(key);
				m.put(key, value);
			}else{
				m.put(key, value);
			}
		}
	}
	public void delete(String key){
		if(m!=null){
			if(m.get(key).nexttable!=null){
				m.put(key, m.get(key).nexttable);
			}else{
				m.remove(key);
			}
		}
	}
	//��������ȷ�����ر�������
	public type_cal check_def(String key){
		boolean flag=false;
		type_cal t=null;
		if(m!=null){
			flag=m.containsKey(key);
			if(flag){
				m.get(key).apoint++;
				t=m.get(key).type;
			}
		}
		return t;
	}
	
	public int get_appoint(String key){
		return m.get(key).apoint;
	}
	public List<Integer> check_Dimension(int dim){
		//ɾ�����ڵ�ǰά���ı������壬ȷ��������������
		Iterator it=m.entrySet().iterator();
		List<String> list=new ArrayList<String>();
//		hashMap��֧���ڱ������ݵ�ʱ���޸����ݣ���Ϊ�����������Լ�����ݵĴ�С������б仯�����׳��쳣
		while(it.hasNext()){
			
			Map.Entry<String, Table> map=(Entry<String, Table>) it.next();
//			System.out.println(map.getKey());
//			if(m.get(map.getKey())!=null)
			if(m.get(map.getKey()).Dimension>dim){
//				delete(map.getKey());
//				System.out.println("---"+map.getKey());
				list.add(map.getKey());
			}
		}
		int i=0;
		List<Integer> list_int=null;
		while(i<list.size()){
			list_int=new ArrayList<Integer>();
			list_int.add(check_appoint(list.get(i)));
			
			delete(list.get(i));
			i++;
		}
		return list_int;
	}
	public int check_appoint(String key){
		int a=-1;
		if(m!=null){
			if(m.get(key)!=null){
				a=m.get(key).apoint;
			}
		}
		if(a==0){
			
		}
		return m.get(key).Dimension;
	}
}

class Table{
	type_cal type;
	int apoint=0;   //���ø���
	int Dimension=1; //ά��
	int code_index=0;   //���ڼ�¼�����������������
	Table nexttable=null;
}