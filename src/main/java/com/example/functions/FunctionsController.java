package com.example.functions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FunctionsController {
	
	 @Autowired
	 TokenizerService tokenService;
	 AnalyzerService analyzer;
	
	 @RequestMapping(value="/", method=RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	 @ResponseBody
	 public String result(@ModelAttribute Statement mychar, Model model, HttpServletResponse response,
	         @RequestParam(value="action", required=true) String action) {
		 
		 model.addAttribute("mystatement", mychar);
		 String expresion1 = mychar.content1;
		 String expresion2 =mychar.content2;
			
		tokenService = new TokenizerService();
		analyzer= new AnalyzerService ();
		String result = "";
		
	    switch(action) {
	         case "Relations":
	        	 List<String> domain = tokenService.getOperands(expresion1);
	     		 List<String> codomain = tokenService.getOperands(expresion2);
	     		 analyzer = new AnalyzerService(domain,codomain);
	             result = analyzer.getRelations();
	             break;
	         case "Functions":
	        	 List<String> domain1 = tokenService.getOperands(expresion1);
	     		 List<String> codomain1 = tokenService.getOperands(expresion2);
	     		 analyzer = new AnalyzerService(domain1,codomain1);
	        	 result = analyzer.getFunctions();
	             break;
	         case "Injectives":
	        	 List<String> domain2 = tokenService.getOperands(expresion1);
	     		 List<String> codomain2 = tokenService.getOperands(expresion2);
	     		 analyzer = new AnalyzerService(domain2,codomain2);
	        	 result = analyzer.getInjectives();
	             break;
	         case "Surjectives":
	        	 List<String> domain3 = tokenService.getOperands(expresion1);
	     		 List<String> codomain3 = tokenService.getOperands(expresion2);
	     		 analyzer = new AnalyzerService(domain3,codomain3);
	        	 result = analyzer.getSurjectives();
	             break;    
	         case "Bijections":
	        	 List<String> domain4 = tokenService.getOperands(expresion1);
	     		 List<String> codomain4 = tokenService.getOperands(expresion2);
	     		 analyzer = new AnalyzerService(domain4,codomain4);
	        	 result = analyzer.getBijectives();
	             break;
	         case "Evaluate":
	        	 result = analyzer.getIndex2();
	        	 try {
	        		 response.sendRedirect("/write");
	        	 } catch (IOException e) {
	        		 e.printStackTrace();
	        	 }
	             break;
	     }
		return result;
	 }
	
	
	@RequestMapping(value="/expression", method=RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String evaluateRelationship(@ModelAttribute Expression expression, Model model1,
			@RequestParam(value="action", required=true) String action) {
	
		if(action.equals("Calculate")) {
			model1.addAttribute("expression", expression);
			String expresion1 = expression.content1;
			String expresion2 =expression.content2;
			String expresion3 =expression.content3;
			
			System.out.println(expresion1);
			System.out.println(action);
			 
			tokenService = new TokenizerService();
			List<String> domain = tokenService.getOperands(expresion1);
			List<String> codomain = tokenService.getOperands(expresion2);	
			List<String> myinput = tokenService.getElementsFromPairs(expresion3);
			analyzer = new AnalyzerService(domain,codomain);
		
			return analyzer.evaluateExpression(myinput,domain,codomain);
		}
		return "hi";
	}
	
	
	@GetMapping("/")
	public String statementForm(Model model) {
		model.addAttribute("mystatement", new Statement());
		return "index";
	}
	
	
	@GetMapping("/write")
	public String statementForm2(Model model) {
		model.addAttribute("expression", new Expression());
		return "index2";
	}
		
}
