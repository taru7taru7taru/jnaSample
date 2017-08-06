package sample.execute;

/**
 * Jna用のメソッドの引数を保持するクラス
 * 
 * @author 
 *
 */
public class MethodParam {
	/** 実行するメソッドの引数の型 */
	public enum methodParaType{StringType,PointerByReferenceType,intType,doubleType}	//もっとたくさん種類があるがとりあえず...

	/** String型の引数の値 */
	private String paraStringVal = null;
	/** int型の引数の値 */
	private int paraIntVal = 0;
	/** double型の引数の値 */
	private double paradoubleVal = 0;
	/** 一つ前の実行メソッドの引数を使用する場合の　引数の順番 1始まり */
	private int prevMethodNo = 0;
	/** 引数のタイプ */
	private methodParaType mpType ;

	
	public String getParaStringVal() {
		return paraStringVal;
	}
	public void setParaStringVal(String paraStringVal) {
		this.paraStringVal = paraStringVal;
	}
	public int getParaIntVal() {
		return paraIntVal;
	}
	public void setParaIntVal(int paraIntVal) {
		this.paraIntVal = paraIntVal;
	}
	public methodParaType getMpType() {
		return mpType;
	}
	public void setMpType(methodParaType mpType) {
		this.mpType = mpType;
	}
	public int getPrevMethodNo() {
		return (prevMethodNo-1);
	}
	public void setPrevMethodNo(int prevMethodNo) {
		this.prevMethodNo = prevMethodNo;
	}
	public double getParadoubleVal() {
		return paradoubleVal;
	}
	public void setParadoubleVal(double paradoubleVal) {
		this.paradoubleVal = paradoubleVal;
	}
	
}
