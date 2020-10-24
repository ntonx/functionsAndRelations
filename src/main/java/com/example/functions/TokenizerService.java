package com.example.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TokenizerService {
	public TokenizerService() {}

	public List<String> getOperands(String myString) {
		List<String> operatorList = new ArrayList<String>();
		 List<String> operandList = new ArrayList<String>();
	 
		 StringTokenizer st = new StringTokenizer(myString, ".-_!?¡¿,; ", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if (".-_!?¡¿,; ".contains(token)) {
		       operatorList.add(token);
		    } else {
		       operandList.add(token);
		    }
		 }
		 return operandList.stream().distinct().collect(Collectors.toList());
		 
	}
	
	public List<String> getPairs(String myString) {
		List<String> operatorList = new ArrayList<String>();
		 List<String> operandList = new ArrayList<String>();
	 
		 StringTokenizer st = new StringTokenizer(myString, "[],() ", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if (",() ".contains(token)) {
		       operatorList.add(token);
		    } else {
		       operandList.add(token);
		    }
		 }
		 return operandList;//.stream().distinct().collect(Collectors.toList());
		 
	}

	public List<String> getElementsFromPairs(String expresion3) {
		List<String> operatorList = new ArrayList<String>();
		 List<String> operandList = new ArrayList<String>();
	 
		 StringTokenizer st = new StringTokenizer(expresion3, "[](), ", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if ("[](), ".contains(token)) {
		       operatorList.add(token);
		    } else {
		       operandList.add(token);
		    }
		 }
//		 System.out.print(operandList);
		 return operandList;//.stream().distinct().collect(Collectors.toList());
		 
	}
}


	 