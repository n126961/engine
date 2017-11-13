package com.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Header {

	List<String> visitedLinks = new ArrayList<String>();

	public void analyse(String url) throws Exception, IOException {
		WebClient client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		visitedLinks.add(url);
		System.out.println(System.currentTimeMillis());
		extracts(url, client);
		System.out.println(System.currentTimeMillis());
	}

	private void extracts(String url, WebClient client) throws Exception,
			IOException {
		// if(visitedLinks.contains(url)) return;
		HtmlPage page = (HtmlPage) client.getPage(url);
		List<HtmlElement> items = page.getByXPath("//div[@id='search-result-items']");
		for (HtmlElement htmlItem : items) {
			List<HtmlAnchor> allProducts = htmlItem.getByXPath(".//a");
			for(HtmlAnchor productLink :allProducts){
				System.out.println(productLink.asText());
				HtmlPage returnPage=productLink.click();
				returnPage.asXml();
				System.out.println(returnPage.asXml());
				System.out.println("returned");
			}
		}
		
		

		
	}

	public static void main(String[] args) throws IOException, Exception {
		Header header = new Header();
		header.analyse("http://www.smartprix.com/laptops");

	}
}
