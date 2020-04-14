/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
public class Mt_Hist extends AbstractModel<Long>{

    private String transaction_id;
    private String msisdn;
    
    @Lob 
    private String message;
    
    private String channel;
    private Timestamp receive_time;
    
    @UpdateTimestamp
    private Timestamp send_time;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "command_id", nullable = true)
    private Command command;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
   
    private String process_unit;
    private String IP_unit;

}
