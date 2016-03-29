package com.db.finder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.db.finder.FileUtil.Log;

public class ValuesCompare {

	// 在first文件增加 secend文件有，而first没有的属性
	private static void checkAndCompare(Document first, Document secend){
		if (first != null && secend != null) {		
			Element first_root = checkAndroidProjectStringVaild(first);
			Element secend_root = checkAndroidProjectStringVaild(secend);		
			if (first_root != null && secend_root != null) {
				 List<Element> secend_childrenList =  secend_root.elements();		 
				 List<Element> first_childrenList =  first_root.elements();	
				 for (Element element : secend_childrenList) {	
					 boolean find = false;
					 String search_key = element.attributeValue("name"); 
					 for (Element first_element : first_childrenList) {
						 if (first_element.attributeValue("name").equals(search_key)) {
							find = true;
							
							break;
						}
					 }
					if (find) {
						continue;
					}else{					
						Element addElement =  (Element)element.clone();	
						first_childrenList.add(addElement);
						Log.i("add element "+search_key);
					}
				 }
				
			}
		}
	}
	
	private static Element checkAndroidProjectStringVaild( Document doc) {
		if (doc != null) {
			Element root = doc.getRootElement();
			if (root.getName().equalsIgnoreCase("resources")) {
				return  root;
			}
		}
		return null;
	}
	
	
	
	public static void Check(List<String> checkList){
		if (checkList == null || checkList.size() <= 1) {
			return ;
		}
		
		List<String> stringList = new ArrayList<String>();
		for (String string : checkList) {
			if (string.endsWith("strings.xml")) {
				stringList.add(string);
			}
		}
		
		Document[] document = new Document[stringList.size()];		
		for (int i = 0; i < stringList.size(); i++) {	
			try {
				document[i] = new SAXReader().read(new File(stringList.get(i)));		
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
				
		for (int i = 1; i < document.length; i++) {			
			checkAndCompare(document[0],document[i]);
		}
		
		for (int i = 1; i < document.length; i++) {			
			checkAndCompare(document[i],document[0]);
		}
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setPadText(true);
		format.setIndent(true);
		format.setEncoding("utf-8");
		for (int i = 0; i < stringList.size(); i++) {		
			try {		
				XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(stringList.get(i))),"UTF-8"),format); //
				output.write(document[i]);
				output.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}
	}
	
	
	public static void computerDpi(){
//		SAXReader reader = new SAXReader();  
 //       //读取文件 转换成Document  
//        Document document = null;
//		try {
//			document = reader.read(new File("E:\\sdk\\VOD_V1.0\\VODAPP\\src\\res\\values-sw720dp-xhdpi\\dimens.xml"));
//			Element root = document.getRootElement(); 
//			
//			if (root.getName().equalsIgnoreCase("resources")) {			
//			    List<Element> childrenList =  root.elements();		    
//			    for (Element element : childrenList) {					
//			    	System.out.print(element.getText()+"\n");			    	
//			    	if (element.getTextTrim().contains("sp")) {
//			    		String valueString = element.getTextTrim();		    	
//				    	String[] spliteStrings = valueString.split("sp");		    	
//				    	if (spliteStrings.length == 1) {					
//				    		float value = (float)Double.parseDouble(spliteStrings[0]) * 0.5f;			    		
//				    		System.out.print(value+"\n");	    		
//				    		element.setText((int)value +"sp");
//						}
//					}else if(element.getTextTrim().contains("dp")){
//						String valueString = element.getTextTrim();		    	
//				    	String[] spliteStrings = valueString.split("dp");		    	
//				    	if (spliteStrings.length == 1) {					
//				    		float value = (float)Double.parseDouble(spliteStrings[0]) * 0.5f;			    		
//				    		System.out.print(value+"\n");	    		
//				    		element.setText((int)value +"dp");
//						}
//					}
//				}
//			}
//			XMLWriter output = new XMLWriter(new FileWriter(new File("E:\\dimens.xml"))); //
//            output.write(document);
//            output.close();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
        //获取根节点元素对象 
	}
}
