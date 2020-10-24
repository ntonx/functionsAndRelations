package com.example.functions;

import java.util.List;

public class HTMLService {

	public HTMLService() {}
	
	
	private String changeBracket(String string) {
		String string1 =  string.replaceAll("\\[", "{").replaceAll("\\]","}");
		return string1;
	}
	
	public String getTable(List<List<String>> relations) {
		
		String up ="<!DOCTYPE HTML>\r\n" + 
				"<html>\r\n" + 
				"<head> \r\n" + 
				"    <title>Computational Mathematics</title>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n" + 
				"     <link rel=\"stylesheet\" href=\"/css/main.css\" disabled=\"disabled\">\r\n" + 
				"     <link rel=\"stylesheet\" href=\"/css/cssIndex2.css\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<h1 class=\"h1\">Functions and relations</h1>\r\n" + 
				"	<div class=\"container container-padding50\">";
		
		
	
	String head1=		"<table><thead >\r\n" + 
			"<tr><th style=\"color: white;background-color: #2F4F4F\" scope=\"col\">No</th>"+
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Pares ordenados</th>"+	
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Relación</th>"+
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Función</th>"+
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Injectiva</th>"+
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Suprayectiva</th>"+
								"<th style=\"color: white;background-color: #2F4F4F\"scope=\"col\">Biyectiva</th>"+
								"</tr>\r\n" +
						"      </thead>\r\n" + 
						"      <tbody>\r\n" ; 
		
		head1=up+head1;
		
		String m1= "<tr><td>";
		String m2 = "</td><td>";
		String m3= "</td></tr>";
		String m4 = "</tbody></table>";
		String page="";
		page=head1;//head;
		for(int i=0;i<relations.size();i++) {
			String set = changeBracket(relations.get(i).get(0));
			page=page+m1+(i+1)+m2+set+m2+relations.get(i).get(1)+m2+relations.get(i).get(2)+
					m2+relations.get(i).get(3)+
					m2+relations.get(i).get(4)+
					m2+relations.get(i).get(5);
			page=page+m3;
		}
		page=page+m4;
		
		return page+getEnd();	
	}

	
	public String getEnd() {
		return 	 
				"    </div>\r\n" 
				+
				" <style>\r\n" + 
				".footer {\r\n" + 
				"  left: 0;\r\n" + 
				"border-top-width: .6px;\r\n" + 
				"border-top-style: solid;\r\n" + 
				"border-bottom-style: solid;\r\n" + 
				"border-bottom-width: 1px;\r\n" + 
				"margin-top: 2px;"+
				"  bottom: 0;\r\n" +  
				"  background-color: #2F4F4F;\r\n" + 
				"  color: white;\r\n" + 
				"  text-align: center;\r\n" + 
				"}\r\n" + 
				"</style>\r\n" + 
				"\r\n" + 
				"<div class=\"footer\">\r\n" + 
				"  <p>By Antonio Nicolas Plata. CINVESTAV 2020</p>\r\n" + 
				"</div> "+
				"\r\n" + 
				
				"</html>\r\n" + 
				"";		
	}

	public String getIndex2() {
		return "<!DOCTYPE HTML>\r\n" + 
				"<html xmlns:th=\"https://www.thymeleaf.org\">\r\n" + 
				"<head> \r\n" + 
				"    <title>Computational Mathematics</title>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n" + 
				"     <link rel=\"stylesheet\" href=\"/css/main.css\" disabled=\"disabled\">\r\n" + 
				"     <link rel=\"stylesheet\" href=\"/css/cssIndex2.css\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<h1 class=\"h1\">Functions and relations</h1>\r\n" + 
				"	<div class=\"container container-padding50\">\r\n" + 
				"    <form name=\"form1\" action=\"/expression\" onsubmit=\"required()\" th:action=\"@{/expression}\" th:object=\"${expression}\" method=\"post\">\r\n" + 
				"\r\n" + 
				"	   \r\n" + 
				"\r\n" + 
				"        <p><input id=\"text1\" type=\"text\" th:field=\"*{content1}\" placeholder=\"Write the domain\" class=\"container1-textinput\"/><!--   />-->\r\n" + 
				"        </p>\r\n" + 
				"        <p><input id=\"text1\" type=\"text\" th:field=\"*{content2}\" placeholder=\"Write the codomain\" class=\"container2-textinput\"/><!--  />-->\r\n" + 
				"        </p>\r\n" + 
				"        <input id=\"text1\" type=\"text\" th:field=\"*{content3}\" placeholder=\"Write your expression as (a,b),(c,d)... \" class=\"container2-textinput\"/><!--  />-->\r\n" + 
				"        </p>\r\n" + 
				"  		<div >\r\n" + 
				"				\r\n" + 
				"	        <input type=\"submit\"  name=\"action\" value=\"Go\" />\r\n" + 
				"	    \r\n" + 
				"	    </div>\r\n" + 
				"    </form>\r\n" + 
				"    \r\n" + 
				"    </div>\r\n" + 
				"    <div>\r\n" + 
				"    <p>Instructions: Write elements(separate by commas) that represents the domain and the codomain in every box.</p>\r\n" + 
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"";
	}
}
