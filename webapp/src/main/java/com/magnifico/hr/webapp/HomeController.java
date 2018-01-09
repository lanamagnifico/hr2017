package com.magnifico.hr.webapp;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.magnifico.hr.dao.PersonDao;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    PersonDao personDao;

    @Autowired
    AmqpTemplate template;

    final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(method = GET)
    public String home(ModelMap modelMap) {

        int count = personDao.getCount();
        logger.info("Emit to queue1");
        template.convertAndSend("queue1","Message to queue");
        modelMap.addAttribute("count",count);
        return "home";
    }

    @RequestMapping("/queue")
    @ResponseBody
    String queue1() {
        logger.info("Emit to queue");
        for(int i = 0;i<5;i++)
            template.convertAndSend("email","Mail # " + (i+1));
        for(int i = 0;i<5;i++)
            template.convertAndSend("notification","Note # " + (i+1));
        return "Emit to queue";
    }
}
