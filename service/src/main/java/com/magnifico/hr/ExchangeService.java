package com.magnifico.hr;


import ru._1c.Client1cExchange2016;
import ru._1c.ExchangeException;

import java.io.*;
import java.util.Map;

public class ExchangeService {

    public void doExchange(){
        Client1cExchange2016 client1cExchange2016 = new Client1cExchange2016();
    }

    public boolean doTestConnection(String url,String user,String pass,String exchPlan,String code) throws ExchangeException{
        Client1cExchange2016 client1cExchange2016 = new Client1cExchange2016();
        return client1cExchange2016.testConnection(url,user,pass,exchPlan,code);
    }
    public Map<String,String> getParamsIB(String url, String user, String pass, String exchPlan, String code) throws ExchangeException{
        Client1cExchange2016 client1cExchange2016 = new Client1cExchange2016();
        return client1cExchange2016.getIBParams(url,user,pass,code,exchPlan);
    }

    public void getData(String url,String user,String pass,String tablName,String outFile) throws IOException{
        Client1cExchange2016 client1cExchange2016 = new Client1cExchange2016();
        String data = client1cExchange2016.getData(url,user,pass,tablName);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outFile), "utf-8"))) {
            writer.write(data);
        }

    }

}
