package sample.execute;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
/*@Controller
@SessionAttributes(value = "ResData")*/
public class ExecController {
	
/*    @ModelAttribute("ResData")
    ResData inputForm() {
        System.out.println("create inputForm");
        return new ResData();
    }    
	@RequestMapping("/")
	public String index( ResData test){
		
		return "hello !!! currentTimeMillis:"+ System.currentTimeMillis();
	}*/

	private Object lock = new Object(); 
	@RequestMapping("/execute")
	public List<ReqParam> execute(@RequestBody List<ReqParam> reqbodyList ) {
		List<ReqParam> ret = null;
		DllServant dllServant = new DllServant();
		
		synchronized(lock){
			ret = dllServant.execute( reqbodyList );
		}
		
	    return ret;
    }
    
}
