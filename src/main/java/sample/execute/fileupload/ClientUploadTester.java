package sample.execute.fileupload;

import java.net.URI;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class ClientUploadTester {

	RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientUploadTester cluploadTest = new ClientUploadTester();
		cluploadTest.tester();
	}

	public void tester(){
		URI uri= URI.create("http://localhost:8080/upload/files");
		
		//マルチパートリクエストとして送信するデータを格納するためにMultiValueMapを生成する
		MultiValueMap<String, Object> multiPartBody = new LinkedMultiValueMap<>();
		//パラメータ名をキーに指定して、アップロードするファイルをMultiValueMapに追加する
		//multiPartBody.add("uploadfile", new FileSystemResource( "X:/gs-spring-boot-complete/src/main/resources/uploadFiles/teraterm-4.93.zip"));
		multiPartBody.add("uploadfile", new ClassPathResource( "/uploadFiles/teraterm-4.93.zip"));
		multiPartBody.add("uploadfile", new FileSystemResource("X:/gs-spring-boot-complete/src/main/resources/uploadFiles/aaaa.txt"));
		multiPartBody.add("uploadfile", new FileSystemResource("X:/gs-spring-boot-complete/src/main/resources/uploadFiles/bbbb.txt"));
		multiPartBody.add("uploadType","test");
		
		RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity
		        .post(uri)
		        .contentType(MediaType.MULTIPART_FORM_DATA)		//Content-Typeヘッダのメディアタイプをmultipart/form-dataに設定する
		        .body(multiPartBody);	//アップロードするファイルが格納されているMultiValueMapをリクエストボディに設定する
		
		ResponseEntity<String> response = (ResponseEntity) restTemplate.exchange(requestEntity, String.class);
		
		HttpStatus hs = response.getStatusCode();
		String retBody = response.getBody();
		System.out.println( hs.value() + "; " + retBody );
		
	}
}
