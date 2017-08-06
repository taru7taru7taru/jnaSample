/**
 * 
 */
package sample.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.jna.Function;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * jna 実験ソース
 * 参考サイト：http://qiita.com/halifax/items/78e94cfc6c4ae232932a
 * 			https://github.com/java-native-access/jna
 * 
 * @author 
 *
 */
public class DllServant {

	/**
	 * 
	 * @param rPara 実行要求オブジェクト
	 * @return ReqParamオブジェクト
	 */
	public List<ReqParam> execute( List<ReqParam> rParaList ){
		Map<String,Function> funcList = new HashMap<String, Function>();
		//a //ArrayList<ArrayList<Object>> workObjList = new ArrayList<ArrayList<Object>>();
		
		//Object[] prevObj = null;
		Iterator<ReqParam> itParaList = rParaList.iterator();
        PointerByReference pByRef =  new PointerByReference();
		//PointerByReference pByRef = null;
		for( int j=0; itParaList.hasNext();j++ ){
			ReqParam rPara = itParaList.next();
	        // Function
			Function function = funcList.get(rPara.getDllname());
			if( function == null ){
				function = Function.getFunction(rPara.getDllname(),rPara.getMethodName(),Function.ALT_CONVENTION );
				funcList.put(rPara.getDllname(),function );
			}
	        List<MethodParam> mParamList = rPara.getmParamList();
	    	Object[] workObj = new Object[mParamList.size()];
	        //a //ArrayList mpObj = new ArrayList<Object>();
	
	        Iterator<MethodParam> it = mParamList.iterator();
	        for( int i=0; it.hasNext() == true; i++ ){
	        	MethodParam m = it.next() ;
	        	switch(m.getMpType()){
	        		case StringType:
	        			workObj[i] = m.getParaStringVal();
	        			//a //mpObj.add(i, m.getParaStringVal());
	        			break;
	        		case PointerByReferenceType:
	        	        //pByRef = null;
	        			workObj[i] = pByRef;
	        			//workObj[i] = new String();
	        			//a //mpObj.add(i, pByRef);
	        			break;
	        		case intType:
	        			workObj[i] = m.getParaIntVal();
	        			//a //mpObj.add(i, m.getParaIntVal());
	        			break;
	        		case doubleType:
	        			workObj[i] = m.getParadoubleVal() ;
	        			//a //mpObj.add(i, m.getParaIntVal());
	        			break;
	        		default:
	        			break;
	        	}
	        }
	        //本処理メソッドの実行
	        function.invoke( workObj );
	      //a //function.invoke( mpObj.toArray() );
	        
	        String workStr = null ;

	        if( pByRef != null){
		        Pointer p = pByRef.getPointer();
		        workStr = p.getString(0);
		        /*
		         * ↓Ｃ言語の関数の呼び方の問題だったっぽい...
		         * これをしても停止時にエラーにはなる...
		         * # A fatal error has been detected by the Java Runtime Environment:
					#
					#  EXCEPTION_ACCESS_VIOLATION (0xc0000005) at pc=0x0000000076d74dc7, pid=292, tid=0x00000000000009bc
					#
					# JRE version: Java(TM) SE Runtime Environment (8.0_111-b14) (build 1.8.0_111-b14)
					# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.111-b14 mixed mode windows-amd64 compressed oops)
					# Problematic frame:
					# C  [ntdll.dll+0x24dc7]
		         */
		        //pByRef = null;
		        //p.clear(workStr.length());
		        //p=null;
	        }
	        System.out.println(" function.size = " +function.SIZE );
	        System.out.println("DllServant tester() :"+ workStr);
	        //後処理メソッドの実行
/*	        if(rPara.getAfterMethodName() != null){
		        function = Function.getFunction(rPara.getDllname(),rPara.getAfterMethodName() ,Function.C_CONVENTION);
		        Pointer po = pByRef.getValue();
		        function.invoke(new Object[]{po});  //ほんとは引数がいる
	        }*/
	        //実行結果をセットする
	        rPara.setJnaResult(true);
	        rPara.setResultMsg("要求受付 tester() :"+ workStr);
	        
	      //a //workObjList.add(mpObj);
	        //System.out.println("  "+prevObj[1]+", "+prevObj[2]);
		}
		return rParaList;
	}
	
