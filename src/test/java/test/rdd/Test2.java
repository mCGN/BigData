package test.rdd;

import java.util.List;
import com.google.gson.Gson;
import com.neusoft.bigdata.domain.KeyValue;
import com.neusoft.bigdata.service.impl.NewHouseService;

public class Test2 {

	public static void main(String[] args) {
		NewHouseService service=new  NewHouseService();
		List<KeyValue<Integer>>result= service.getAvg();
		Gson gson=new Gson();
		String json= gson.toJson(result);
		System.out.println(json);
		
//		service.getNumByTag("轨交房");	
		
	}

}
