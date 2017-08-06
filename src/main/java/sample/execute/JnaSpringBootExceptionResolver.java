package sample.execute;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JnaSpringBootExceptionResolver implements HandlerExceptionResolver {

	/**
	 * MultipleException が発生した場合の処理を行う
	 * 参考URL https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html
	 * 
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object obj,
			Exception e) {
		// TODO Auto-generated method stub
		ModelAndView modelAndView = new ModelAndView();
		if (e instanceof MultipartException && e.getCause() instanceof IllegalStateException
				&& e.getCause().getCause() instanceof FileSizeLimitExceededException){
            modelAndView.addObject("status", "500");
            modelAndView.addObject("error", e);
            modelAndView.addObject("message", "file size over!!!");            
            modelAndView.setViewName("error");
            modelAndView.addObject("timestamp", new Date());
		}else{
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}

}
