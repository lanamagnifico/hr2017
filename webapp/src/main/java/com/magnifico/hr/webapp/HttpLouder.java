package com.magnifico.hr.webapp;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpLouder {

    public static void main(String[] args) throws IOException {

        System.out.format("\n * %f", getValuteValue(new Date(),"R01235"));
    }

    public static float getValuteValue(Date date,String cbrId) throws IOException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = simpleDateFormat.format(date);
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="+strDate;
        Document doc = Jsoup.connect(url).get();
        Element value= doc.select("Valute#"+cbrId).select("value").first();
        return value==null?Float.parseFloat("0.0"):Float.parseFloat(value.text().replace(',','.'));
    }
}
