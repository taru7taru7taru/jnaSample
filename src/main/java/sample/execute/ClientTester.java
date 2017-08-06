package sample.execute;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

public class ClientTester {

	RestTemplate restTemplate = new RestTemplate();
	
	
	public static void main(String[] args){
		ClientTester clt = new ClientTester();
		clt.tester();
	}

	public void tester(){
		List<ReqParam> reqParaList = new ArrayList<ReqParam>();
		
		/*
		 *  int sprintf( char *buffer, const char *format [, argument] ... );
		 * 
		 * 
		 */
		for(int i=0; i<1000; i++ ){
			List<MethodParam> modParaList = new ArrayList<MethodParam>();
			MethodParam mPara = new MethodParam();
			mPara.setMpType(MethodParam.methodParaType.PointerByReferenceType );
			modParaList.add(mPara);
	
			MethodParam m2Para = new MethodParam();
			m2Para.setMpType(MethodParam.methodParaType.StringType );
			StringBuilder sb = new StringBuilder();
			sb.append("test aaa ");
			for(int k=0; k< 10;k++){
				sb.append("[%7d]");
			}
			sb.append("\n");
			String str = new String(sb);
			m2Para.setParaStringVal(str);
			modParaList.add(m2Para);

			for(int j=0; j< 10;j++){
				MethodParam m3Para = new MethodParam();
				m3Para.setMpType(MethodParam.methodParaType.intType );
				m3Para.setParaIntVal(j+100*i);;
				modParaList.add(m3Para);
			}
			ReqParam rPara = new ReqParam("msvcrt","sprintf", modParaList,false);
			reqParaList.add(rPara);
		}
/*		ResponseEntity<ReqParam> responseE = 
				restTemplate.postForEntity("http://localhost:8080/execute", rPara , ReqParam.class );
*/
		long start = System.currentTimeMillis();
		//参考資料 https://jira.spring.io/browse/SPR-8263
		ResponseEntity<? extends ArrayList<ReqParam>> responseE = 
				restTemplate.postForEntity("http://localhost:8080/execute", reqParaList , (Class<? extends ArrayList<ReqParam>>)ArrayList.class );
		long end = System.currentTimeMillis();
		
		HttpStatus statusCode = responseE.getStatusCode();
		List<ReqParam> hp = responseE.getBody();
		
		String work = null;
		Iterator<ReqParam> it = hp.iterator();
		while(it.hasNext()){
			//System.out.println( it.next() );
			work= it.next()+"\n";
		}
		
		System.out.println(" 処理時間(msec) :"+ (end -start) +" :"+work);
	}
	
}
