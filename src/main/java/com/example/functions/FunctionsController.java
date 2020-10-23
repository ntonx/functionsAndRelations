package com.example.functions;

 


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FunctionsController {
	
	 @Autowired
	 TokenizerService tokenService;
	 AnalyzerService analyzer;
/*	 BuilHTMLService buildHTML;
*/	 
	 
	
	 @RequestMapping(value="/", method=RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
		@ResponseBody
	 public String result(@ModelAttribute Statement mychar, Model model, 
	         @RequestParam(value="action", required=true) String action) {
		 
		 model.addAttribute("mystatement", mychar);
		 //String expresion1 = mychar.content1;
		 //String expresion2 =mychar.content2;
		 
		 String expresion1 ="1,2"; //mychar.content1;
		 String expresion2 ="3,4";//;mychar.content2;
			
		tokenService = new TokenizerService();
		analyzer = new AnalyzerService();
		List<String> domain = tokenService.getOperands(expresion1);
		List<String> codomain = tokenService.getOperands(expresion2);
		String result = "";
	     switch(action) {
	         case "relations":
	             result = analyzer.getRelations(domain,codomain);
	             break;
	         case "functions":
	        	 result = analyzer.getFunctions(domain,codomain);
	             break;
	         case "injectives":
	        	 result = analyzer.getInjectives(domain,codomain);
	             break;
	         case "surjectives":
	        	 result = analyzer.getSurjectives(domain,codomain);
	             break;    
	         case "bijectives":
	        	 result = analyzer.getBijectives(domain,codomain);
	             break;
	         case "evaluate":
	        	 result = "go to index2";
	             break;
	     }
		return result;
	 }
	   
	
	 
	/*path to get relations*/ 
	@PostMapping(value="/relations",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getRelations(@ModelAttribute Statement mychar, Model model) {
				
	  return "relations";
	}
	
	/*path to get functions*/
	@PostMapping(value="/functions",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getFunctions(@ModelAttribute Statement mychar, Model model) {
		
	  return "functions";
	}
	
	/*path to get injective functions*/
	@PostMapping(value="/injectives",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getInjectives(@ModelAttribute Statement mychar, Model model) {
	
	  return "injective";
	}
	
	/*path to get surjective functions*/
	@PostMapping(value="/surjectives",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getSurjectives(@ModelAttribute Statement mychar, Model model) {
			
	  return "surjective";
	}
	
	
	
	/*path to get bijective functions*/
	@PostMapping(value="/bijectives",produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getBijectives(@ModelAttribute Statement mychar, Model model) {
		
	  return "bijectives";
	}
	
	
	/*path to get bijective functions*/
	@PostMapping(value="/evaluate",produces = MediaType.TEXT_HTML_VALUE)
	public String evaluateRelationship(/*@ModelAttribute Statement mychar, Model model*/) {

	  return "index2";
	}
	
	
	
	@GetMapping("/")
	public String statementForm(Model model) {
		model.addAttribute("mystatement", new Statement());
		return "index";
	}
		


/*		analyzer = new AnalyzerService();
	buildHTML = new BuilHTMLService();
	
	List<String> resultOne = analyzer.analizeOne(tokenService.getOperands(expresion1));
	List<String> resultTwo = analyzer.analizeOne(tokenService.getOperands(expresion2));
	List<String> datos = analyzer.analyzeBoth(tokenService.getOperands(expresion1),tokenService.getOperands(expresion2));
	
	String m0 = "<tr>";
	String m1 = "<td>";
	String m2 = "</td>";
	String m4 = m2+m1;
	String m3="</tr>";
	String m5= m2+m3+m0+m1;
	
	String page = buildHTML.getHead();
	page=page+m0+m1+"Members"+m2+m1+resultOne.get(1)+m2+m1+resultTwo.get(1)+m2+m3;
	page=page+m0+m1+"Cardinality"+m2+m1+resultOne.get(0)+m2+m1+resultTwo.get(0)+m2+m3;
	page=page+m0+m1+"Powerset"+m2+m1+resultOne.get(2)+m2+m1+resultTwo.get(2)+m2+m3;
	page=page+m0+m1+"Partitions"+m2+m1+resultOne.get(3)+m2+m1+resultTwo.get(3)+m2+m3;
	
	page = page+buildHTML.getMiddle();
	page = page+m0+m1+"A = B?"+m4+datos.get(0)+m5+"A ⊆ B"+m4+datos.get(1)+m5+"B ⊆ A"+m4+datos.get(2)+m5+"A ⊂ B"+m4+datos.get(3)+m5+"B ⊂ A"+m4+
			datos.get(4)+m5+"A - B"+m4+datos.get(5)+m5+"B - A"+m4+datos.get(6)+m5+"A Δ B"+m4+datos.get(7)+m5+"B Δ A"+m4+
			datos.get(8)+m5+"A U B"+m4+datos.get(9)+m5+"A ⋂ B"+m4+datos.get(10)+m5+"Are disjuntion?"+m4+datos.get(11)+m5+
			"A X B"+m4+datos.get(12)+m5+"B X A"+m4+datos.get(13)+m2+m3;
	
	page = page+buildHTML.getEnd();
	
*/	
	
		
}
