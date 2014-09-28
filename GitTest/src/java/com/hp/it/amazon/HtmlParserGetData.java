package com.hp.it.amazon;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chenkang on 8/21/2014.
 */
public class HtmlParserGetData {
    public static void main(String[] args) throws ParserException, IOException {
        FileWriter fw = null;
        try {
            ArrayList<String> comments = new ArrayList<String>();
            fw = new FileWriter("C:\\comments.txt", true);
           
            //for (int page = 1; page <= 40; page++) {
                String url = "http://www.amazon.com/HP-Chromebook-11-1101-White-Blue/product-reviews/B00FJXVRM8/ref=cm_cr_pr_top_link_1?ie=UTF8&pageNumber=1&showViewpoints=0&sortBy=byRankDescending";
                Parser parser =createParser(url) ;
                NodeFilter filter = new CssSelectorNodeFilter("div[class='reviewText']");
              //  NodeFilter filter1=new CssSelectorNodeFilter("span .className paging");
                NodeFilter filter1 = new TagNameFilter("a"); 
                NodeList nodeList = parser.extractAllNodesThatMatch(filter);
                NodeList nodeList1=parser.extractAllNodesThatMatch(filter1);
              //  System.out.println(nodeList1.size());
                for(int j=0;j<nodeList1.size();j++){
                	 Node node1 = nodeList1.elementAt(j);
                	Node last_node= node1.getLastChild();
                	Node page_node=last_node.getPreviousSibling();
                     String pageIndex = page_node.toPlainTextString();
                     System.out.println(pageIndex);
                }
                for (int i = 0; i < nodeList.size(); i++) {
                    Node node = nodeList.elementAt(i);
                    String comment = node.toPlainTextString();
                    comments.add(comment);

                    fw.write(comment);
                    fw.write("\r\n");
                    fw.flush();
                }
           // }
        }finally {
            fw.close();
        }
    }
    /**
     * 解析字符串
     * @param inputHTML String
     * @return Parser
     */
    public static Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        return new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
}


