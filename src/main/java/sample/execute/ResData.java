package sample.execute;

public class ResData {

	/** メソッドの実行結果のメッセージ */
	private String resultMsg =null;
	/** メソッドの実行結果 */
	private boolean jnaResult =false;
	/** メソッド実行時のExceptionメッセージ */
	private String jnaExceptionMessage = null;
	
	/*　統合開発環境で出力したメソッド */
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
	
	
}
