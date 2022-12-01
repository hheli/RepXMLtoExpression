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

public class RepXMLtoExperssion {

	public void printPreview() {
		HashSet<String> ExprSet = new HashSet<String>();

		File file = new File("D:\\Workspase\\RepXMLtoExpression\\src\\test.xml");

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
				//if(nodeList.item(j).getNodeName()=="modelPath") {
				//	continue;
				//}

				System.out.println(nodeList.item(j).getNodeName());
				System.out.println(nodeList.item(j).getTextContent());
			//	String testString = "[ffff].[fffff].[ffff]  + [[gggg].[[gg]";
			//	Matcher matcher = pattern.matcher(testString);
			    Matcher matcher = pattern.matcher(nodeList.item(j).getTextContent());
				
			    while (matcher.find()) {
			        int start=matcher.start();
			        int end=matcher.end();
			        System.out.println("Найдено совпадение " + nodeList.item(j).getTextContent().substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
			        ExprSet.add(nodeList.item(j).getTextContent().substring(start,end));
			     //   System.out.println("Найдено совпадение " + testString.substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
			    }
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File file = new File("D:\\Workspase\\RepXMLtoExpression\\src\\test.xml");
		File file = new File("D:\\Workspase\\RepXMLtoExpression\\src\\09_report.xml");
		Report r = new Report(file);
	//	RepXMLtoExperssion app = new RepXMLtoExperssion();
		//app.printPreview();
		System.out.println(r.getModelFolder());
		System.out.println(r.getSearchPath());
		for(String s:r.getExpressionSet()) {
			System.out.println(s);
		}

	}

}
