package com.magnifico.hr.dao;

import com.magnifico.hr.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int savePerson(Person p){
        String query="insert into persons values('"+p.getId()+"','"+p.getName()+"')";
        return jdbcTemplate.update(query);
    }

    public int updatePerson(Person p){
        String query="update persons set name='"+p.getName()+"' where id='"+p.getId()+"'";
        return jdbcTemplate.update(query);
    }

    public int deletePerson(Person p){
        String query="delete from persons where id='"+p.getId()+"' ";
        return jdbcTemplate.update(query);
    }

    public Map<String, Object> getById(int id){
        String query="select * from persons where id='"+id+"' ";
        return jdbcTemplate.queryForMap(query);
    }

    public int getCount(){
        String sql = "select count(*) from persons";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
