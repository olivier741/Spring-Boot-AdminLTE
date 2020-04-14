/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hendisantika.adminlte.model.register;

import com.hendisantika.adminlte.model.AbstractModel;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

/**
 *
 * @author olivier.tatsinkou
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Subselect(
"   SELECT cmd.command_id,  \n"+
"          cmd.command_code,  \n" +
"	   cmd.split_separator, \n" +
"	   cmd.channel, \n" +
"	   ac.action_name,  \n" +
"	   ac.action_type,  \n" +
"	   ac.action_description,    \n" +
"	   pro.end_date, \n" +
"	   pro.end_reg_time, \n" +
"	   pro.extend_fee,   \n" +
"	   pro.isextend, \n" +
"	   pro.pending_duration, \n" +
"	   pro.product_code, \n" +
"	   pro.reg_fee,  \n" +
"	   pro.register_day, \n" +
"	   pro.restrict_product, \n" +
"	   pro.start_date,   \n" +
"	   pro.start_reg_time,   \n" +
"	   pro.validity, \n" +
"	   ser.service_name, \n" +
"          ser.receive_channel, \n" +
"          ser.send_channel, \n" +
"          ser.service_provider, \n" +
"          ser.service_description,\n" +
"          ser.create_time, \n" +
"	   pr.param_name,   \n" +
"	   pr.param_pattern,    \n" +
"	   pr.param_length,   \n" +
"	   pr.paramDescription  \n" +
"   FROM Command cmd   \n" +
"   LEFT JOIN PARAM pr ON cmd.command_id = pr.command_id \n" +
"   LEFT JOIN Action ac ON cmd.action_id = ac.action_id \n" +
"   LEFT JOIN Product pro ON ac.product_id = pro.product_id \n" +
"   LEFT JOIN Service ser ON pro.service_id = ser.service_id ")
@Synchronize({"Command", "PARAM","Product","Service"})
@Immutable
public class Request_Conf extends AbstractModel<Long>{

    private String command_code;
    private String split_separator;
    private String channel;
    
    private String action_name;
    
    @Enumerated(EnumType.STRING)
    private Action_Type action_type;
    
    private String action_description;
    
    private String product_code;
    private Long reg_fee;
    private String restrict_product; 
    private String register_day;     // 'Day allow register: 0-Sunday, 1-Monday, 2-Tuesday, 3-Wednesday, ... not information mean registration every day'
    private Timestamp start_date;    //  2019-04-16 23:00:01-07:00:00  this promotion will launch  the 2019-04-16 at 11PM 
    private Timestamp end_date;      //  2050-04-16 23:00:01-07:00:00  this promotion will end  the 2050-04-16 at 11PM 
    private Time start_reg_time;     //  23:00:01  star time in one day from when customer allow to register
    private Time end_reg_time;       //  07:00:00  end time in one day from when customer not allow to register
    private Boolean isextend;
    private Long extend_fee;
    private String validity;          // D1 mean customer must have this offer for one Day 
    private String pending_duration;  
    private String service_name;
    private String receive_channel;
    private String send_channel;
    private String service_provider;
    private String service_description;
    
    private String param_name;
    private Integer param_length;
    private String param_pattern;
    private String paramDescription; 

   
   
}
