//package com.hp.it.amazon;
//
//
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * Created by chenkang on 8/21/2014.
// */
//public class GetAmazonData {
//    public static void main(String[] args) throws Exception{
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        try{
//            HttpGet httpGet = new HttpGet("http://www.amazon.com/HP-Chromebook-11-1101-White-Blue/product-reviews/B00FJXVRM8/ref=cm_cr_pr_top_link_1?ie=UTF8&pageNumber=1&showViewpoints=0&sortBy=byRankDescending");
//
//            System.out.println("Executing request " + httpGet.getRequestLine());
//
//            ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
//
//            
//                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
//                    int status = httpResponse.getStatusLine().getStatusCode();
//                    if (status >= 200 && status < 300) {
//                        HttpEntity entity = httpResponse.getEntity();
//                        InputStream content;
//                        if (entity != null){
//                            content = entity.getContent();
//
//                        }
//                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
//                    }
//                    return null;
//                }
//            };
//            String responseBody = httpClient.execute(httpGet, responseHandler);
//            System.out.println("-----------------------------------------");
//            System.out.println(responseBody);
//        }finally {
//            httpClient.close();
//        }
//    }
//}
