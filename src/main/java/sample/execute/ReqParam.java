package sample.execute;

import java.util.ArrayList;
import java.util.List;

/**
 * 実行要求するライブラリーの情報を保持するクラス.
 * 
 * @author 
 *
 */
public class ReqParam {

	private final int mParaMax = 10;
	
	/** 実行するDLLファイル名 */
	private String dllname = null;
	/** 本処理として実行するメソッド名 */
	private String methodName = null;
	/** 実行するメソッドの引数　*/
	private List<MethodParam> mParamList = new ArrayList<MethodParam>(mParaMax);	
	/** 後処理として実行するメソッド名.
	 *  本処理として実行するメソッドの引数で取得したPointerByReference opVal を Pointer p = opVal.getValue() として
	 *  後処理では、Pointer p を引数として実行する  
	 *  */
	private String afterMethodName = null;
	/** 自メソッドでエラーが発生した場合の次のメソッドの実行判断 */
	private boolean nextNeed = false;

	/**
	 * 
	 * このメソッドは、Spring boot で、json->Object するときに必要なので消さないこと
	 */
	public ReqParam(){
		
	}
	public ReqParam( String dllname, String methodName, List<MethodParam> mParaList, boolean nextNeed ){
		setDllname(dllname);
		setMethodName(methodName);
		setmParamList(mParaList);
		setNextNeed(nextNeed);
	}
	
	/*　統合開発環境で出力したメソッド */
	public String getDllname() {
		return dllname;
	}
	public void setDllname(String dllname) {
		this.dllname = dllname;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<MethodParam> getmParamList() {
		return mParamList;
	}
	public void setmParamList(List<MethodParam> mParamList) {
		this.mParamList = mParamList;
	}
	public boolean isNextNeed() {
		return nextNeed;
	}
	public void setNextNeed(boolean nextNeed) {
		this.nextNeed = nextNeed;
	}

	//--レスポンス用の 変数
	/** メソッドの実行結果のメッセージ */
	private String resultMsg =null;
	/** メソッドの実行結果 */
	private boolean jnaResult =false;
	/** メソッド実行時のExceptionメッセージ */
	private String jnaExceptionMessage = null;
	
	
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public boolean isJnaResult() {
		return jnaResult;
	}
	public void setJnaResult(boolean jnaResult) {
		this.jnaResult = jnaResult;
	}
	public String getJnaExceptionMessage() {
		return jnaExceptionMessage;
	}
	public void setJnaExceptionMessage(String jnaExceptionMessage) {
		this.jnaExceptionMessage = jnaExceptionMessage;
	}
	public String getAfterMethodName() {
		return afterMethodName;
	}
	public void setAfterMethodName(String afterMethodName) {
		this.afterMethodName = afterMethodName;
	}
}
