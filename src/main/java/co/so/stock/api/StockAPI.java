package co.so.stock.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.so.conn.PropertiesPair;
import co.so.stock.vo.TempStockVO;

public class StockAPI {
	
	public static List<TempStockVO> getStock(String basDt) {
		List<TempStockVO> list = new ArrayList<TempStockVO>();
		String serviceKey = "4zF5Tq3qAwcMj8HlzZjeQJVuoh6YN9Es7vn8HDYB0iTY0kH/BVniquL0UWnOmhFnVSll98CbDRumTgaox2UUSQ==";
		String serviceURL = "https://api.odcloud.kr/api/GetStockSecuritiesInfoService/v1/getStockPriceInfo?";
		String resultType = "json";
		String beginTrqu = "10000000";
		String numOfRows = "100";
		
		String paramURL = "";
		String requestURL = "";
		
		List<PropertiesPair> params = new ArrayList<PropertiesPair>();
		params.add(new PropertiesPair("serviceKey", serviceKey));
		params.add(new PropertiesPair("resultType", resultType));
		params.add(new PropertiesPair("beginTrqu", beginTrqu));
		params.add(new PropertiesPair("numOfRows", numOfRows));
		params.add(new PropertiesPair("basDt", basDt));
		
		StringBuilder sb = new StringBuilder();
		
		try {
			paramURL = PropertiesPair.getQuery(params);
			
			requestURL = serviceURL + paramURL;
			URL url = new URL(requestURL);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json");
			
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String line;
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
			}else {
				System.out.println(con.getResponseMessage());
			}
			
			String jsonResult = sb.toString();
			list = getStockData(jsonResult);
			
			con.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private static List<TempStockVO> getStockData(String jsonResult){
		JSONParser parser = new JSONParser();
		List<TempStockVO> list = new ArrayList<TempStockVO>();
		try {
			JSONObject stockData = (JSONObject) parser.parse(jsonResult);
			JSONObject response = (JSONObject) stockData.get("response");
			JSONObject body = (JSONObject) response.get("body");
			JSONObject items = (JSONObject) body.get("items");
			JSONArray item = (JSONArray) items.get("item");
			if(item != null) {
				for(int i = 0; i < item.size(); i++) {
					JSONObject data = (JSONObject) item.get(i);
					TempStockVO vo = new TempStockVO();
					vo.setTempStockNum((String)data.get("isinCd"));
					vo.setTempStockName((String)data.get("itmsNm"));
					vo.setTempStockPrice(Integer.parseInt((String)data.get("clpr")));
					
					list.add(vo);
				}
			}else {
				return null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
		
	}
}
