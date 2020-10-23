package com.example.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerService {
	
	
	HTMLService builderHTML;
	TokenizerService tokenService;
	
	
	public AnalyzerService() {}
	
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

	

	private String changeBracket(String string) {
		String string1 =  string.replaceAll("\\[", "{").replaceAll("\\]","}");
		return string1;
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


	public String getRelations(List<String> domain, List<String> codomain) {
		builderHTML = new HTMLService();
		
	//	List<String> inter = getProductAB(domain,codomain);
				
		List<List<String>> relations = getRelation(domain,codomain); 
	
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
		}
		
		return result;
	}
	
	
	
	
	
	

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
			
			Set<String> set = new HashSet<String>(codominio);
			
//			Set<String> dom = new HashSet<String>(dominio);
			if(set.size() < codominio.size()){
			   return false;
			}else {
				codominio.remove(codominio.size()-1);
				dominio.remove(dominio.size()-1);
				dominio.remove(dominio.size()-1);
				if(dominio.size() == domain.size()&&codominio.size()==codomain.size()){
					return true;
				}else {return false;}
			}	
		}
		
		return false;
		
	}

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
//			Set<String> dom = new HashSet<String>(dominio);
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




	public String getFunctions(List<String> domain, List<String> codomain) {
		builderHTML = new HTMLService();			
	    List<List<String>> relations = getRelation(domain,codomain); 
	    List<List<String>> functions_filtered = new ArrayList<>();
	    for(int i=0;i<relations.size();i++) {
//	    	System.out.println(relations.get(i));
	    	if(relations.get(i).get(2).equals("true")) {
	    		functions_filtered.add(relations.get(i));
	    	}
	    }
			return builderHTML.getTable(functions_filtered);
	}



	public String getInjectives(List<String> domain, List<String> codomain) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getSurjectives(List<String> domain, List<String> codomain) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getBijectives(List<String> domain, List<String> codomain) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	String [][] datos = new String[domain.size()][codomain.size()];
	int k=0;
	for(int i=0;i<domain.size();i++) {
		for(int j=0;j<codomain.size();j++) {
			datos[i][j]=inter.get(k);
			k=k+1;
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	private void analizeListRespaldo(List<List<String>> relations, List<String> domain, List<String> codomain) {
		List<String> oneRelation=new ArrayList<>();
		List<String> result=new ArrayList<>();
		for (int i=0;i<7/*relations.size()*/;i++) {
			//the 0 is for the set
			oneRelation = relations.get(i);
     		result.add(oneRelation.toString());
     		//1 is for RELATION. is relation, obviously cause it comes from relation function
//     		oneRelation.add("true");
     		result.add("true");
     		//2 is for FUNCTIONS....is function.. it get values as injective function;
     		Boolean isFunction= isFunction(oneRelation,domain);
//     		oneRelation.add(String.valueOf(isFunction));
     		result.add(String.valueOf(isFunction));
     		//3 is INJECTIVE
     		if(!isFunction) {
//     			oneRelation.add("false");//not injective
     			result.add("false");
     			result.add("false");
     			result.add("false");
//     			oneRelation.add("false");//not supra
 //    			oneRelation.add("false");//not bi
     		}else {
     		Boolean isInjective= isInjective(oneRelation,domain);
//     		oneRelation.add(String.valueOf(isInjective));
     		result.add(String.valueOf(isInjective));
     		//add other booleans functions results
     		Boolean isSupra= isSupra(oneRelation,domain,codomain);
//     		oneRelation.add(String.valueOf(isSupra));
     		result.add(String.valueOf(isSupra));
     		//if injective&supra->bi
	     		if(isInjective&&isSupra) {
	     			result.add("true");
//	     			oneRelation.add("true");
	     		}
     		}
			System.out.println(result);
//			System.out.println("injective"+isInjective);
		}
		
	
	}


}

	 