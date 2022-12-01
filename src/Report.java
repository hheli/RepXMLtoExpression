import java.io.File;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Report {
	
	private String searchPath;
	private File file;
	private HashSet<String> expressionSet = new HashSet<String>();;
	private String packageName;
		
	
	public String getSearchPath() {
		return searchPath;
	}


	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}
	private String modelFolder;
		
	public HashSet<String> getExpressionSet() {
		return expressionSet;
	}
	public void setExpressionSet(HashSet<String> expressionSet) {
		this.expressionSet = expressionSet;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getModelFolder() {
		return modelFolder;
	}
	public void setModelFolder(String modelFolder) {
		this.modelFolder = modelFolder;
	}
	
	public Report( File file)
	{
		this.searchPath="";
		//this.expressionSet=new HashSet<String>(); 
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "//*[contains(text(),'[') and contains(text(),']') ]";
		String patternString="\\[.+?\\](?![\\.|\\]])";
		Pattern pattern=Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.parse(file);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			for (int j = 0; j < nodeList.getLength(); j++) {
				if(nodeList.item(j).getNodeName()=="modelPath") {
					this.packageName=nodeList.item(j).getTextContent();//заглушка нужно доаработать выбор packageName
					this.modelFolder=nodeList.item(j).getTextContent();//заглушка нужно доработать выбор папки
					continue;
				}

			//	System.out.println(nodeList.item(j).getNodeName());
			//	System.out.println(nodeList.item(j).getTextContent());
			//	String testString = "[ffff].[fffff].[ffff]  + [[gggg].[[gg]";
			//	Matcher matcher = pattern.matcher(testString);
			    Matcher matcher = pattern.matcher(nodeList.item(j).getTextContent());
				
			    while (matcher.find()) {
			        int start=matcher.start();
			        int end=matcher.end();
			      //  System.out.println("Найдено совпадение " + nodeList.item(j).getTextContent().substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
			       this.expressionSet.add(nodeList.item(j).getTextContent().substring(start,end));
			     //   System.out.println("Найдено совпадение " + testString.substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
			    }
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}



}
