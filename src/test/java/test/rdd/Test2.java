package test.rdd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neusoft.bigdata.service.impl.NewHouseService;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewHouseService service=new NewHouseService();
		HashMap<String, Double>result= service.getAVG();
		Iterator<Entry<String, Double>>iterator=result.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Double>item= iterator.next();
//			System.out.println(item.getKey()+item.getValue());
		}
		Gson gson=new Gson();
		String json= gson.toJson(result);
		System.out.println(json);
//		System.out.println("result size:"+result.size());
	}

}
