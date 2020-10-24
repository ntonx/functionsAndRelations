package com.example.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {
	
	
	HTMLService builderHTML;
	TokenizerService tokenService;
	List<List<String>> relations = new ArrayList<>();
	
	public AnalyzerService() {}
	
	public AnalyzerService(List<String> domain, List<String> codomain) {
		relations = getRelation(domain,codomain);
	}
	
	public List<List<String>> getPowerSet(List<String> set) {
	    List<List<String>> powerSet = new ArrayList<List<String>>();
	    int max = 1 << set.size();
	    for(int i=0; i < max; i++) {
	        List<String> subSet = getSubSet(i, set);
	        powerSet.add(subSet);
	    }
	    return powerSet;
	}
	
	private List<String> getSubSet(int p, List<String> set) {
	    List<String> subSet = new ArrayList<String>();
	    int position = 0;
	    for(int i=p; i > 0; i >>= 1) {
	        if((i & 1) == 1) {
	            subSet.add(set.get(position));
	        }
	        position++;
	    }
	    return subSet;
	}


	private List<String> getProductAB(List<String> set1, List<String> set2) {
		List<String> inter1 = new ArrayList<String>();
		if(set2.isEmpty()||set2.isEmpty()) {
			return inter1;
		}
		
		String [][] datos = new String[set1.size()][set2.size()];
		for(int i=0;i<set1.size();i++) {
			for (int j=0;j<set2.size();j++) {
				datos[i][j]="("+set1.get(i)+","+set2.get(j)+")";
				inter1.add(datos[i][j]);
			}
		}
		return inter1;
	}


	public String getRelations() {
		
		builderHTML = new HTMLService();
//		List<List<String>> relations = getRelation(domain,codomain); 
		return builderHTML.getTable(relations);
	}
	
	
	public List<List<String>> getRelation(List<String> domain, List<String> codomain) {
		
		List<String> inter = getProductAB(domain,codomain);
		List<List<String>> relations = new ArrayList<>();
		relations = getPowerSet(inter);
		List<List<String>> datos = analizeList(relations,domain,codomain);
		return datos;
	}


	
	


	private List<List<String>> analizeList(List<List<String>> relations, List<String> domain, List<String> codomain) {
		List<String> oneRelation=new ArrayList<>();
		List<String> midle=new ArrayList<>();
		List<List<String>> result=new ArrayList<>();
		for (int i=0;i<relations.size();i++) {
			//the 0 is for the set
			oneRelation = relations.get(i);
			midle.add(oneRelation.toString());

     		//1 is for RELATION. is relation, obviously cause it comes from relation function
//     		oneRelation.add("true");
     		midle.add("true");

     		//2 is for FUNCTIONS....is function.. it get values as injective function;
     		Boolean isFunction= isFunction(oneRelation,domain);
     		oneRelation.add(String.valueOf(isFunction));
     		midle.add(String.valueOf(isFunction));
     		//3 is INJECTIVE
     		if(!isFunction) {
/*     			oneRelation.add("false");//not injective
     			oneRelation.add("false");//not supra
     			oneRelation.add("false");//not bi
 */    			midle.add("false");//
     			midle.add("false");//
     			midle.add("false");//
     			
     		}else {
     		Boolean isInjective= isInjective(oneRelation,domain);
//     		oneRelation.add(String.valueOf(isInjective));
     		midle.add(String.valueOf(isInjective));
     		//add other booleans functions results
     		Boolean isSupra= isSupra(oneRelation,domain,codomain);
//     		oneRelation.add(String.valueOf(isSupra));
     		midle.add(String.valueOf(isSupra));

     		//if injective&supra->bi
	     		if(isInjective&&isSupra) {
//	     			oneRelation.add("true");
	     			midle.add("true");
	     		}else {
//	     			oneRelation.add("false");
	     			midle.add("false");
	     			}
     		}
     		
     	// Cloning a list 
     		List<String> cloned_list = new ArrayList<String>(); 
            cloned_list.addAll(midle); 
        
     		result.add(i,cloned_list);
     		midle.clear();
     		oneRelation.clear();
		}
		
		return result;
	}
	


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Boolean isSupra(List<String> oneRelation, List<String> domain, List<String> codomain) {
		String [] datos=new String[oneRelation.size()*2];
		List<String> list = new ArrayList(Arrays.asList());
		if(oneRelation.size()!=0) {
			for(int i=0;i<oneRelation.size();i++) {
				String cadena = oneRelation.get(i);
				cadena = cadena.replaceAll("\\(", "");
				cadena = cadena.replaceAll("\\)", "");
				datos=cadena.split(",");
			    list.addAll(Arrays.asList(datos));		
			}			
			
		    Object[] c = list.toArray();
		    List<String> codominio = new ArrayList<>(); 
		    List<String> dominio = new ArrayList<>(); 
			for(int i=0;i<c.length;i++) {
//				System.out.println(c[i]);
				if(i%2!=0) {
					codominio.add((String) c[i]);
				}else if(i%2==0) {
					dominio.add((String) c[i]);
				}				
			}
			
			codominio = codominio.stream().distinct().collect(Collectors.toList());
	//		Set<String> set = new HashSet<String>(codominio);
		//	if(set.size() < codominio.size()){
			if(codominio.size() < codomain.size()){
			   return false;
			}else {
				dominio.remove(dominio.size()-1);
				if(dominio.size() == domain.size()/*&&codominio.size()==codomain.size()*/){
					return true;
				}else {return false;}
			}	
		}
		
		return false;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Boolean isFunction(List<String> oneRelation, List<String> domain) {
		String [] datos=new String[oneRelation.size()*2];
		List<String> list = new ArrayList(Arrays.asList());
		if(oneRelation.size()!=0) {
			for(int i=0;i<oneRelation.size();i++) {
				String cadena = oneRelation.get(i);
				cadena = cadena.replaceAll("\\(", "");
				cadena = cadena.replaceAll("\\)", "");
				datos=cadena.split(",");
			    list.addAll(Arrays.asList(datos));		
			}			
			
		    Object[] c = list.toArray();
	//	    System.out.println(Arrays.toString(c));
		    List<String> dominio = new ArrayList<>();  
			for(int i=0;i<c.length;i++) {
				if(i%2==0) {
					dominio.add((String) c[i]);
				}				
			}
		//	dominio.remove(dominio.size()-1);
			
			Set<String> set = new HashSet<String>(dominio);
			if(set.size() < dominio.size()){
			   return false;
			}else {
				if(dominio.size() == domain.size()){
					return true;
				}else {return false;}
			}		
		}
		
		return false;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Boolean isInjective(List<String> oneRelation, List<String> domain) {
		String [] datos=new String[oneRelation.size()*2];
		List<String> list = new ArrayList(Arrays.asList());
		if(oneRelation.size()!=0) {
			for(int i=0;i<oneRelation.size();i++) {
				String cadena = oneRelation.get(i);
				cadena = cadena.replaceAll("\\(", "");
				cadena = cadena.replaceAll("\\)", "");
				datos=cadena.split(",");
			    list.addAll(Arrays.asList(datos));		
			}			
			
		    Object[] c = list.toArray();
	//	    System.out.println(Arrays.toString(c));
		    List<String> codominio = new ArrayList<>(); 
		    List<String> dominio = new ArrayList<>(); 
			for(int i=0;i<c.length;i++) {
				if(i%2!=0) {
					codominio.add((String) c[i]);
				}else if(i%2==0) {
					dominio.add((String) c[i]);
				}				
			}
			dominio.remove(dominio.size()-1);
			
			Set<String> set = new HashSet<String>(codominio);
			if(set.size() < codominio.size()){
			   return false;
			}else {
				if(dominio.size() == domain.size()){
					return true;
				}else {return false;}
			}	
		}
		
		return false;
	}
	

	public String getFunctions() {
		builderHTML = new HTMLService();			 
	    List<List<String>> functions_filtered = new ArrayList<>();
	    for(int i=0;i<relations.size();i++) {
	    	if(relations.get(i).get(2).equals("true")) {
	    		functions_filtered.add(relations.get(i));
	    	}
	    }
		return builderHTML.getTable(functions_filtered);
	}


	public String getInjectives() {
		builderHTML = new HTMLService();			
	    List<List<String>> functions_filtered = new ArrayList<>();
	    for(int i=0;i<relations.size();i++) {
	    	if(relations.get(i).get(3).equals("true")) {
	    		functions_filtered.add(relations.get(i));
	    	}
	    }
		return builderHTML.getTable(functions_filtered);
	}


	public String getSurjectives() {
		builderHTML = new HTMLService();			
	    List<List<String>> functions_filtered = new ArrayList<>();
	    for(int i=0;i<relations.size();i++) {
	    	if(relations.get(i).get(4).equals("true")) {
	    		functions_filtered.add(relations.get(i));
	    	}
	    }
		return builderHTML.getTable(functions_filtered);
	}



	public String getBijectives() {
		builderHTML = new HTMLService();			
	    List<List<String>> functions_filtered = new ArrayList<>();
	    for(int i=0;i<relations.size();i++) {
	    	if(relations.get(i).get(5).equals("true")) {
	    		functions_filtered.add(relations.get(i));
	    	}
	    }
		return builderHTML.getTable(functions_filtered);
	}


	public String getIndex2() {
		builderHTML = new HTMLService();
		return builderHTML.getIndex2();
	}


	public String evaluateExpression(List<String> myinput, List<String> domain, List<String> codomain) {
		tokenService = new TokenizerService();
		int pos=-1;
		List<String> myinputPair = new ArrayList<>();
		//change from [a,b,c,d] to pair format [(a,b),(c,d)]
		for(int i=0;i<myinput.size();i=i+2) {
			myinputPair.add("("+myinput.get(i)+","+myinput.get(i+1)+")");
		}
		
		List<String> myRelationsXY = new ArrayList<>();
		
		for(int i=0;i<relations.size();i++) {
			String pairs = relations.get(i).get(0);
			pairs = pairs.replaceAll("\\[", "").replaceAll("\\]","");
			if(pairs.equals("")) {
	//			System.out.println("es vacia");
			}else {
				List<String> pairsInSystem = tokenService.getElementsFromPairs(pairs);
				
				//change from sublist in total relations [a,b,c,d] in a pair format
				for(int k=0;k<pairsInSystem.size();k=k+2) {
					myRelationsXY.add("("+pairsInSystem.get(k)+","+pairsInSystem.get(k+1)+")");
				}
				if(myRelationsXY.size()!=myinputPair.size()) {
					myRelationsXY.clear();
				}else {
					//evaluate content of both lists
					boolean checking = checkContent(myRelationsXY, myinputPair);
					if(checking) {
						pos = i;
						break;
					}
				myRelationsXY.clear();
				}
			}	
		}
		if(pos==-1) {
			String pairs = "{";
			for(int i=0;i<myinputPair.size();i++) {
				pairs = pairs+myinputPair.get(i);
			}
			pairs=pairs+"}";
			List<String> relationUser = new ArrayList<>();
			relationUser.add(pairs);
			relationUser.add("false");
			relationUser.add("false");
			relationUser.add("false");
			relationUser.add("false");
			relationUser.add("false");
			
			List<List<String>> relation = new ArrayList<>();
			relation.add(relationUser);
			builderHTML = new HTMLService();
			String table = builderHTML.getTable(relation);
			return table;
		}
		List<List<String>> relation = new ArrayList<>();
		relation.add(relations.get(pos));
		builderHTML = new HTMLService();
		String table = builderHTML.getTable(relation);
		return table;
	}

	

	private static class Count {
	    public int count = 0;
	}

	
	
	public boolean checkContent(final List<String> list1, final List<String> list2) {
	    // (list1, list1) is always true
	    if (list1 == list2) return true;

	    // If either list is null, or the lengths are not equal, they can't possibly match 
	    if (list1 == null || list2 == null || list1.size() != list2.size())
	        return false;

	    // (switch the two checks above if (null, null) should return false)

	    Map<String, Count> counts = new HashMap<>();

	    // Count the items in list1
	    for (String item : list1) {
	        if (!counts.containsKey(item)) counts.put(item, new Count());
	        counts.get(item).count += 1;
	    }

	    // Subtract the count of items in list2
	    for (String item : list2) {
	        // If the map doesn't contain the item here, then this item wasn't in list1
	        if (!counts.containsKey(item)) return false;
	        counts.get(item).count -= 1;
	    }

	    // If any count is nonzero at this point, then the two lists don't match
	    for (Map.Entry<String, Count> entry : counts.entrySet()) {
	        if (entry.getValue().count != 0) return false;
	    }

	    return true;
	}


}








//private void analizeListRespaldo(List<List<String>> relations, List<String> domain, List<String> codomain) {
//	List<String> oneRelation=new ArrayList<>();
//	List<String> result=new ArrayList<>();
//	for (int i=0;i<7/*relations.size()*/;i++) {
//		//the 0 is for the set
//		oneRelation = relations.get(i);
// 		result.add(oneRelation.toString());
// 		//1 is for RELATION. is relation, obviously cause it comes from relation function
//// 		oneRelation.add("true");
// 		result.add("true");
// 		//2 is for FUNCTIONS....is function.. it get values as injective function;
// 		Boolean isFunction= isFunction(oneRelation,domain);
//// 		oneRelation.add(String.valueOf(isFunction));
// 		result.add(String.valueOf(isFunction));
// 		//3 is INJECTIVE
// 		if(!isFunction) {
//// 			oneRelation.add("false");//not injective
// 			result.add("false");
// 			result.add("false");
// 			result.add("false");
//// 			oneRelation.add("false");//not supra
////    			oneRelation.add("false");//not bi
// 		}else {
// 		Boolean isInjective= isInjective(oneRelation,domain);
//// 		oneRelation.add(String.valueOf(isInjective));
// 		result.add(String.valueOf(isInjective));
// 		//add other booleans functions results
// 		Boolean isSupra= isSupra(oneRelation,domain,codomain);
//// 		oneRelation.add(String.valueOf(isSupra));
// 		result.add(String.valueOf(isSupra));
// 		//if injective&supra->bi
//     		if(isInjective&&isSupra) {
//     			result.add("true");
////     			oneRelation.add("true");
//     		}
// 		}
////		System.out.println(result);
////		System.out.println("injective"+isInjective);
//	}
//	
//
//}






//public String evaluateExpression1(List<String> myinput, AnalyzerService analyzer) {
//	this.relations=analyzer.relations;
//	
//	List<String> myinputPair = new ArrayList<>();
//	List<String> pairs = new ArrayList<>();
//	
//	for(int i=0;i<myinput.size();i=i+2) {
//		myinputPair.add("("+myinput.get(i)+","+myinput.get(i+1)+")");
//	}
//	System.out.println(myinputPair);
//	
//	List<String> myRelationsPair = new ArrayList<>();
//	
//	for(int i=0;i<relations.size();i++) {
//		String value =String.valueOf(relations.get(i).get(0).toString());
////
//
//		if(value.equals("[]")) {
//			
//		}else {
//			value = changeBracket(value);
//			value =value.replaceAll("\\s","");
//	//		System.out.println(value);
//			List<String> pairs1 = tokenService.getPairs(value);
//			System.out.println(pairs1);
//		/*	if(pairs.size()==0) {}
//		else {
//			myRelationsPair.add("("+myinput.get(i)+","+myinput.get(i+1)+")");
//		}
//	*/	}
//	}
//	
//	return "ok";
//	
//}










	 