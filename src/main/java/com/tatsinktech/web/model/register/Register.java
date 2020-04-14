/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"transaction_id","msisdn" , "product_id"})})
public class Register extends AbstractModel<Long>{

    private String transaction_id;
    private String msisdn;        // 1 = active|active, 0 = active|cancel, 2=active|pending, -1=cancel|cancel (state in network|state in service)
    private int status;
    private boolean autoextend;
    
    private Timestamp reg_time;
    
    @UpdateTimestamp
    private Timestamp Renew_time;
    
    private Timestamp expire_time;
    private Timestamp unreg_time;
    private Timestamp cancel_time;
    
    private int number_reg;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

   
}
