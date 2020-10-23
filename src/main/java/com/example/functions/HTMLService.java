package com.example.functions;

import java.util.List;

public class HTMLService {

	public HTMLService() {}
	
	public String getTable(List<List<String>> relations) {
		for(int i=0;i<relations.size();i++) {
			System.out.println(relations.get(i));
		}
		
		
		return "<table class=\"blueTable\">\r\n" + 
				"<thead>\r\n" + 
				"<tr>\r\n" + 
				"<th>No</th>\r\n" + 
				"<th>Pares ordenados</th>\r\n" + 
				"<th>Relación</th>\r\n" + 
				"<th>Función</th>\r\n" + 
				"<th>Injectiva</th>\r\n" + 
				"<th>Suprayectiva</th>\r\n" + 
				"<th>Biyectiva</th>\r\n" + 
				"</tr>\r\n" + 
				"</thead>\r\n" + 
				"<tbody>\r\n" + 
				"<tr>\r\n" + 
				"<td>cell1_1</td>\r\n" + 
				"<td>cell2_1</td>\r\n" + 
				"<td>cell3_1</td>\r\n" + 
				"<td>cell4_1</td>\r\n" + 
				"<td>cell5_1</td>\r\n" + 
				"<td>cell6_1</td>\r\n" + 
				"<td>cell7_1</td>\r\n" + 
				"</tr>\r\n" + 
				"</tbody>\r\n" + 
				"</table>";
	}
}
