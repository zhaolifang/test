package com.hp.it.amazon;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenkang on 8/21/2014.
 */
public class test {
	public static void main(String[] args) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				try {
					test t = new test();
					String pageSize = t.getPageSize();
					t.getPageComments(pageSize);
					Thread.sleep(2000);
				} catch (ParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static URLConnection getUrlAgent(String strUrl) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public String getPageSize() {
		Node page = null;
		try {
			String url_page = "http://www.amazon.com/HP-Chromebook-11-1101-White-Blue/product-reviews/B00FJXVRM8/ref=cm_cr_pr_top_link_1?ie=UTF8&pageNumber=1&showViewpoints=0&sortBy=byRankDescending";
			Parser parser1 = new Parser(getUrlAgent(url_page));
			parser1.setEncoding("UTF-8");
			NodeFilter filterID = new CssSelectorNodeFilter("span[class='paging'");
			NodeList list = parser1.extractAllNodesThatMatch(filterID);

			for (int i = 0; i < list.size(); i++) {
				Node node_page = (Node) list.elementAt(i);
				NodeList child = node_page.getChildren();
				for (int j = 0; j < child.size(); j++) {
					if (j == child.size() - 4) {
						page = child.elementAt(j);
					}
				}
				return page.toPlainTextString();

			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page.toPlainTextString();
	}

	public void getPageComments(String pageSize) throws IOException, ParserException {
		FileWriter fw = null;
		Random no = new Random(System.currentTimeMillis());
		File file = new File("C:\\comments_" + no + ".txt");
		try {
			ArrayList<String> comments = new ArrayList<String>();
			System.setProperty("http.proxyHost", "proxy.houston.hp.com");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("http.nonProxyHosts", "localhost");
			fw = new FileWriter(file, true);
			for (int page = 1; page <= Integer.parseInt(pageSize); page++) {
				System.out.println("fetching page " + Integer.parseInt(pageSize));
				String url = "http://www.amazon.com/HP-Chromebook-11-1101-White-Blue/product-reviews/B00FJXVRM8/ref=cm_cr_pr_top_link_"
						+ page + "?ie=UTF8&pageNumber=" + page + "&showViewpoints=0&sortBy=byRankDescending";
				Parser parser = new Parser(getUrlAgent(url));
				parser.setEncoding("UTF-8");
				NodeFilter filter = new CssSelectorNodeFilter("div[class='reviewText'");
				NodeList nodeList = parser.extractAllNodesThatMatch(filter);
				System.out.println(nodeList.size());
				for (int i = 0; i < nodeList.size(); i++) {
					Node node = nodeList.elementAt(i);
					String comment = node.toPlainTextString();
					comments.add(comment);
					fw.write(comment);
					fw.write("\r\n");
					fw.flush();
				}
			}
		} finally {
			fw.close();
		}
	}
}
