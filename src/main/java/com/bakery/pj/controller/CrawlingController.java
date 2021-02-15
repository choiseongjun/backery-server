package com.bakery.pj.controller;

import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.BackeryVo2;
import com.bakery.pj.service.BackeryService;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@AllArgsConstructor
public class CrawlingController {
    @Autowired
    BackeryService backeryService;

    @GetMapping(value="/crawling")
    public void crawling() throws IOException {
        List<BackeryVo2> backeryList = backeryService.listbackery2();

        try {
            for(int i=0; i<backeryList.size(); i++ ) {
//                if(i%500 ==  0 && i != 0) {
//                    Thread.sleep(10000);
//                }
                Thread.sleep(1500);


                String query = backeryList.get(i).getEntrpNm();
                String host = "https://m.search.naver.com/search.naver?where=m&sm=mtb_jum&query="+query;

                String naverPlaceUrl = "";
                String hour = "";

                Document doc = Jsoup.connect(host).get();
                Elements infoList = doc.select("div[id=place-app-root]").select("a[id=_title]");
                String href = infoList.attr("href");

                naverPlaceUrl = href;

                if("".equals(href)) {
                    infoList = doc.select("a[class=ZYYRR]");
                    try {
                        naverPlaceUrl = infoList.get(0).attr("href");
                    }catch (Exception e) {

                    }
                }

                if(!"".equals(naverPlaceUrl)){
                    if(naverPlaceUrl.contains("?")) {
                        int io = naverPlaceUrl.indexOf("?");
                        naverPlaceUrl = naverPlaceUrl.substring(0, io);
                    }
                    host = naverPlaceUrl + "/home";

                    doc = Jsoup.connect(host).get();


                    infoList = doc.select("span[class=_20pEw]");
                    for(Element content : infoList){
                            hour += content.text() + "|";
                    }

                    if("".equals(hour) || hour.contains("생생정보") || hour.contains("생방송") || hour.contains("맛있는녀석")) {
                        hour = "";
                        infoList = doc.select("div[class=_2ZP3j]");
                        for(Element content : infoList){
                                hour += content.text() + "|";
                        }
                    }
                }

                if (hour.length() > 0) {
                    hour = hour.substring(0, hour.length()-1);
                }





                BackeryVo2 backeryVo2 = new BackeryVo2();
                backeryVo2.setNaverPlaceUrl(naverPlaceUrl);
                backeryVo2.setId(backeryList.get(i).getId());
                backeryVo2.setBusinessHours(hour);
                int result = backeryService.updateBakery(backeryVo2);

                if(!"".equals(naverPlaceUrl)) {
                    Document doc2 = Jsoup.connect(naverPlaceUrl+"/menu/list").get();
                    Elements infoList2 = doc2.select("ul[class=Scbop] > li[class=_3Jbu5]");
                    String menu = "";
                    String price = "";
                    if(infoList2.hasText()) {
                        for(Element content : infoList2){
                            menu = content.select("span[class=cbGGg]").text();
                            price = content.select("div[class=UnMvu]").text();
                            backeryVo2.setMenuNm(menu);
                            backeryVo2.setMenuPrice(price);
                            int result2 = backeryService.insertBakeryMenu(backeryVo2);

                            System.out.println(menu + "/" + price);
                        }
                    }
                }
                System.out.println(backeryList.get(i).getEntrpNm() +" : "+ hour);






            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        //System.out.println(backeryList);
    }
}

