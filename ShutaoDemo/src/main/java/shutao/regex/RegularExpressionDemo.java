package shutao.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionDemo {

	public static void main(String[] args) {
		/**
		 * 1. Validation
		 */
		System.out.println("ss@gmail.com".matches(".*@.*\\..*"));
		System.out.println("ssgmail.com".matches(".*@.*\\..*"));

		/**
		 * 2. Grab content
		 */
		String webPageContent = "</thead>   <tbody>  <tr class = info ><td><a href=\"/link1\">Identify1</a></td> <td>Attribute1</td></tr>"
				+ "<tr class = info ><td><a href=\"/link2\">Identify2</a></td> <td>Attribute2</td>";
		String regx3 = "<a href=\"(/.*?)\">(.*?)</a></td>" + ".*?<td>(.*?)</td>";
		Pattern p = Pattern.compile(regx3);

		Matcher m = p.matcher(webPageContent);
		while (m.find()) {
			String link = m.group(1);
			String id = m.group(2);
			String attr = m.group(3);

			System.out.println("Link: [" + link + "] ID: [" + id + "] Attr: [" + attr + "]");
		}
		
		/**
		 * 3, String operation: Split
		 */
		Pattern p2 = Pattern.compile("\\d+");
		String[] str = p2.split("a10Bb");
		System.out.println(Arrays.asList(str));
		
		/**
		 * 4, String operation: ReplaceAll
		 */
		String repStr = "Hello2 W3o5r7l8d";
		System.out.println(repStr.replaceAll("\\d", ""));
	}

}
