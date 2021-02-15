package com.bakery.pj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;

public class CrawlingController {

    @Test
    public void crawling() throws IOException {


        String query = "더마카롱샵";
        //String host = "https://m.search.naver.com/search.naver?where=m&sm=mtb_jum&query="+query;
       // String host = "https://m.place.naver.com/restaurant/1588834856/home?entry=plt";
        //String host = "https://m.place.naver.com/restaurant/1588834856?entry=plt";
        String host = "https://m.place.naver.com/restaurant/1253183545/menu/list";


        try {
            Document doc2 = Jsoup.connect(host).get();
            Elements infoList2 = doc2.select("ul[class=Scbop] > li[class=_3Jbu5]");
            String menu = "";
            String price = "";
            if(infoList2.hasText()) {
                for(Element content : infoList2){
                    menu = content.select("span[class=cbGGg]").text();
                    price = content.select("div[class=UnMvu]").text();
                    System.out.println("aron3 => " + menu);
                    System.out.println("aron3 => " + price);
                }
            }


            //Document doc = Jsoup.connect(host).get();
            //Elements infoList = doc.select("div[id=place-app-root]").select("a[id=_title]");
            //String href = infoList.attr("href");
            //System.out.println(infoList.get(i).attr("href"));
            //System.out.println(doc);
            //Elements infoList = doc.select("span[class=_20pEw]");


            //System.out.println(infoList.text());

            //infoList = doc.select("span[class=_20pEw]");

           // Elements infoList = doc.select("li[class=_1ii-H]");


            //System.out.println(infoList);

//            for(Element content : infoList){
//                //hour += content.text() + "|";
//
//                String menu = content.select("a[class=cK9DK]").text();
//                String price = content.select("em[class=_2e0-Z]").text();
//
//                System.out.println("aron3 => " + menu);
//                System.out.println("aron3 => " + price);
//
//            }

//            if(!infoList.hasText()) {
//                System.out.println("NULL!!!!!!!");
//                infoList = doc.select("li[class=_1XxR5]");
//                for(Element content : infoList){
//                    String menu = content.select("div[class=_1tQLX] > div").text();
//                    String price = content.select("div[class=_3IAAc] > div").text();
//                    System.out.println("aron3 => " + menu);
//                    System.out.println("aron4 => " + price);
//                }
//            }




//            System.out.println("aron1 => " + hour);
//            if("".equals(hour) || hour.contains("생생정보") || hour.contains("생방송") || hour.contains("맛있는녀석")) {
//                System.out.println("aron2 => " + hour);
//                hour = "";
//                infoList = doc.select("div[class=_2ZP3j]");
//                for(Element content : infoList){
//                    hour += content.text() + "|";
//                }
//                System.out.println("aron3 => " + hour);
//            }




//            if("".equals(href)) {
//                infoList = doc.select("a[class=ZYYRR]");
//                System.out.println(infoList.get(0).attr("href"));
//            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
