package com.magnifico.hr.dao;

import com.magnifico.hr.model.Person;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.activation.DataSource;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
public class PersonDaoTest {

    private static PersonDaoTest instance;
    private PersonDao personDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public  void init(){
        personDao = new PersonDao();
        personDao.setJdbcTemplate(jdbcTemplate);
    }

    @After
    public void clear() {
        String query="delete from persons where id='1'";
        jdbcTemplate.update(query);
    }
    @Test
    public void testSavePerson() throws Exception {
        int status = personDao.savePerson(new Person(1,"Mike"));
        Assert.assertEquals("Check save status:", 1,status);
        Assert.assertEquals("Check rows count:",1,personDao.getCount());
        Map<String, Object> mapPerson = personDao.getById(1);
        Assert.assertEquals("Check new person:", "Mike",mapPerson.get("name").toString());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        personDao.savePerson(new Person(1,"Mike"));
        int status = personDao.updatePerson(new Person(1,"Alex"));
        Assert.assertEquals("Check update status:",1,status);
        Assert.assertEquals("Check rows count:",1,personDao.getCount());
        Map<String, Object> mapPerson = personDao.getById(1);
        Assert.assertEquals("Check new name:","Alex",mapPerson.get("name").toString());
    }

    @Test
    public void testDeletePerson() throws Exception {
        personDao.savePerson(new Person(1,"Mike"));
        Assert.assertEquals("Check rows count:",1,personDao.getCount());
        int status = personDao.deletePerson(new Person(1,"Alex"));
        Assert.assertEquals("Check delete status:",1,status);
        Assert.assertEquals("Check empty table:",0,personDao.getCount());
    }
}