	/**
	 * 
	 * @param rPara 実行要求オブジェクト
	 * @return ReqParamオブジェクト
	 */
	public List<ReqParam> executeOLD( List<ReqParam> rParaList ){
		
		//Object[] prevObj = null;
		Iterator<ReqParam> itParaList = rParaList.iterator();
		for( int j=0; itParaList.hasNext();j++ ){
			ReqParam rPara = itParaList.next();
	        // Function
	        Function function = Function.getFunction(rPara.getDllname(),rPara.getMethodName(),Function.C_CONVENTION);
	        
	        List<MethodParam> mParamList = rPara.getmParamList();
	    	Object[] workObj = new Object[mParamList.size()];
	        PointerByReference pByRef = new PointerByReference();
	
	        Iterator<MethodParam> it = mParamList.iterator();
	        for( int i=0; it.hasNext() == true; i++ ){
	        	MethodParam m = it.next() ;
	        	switch(m.getMpType()){
	        		case StringType:
	        			workObj[i] = m.getParaStringVal();
	        			break;
	        		case PointerByReferenceType:
	        			workObj[i] = pByRef;
	        			break;
	        		case intType:
	        			workObj[i] = m.getParaIntVal();
	        			break;
	        		/*case prevMethodParaType:		//↓Objectのコピーが上手く行かない...
	        			workObj[i] = prevObj[ m.getPrevMethodNo() ] ;
	        			System.out.println(" forDebug :" + workObj[i]);
	        			break;
	        			*/
	        		default:
	        			break;
	        	}
	        }
	        function.invoke(workObj);
	        Pointer p = pByRef.getPointer();
	        String workStr = p.getString(0);
	        System.out.println("tester() :"+ workStr);
	        rPara.setJnaResult(true);
	        rPara.setResultMsg("要求受付 tester() :"+ workStr);
/*	    	prevObj = null;	        
	        prevObj = workObj.clone();
	        System.out.println("  "+prevObj[1]+", "+prevObj[2]);*/
		}
		return rParaList;
	}
	
	//以下のメソッドはテスト用
	public void tester(){
        String libraryName  = Platform.isWindows() ? "msvcrt" : "c";
        String functionName = "sprintf";

        // Function
        Function function = Function.getFunction(
            libraryName,
            functionName,
            Function.C_CONVENTION);
        
        //参考サイト：http://www.atmarkit.co.jp/fjava/special/jna/jna_2.html
        PointerByReference pByRef = new PointerByReference();
        String workStr = new String();
        int worki = 1000;
        Pointer p = null ;
        // Invoke
        /* sprintf(char *buffer, const char *format, argument) */
        function.invoke(new Object[]{ pByRef,"test [%d]\n", worki });
        p = pByRef.getPointer();
        workStr = p.getString(0);
        System.out.println("tester() :"+ workStr);
	}
	public void tester2(int sleepTime){
        String libraryName  = "Kernel32";
        String functionName = "Sleep";

        // Function
        Function function = Function.getFunction(
            libraryName,
            functionName,
            Function.C_CONVENTION);
        
        //参考サイト：http://www.atmarkit.co.jp/fjava/special/jna/jna_2.html
        int worki = 5000;
        // Invoke
        /* sprintf(char *buffer, const char *format, argument) */
        long start = System.currentTimeMillis();
        function.invoke(new Object[]{ sleepTime });
        System.out.println("tester()  start :"+start);
        long end = System.currentTimeMillis();
        System.out.println("tester()  経過時間:"+(end - start));
	}
	
}
