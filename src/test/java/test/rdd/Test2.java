package test.rdd;

import java.util.HashMap;
import com.google.gson.Gson;
import com.neusoft.bigdata.service.impl.NewHouseService;

/**
 * 测试 数据处理
 * @author AE
 *
 */
public class Test2 {

	static Gson gson=new Gson();
	public static void main(String[] args) {
		NewHouseService service=new  NewHouseService();
//		List<KeyValue<Integer>>result= service.getAvg();
//		String json= gson.toJson(result);
//		System.out.println(json);
		
		HashMap<String, Integer>r= service.getMaxAndMinPrice("北京市");
		String a= gson.toJson(r);
		System.out.println(a);
		
//		service.getNumByTag("轨交房");	
		
	}

}
