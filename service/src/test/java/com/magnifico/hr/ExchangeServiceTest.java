package com.magnifico.hr;

import org.junit.Assert;
import org.junit.Test;
import ru._1c.ExchangeException;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.util.Map;

public class ExchangeServiceTest {

    @Test
    public void doTest() {
        String serviceURLDataExchange = "*****.1cws?wsdl";
        String user = "*********";
        String password = "***********";
        String exchangePlanName = "ОбменУППЗарплатаИУправлениеПерсоналом25";
        String appCode = "SAL";
        String outFile = "/Users/magnifico/Documents/dev/projects/exchange1chr/out_data";
        String error = "";

        ExchangeService exchangeService = new ExchangeService();
        boolean testConnection = false;
        try {
            testConnection = exchangeService.doTestConnection(serviceURLDataExchange, user, password, exchangePlanName, appCode);
        } catch (ExchangeException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("test connection to ws", testConnection);
    }

    @Test
    public void testParamIB_ExchangePlanAndNodeExist() {
        String serviceURLDataExchange = "*****.1cws?wsdl";
        String user = "*********";
        String password = "***********";
        String exchangePlanName = "ОбменУППЗарплатаИУправлениеПерсоналом25";
        String appCode = "SAL";
        String outFile = "/Users/magnifico/Documents/dev/projects/exchange1chr/out_data";
        String error = "";

        ExchangeService exchangeService = new ExchangeService();
        Map<String, String> params = null;
        try {
            params = exchangeService.getParamsIB(serviceURLDataExchange, user, password, exchangePlanName, appCode);
        } catch (ExchangeException e) {
            e.printStackTrace();
        }
        Assert.assertTrue("exchange plan exists", params.get("ПланОбменаСуществует").equals("true"));
    }

    @Test
    public void testParamIB_ExchangePlanExistsAndNodeDoesNotExist() {
        String serviceURLDataExchange = "*****.1cws?wsdl";
        String user = "*********";
        String password = "***********";
        String exchangePlanName = "ОбменУППЗарплатаИУправлениеПерсоналом25";
        String appCode = "SAL";
        String outFile = "/Users/magnifico/Documents/dev/projects/exchange1chr/out_data";
        String error = "";

        ExchangeService exchangeService = new ExchangeService();
        Map<String, String> params = null;
        try {
            params = exchangeService.getParamsIB(serviceURLDataExchange, user, password, exchangePlanName, "NOT");
            Assert.fail("node exchange plan not exists");
        } catch (ExchangeException e) {
            Assert.assertNotNull("Exception is trown",e);
            e.printStackTrace();
        }
    }

    @Test
    public void testParamIB_ExchangePlanAndNodeDoNotExist() {
        String serviceURLDataExchange = "*****.1cws?wsdl";
        String user = "*********";
        String password = "***********";
        String exchangePlanName = "ОбменУППЗарплатаИУправлениеПерсоналом25";
        String appCode = "SAL";
        String outFile = "/Users/magnifico/Documents/dev/projects/exchange1chr/out_data";
        String error = "";

        ExchangeService exchangeService = new ExchangeService();
        Map<String, String> params = null;
        try {
            params = exchangeService.getParamsIB(serviceURLDataExchange, user, password, "NOT", "NOT");
            Assert.fail("exchange plan does not exist");
        } catch (ExchangeException e) {
            Assert.assertNotNull("Exception is trown",e);
            e.printStackTrace();
        }
    }

    @Test
    public void testGetData()  {
        String serviceURLDataExchange = "*****.1cws?wsdl";
        String user = "*********";
        String password = "***********";
        String tableName = "Справочник.Подразделения";
        String out = "/Users/magnifico/Documents/dev/projects/hr/out.xml";
        ExchangeService exchangeService = new ExchangeService();
        try {
            exchangeService.getData(serviceURLDataExchange,user,password,tableName,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}