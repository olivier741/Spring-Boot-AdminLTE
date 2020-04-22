/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


/**
 *
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product")
@Check(constraints = "reg_fee >= 0 AND extend_fee >= 0")
public class Product extends AbstractModel<Long> {
            
    @Column(nullable = false, unique = true)
    private String product_code;
    
    @Column(nullable = true)
    private long reg_fee;
    
    @Column(nullable = true)
    private String restrict_product;  // list of restric product separate by | (e.g : CAN1|CAN2)
    
    @Column(nullable = true)
    private String register_day;      // 'Day allow register: (e.g 1|2|3) 0-Sunday, 1-Monday, 2-Tuesday, 3-Wednesday, ... not information mean registration every day'
    
    @Column(nullable = true)
    private Timestamp start_date;     //  2019-04-16 23:00:01-07:00:00  this promotion will launch  the 2019-04-16 at 11PM 
    
    @Column(nullable = true)
    private Timestamp end_date;       //  2050-04-16 23:00:01-07:00:00  this promotion will end  the 2050-04-16 at 11PM 
   
    @Column(nullable = true)
    private Time start_reg_time;      //  23:00:01  star time in one day from when customer allow to register
    
    @Column(nullable = true)
    private Time end_reg_time;        //  07:00:00  end time in one day from when customer not allow to register   
    
    @Column(nullable = true)
    private boolean isextend = true;
    
    @Column(nullable = true)
    private boolean isOveride_reg = true;
    
    @Column(nullable = true)
    private boolean isNotify_extend=false;
    
    @Column(nullable = true)
    private long extend_fee;
    
    @Column(nullable = true)
    private String validity;          // D1 mean customer must have this offer for one Day, H5 mean customer must have this offer for 5 hours
    
    @Column(nullable = true)
    private String pending_duration;  // D30 mean customer pending 30 Day on this offert, he is cancel (system will not try to extend) 
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id", nullable = true)
    private Promotion promotion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = true)
    private ServiceProvider service;
   
    @OneToMany(mappedBy = "product")
    private Set<Action> listAction = new HashSet<>();
    
    @OneToMany(mappedBy = "product")
    private Set<Register> listRegister= new HashSet<>();
    
    @OneToMany(mappedBy = "product")
    private Set<Charge_Hist> listCharge_Hist= new HashSet<>();
    
    @OneToMany(mappedBy = "product")
    private Set<Mo_Hist> listMo_Hist= new HashSet<>();
    
    @OneToMany(mappedBy = "product")
    private Set<Mt_Hist> listMt_Hist= new HashSet<>();

    
}
