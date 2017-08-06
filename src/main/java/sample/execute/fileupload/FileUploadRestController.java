package sample.execute.fileupload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルアップロード用のコントローラー
 * @author adon
 *
 */
@RestController
@RequestMapping(value = "upload")
public class FileUploadRestController {

	/**
	 * アップロードファイルを受信するコントローラ.
	 * アップロードファイルが単体の場合。ファイルの保存は、Spring FW のMultipartFileクラスのtransferToメソッドを使用している.
	 * 
	 * @param multipartFile アップロードファイルがSpring FWによってセットされる
	 * @param uploadType　アップロードするファイルのタイプ.ここでは、格納ファイルを保存するディレクトリの特定に利用している.
	 * @return とりあえず文字列を返している.
	 */
	@RequestMapping(value = "files", method = RequestMethod.POST)
	public Object uploadFiles(
			@RequestParam("uploadfile") List<MultipartFile> multipartFiles,
			@RequestParam("uploadType") String uploadType
			){
		
		//ファイルの存在チェック
		//もう少しなかのファイルの存在チェックをしたほうがよいかも知れない..
		if(multipartFiles.isEmpty() == true ){
			return "no files";			
		}
		
		//アップロードファイルの保存ディレクトリを作成する
		//nio を使ってみる
		//参考サイト：http://qiita.com/toastkidjp/items/5500521ff5dc0346c2b1
		//			http://www.ne.jp/asahi/hishidama/home/tech/java/files.html
		Path uploadDir;
		StringBuffer uploadFileName = null;
		try {
			uploadDir = getUploadDir(uploadType);
			for( MultipartFile multipartFile : multipartFiles ){
				uploadFileName = new StringBuffer(uploadDir.toString()).append(File.separator).append(multipartFile.getOriginalFilename() );
				//MultipartFileクラスを使ってファイルを書き込み
				multipartFile.transferTo(Paths.get(uploadFileName.toString()).toFile() );
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "success";
	}

	/**
	 * アップロードファイルを受信するコントローラ.
	 * アップロードファイルが単体の場合。ファイルの保存は、Spring FW のMultipartFileクラスのtransferToメソッドを使用している.
	 * 
	 * @param multipartFile アップロードファイルがSpring FWによってセットされる
	 * @param uploadType　アップロードするファイルのタイプ.ここでは、格納ファイルを保存するディレクトリの特定に利用している.
	 * @return とりあえず文字列を返している.
	 */
	@RequestMapping(value = "file", method = RequestMethod.POST)
	public Object uploadFile(
			@RequestParam("uploadfile") MultipartFile multipartFile,
			@RequestParam("uploadType") String uploadType
			){
		
		//ファイルの存在チェック
		if(multipartFile.isEmpty() == true ){
			return "no files";			
		}
		
		//アップロードファイルの保存ディレクトリを作成する
		//nio を使ってみる
		//参考サイト：http://qiita.com/toastkidjp/items/5500521ff5dc0346c2b1
		//			http://www.ne.jp/asahi/hishidama/home/tech/java/files.html
		Path uploadDir;
		StringBuffer uploadFileName = null;
		try {
			uploadDir = getUploadDir(uploadType);
			//ここでは、アップロードしたファイル名を使用しているが、ディレクトリとラバーサル攻撃を考慮すると、アップロードされた名前は使わないほうが良い..
			uploadFileName = new StringBuffer(uploadDir.toString()).append(File.separator).append(multipartFile.getOriginalFilename() );
			//MultipartFileクラスを使ってファイルを書き込み
			multipartFile.transferTo(Paths.get(uploadFileName.toString()).toFile() );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		/*		
		//アップロードファイルを取得
		byte[] uploadBytes = null;
		try {
			uploadBytes = multipartFile.getBytes();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//アップロードファイルの書き込み
		try( OutputStream os = Files.newOutputStream(Paths.get(uploadFileName.toString()), StandardOpenOption.CREATE_NEW )  ) {
			os.write(uploadBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return "success";
	}

	/**
	 * uploadTypeで指定された種別ごとに、アップロードされたファイルの保存ディレクトリを返却する.
	 * とりあえず仮
	 * 
	 * @param uploadType　アップロードされたファイルの種別
	 * @return アップロードされたファイルの保存ディレクトリのPathインスタンス
	 * @throws IOException 
	 */
	private Path getUploadDir(String uploadType) throws IOException{
		final String baseDir ="X:\\uploadFileDir";
		
		//ディレクトリを決定
		StringBuffer sbwork = new StringBuffer(baseDir).append(File.separator).append(uploadType) ;
		
		//ディレクトリの取得
		int prefix = 0;
		Path uploadDir = Paths.get( sbwork.toString() + "-"+String.valueOf(prefix) );		
		while( Files.exists(uploadDir) == true  ){
			prefix++;
			uploadDir = Paths.get( sbwork.toString() + "-"+String.valueOf(prefix) );	
		}
		Files.createDirectories(uploadDir);
		
		return uploadDir;		
	}
		
	
}
