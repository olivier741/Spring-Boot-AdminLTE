/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.gateway_api;

import com.tatsinktech.web.model.AbstractModel;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


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
@Table(name = "ws_transaction_log")
public class WS_Transaction_Log extends AbstractModel<Long> {

     @Column(name = "transaction_id",nullable = false)
    private String transactionId;
    
    @Column(name = "msisdn",nullable = false)
    private String msisdn;
    
    @Column(name = "operator",nullable = false)
    private String operator;
    
    @Column(name = "client_name",nullable = false)
    private String clientName;
    
    @Column(name = "webservice_name",nullable = false)
    private String webserviceName;
     
    @Lob 
    @Column(name = "charge_request",nullable = false)
    private String chargeRequest;
    
    @Lob 
    @Column(name = "charge_response",nullable = false)
    private String chargeResponse;
    
    @Column(name = "duration",nullable = false)
    private long duration;
     
    @Column(name = "error_ode",nullable = false)
    private String errorCode;
       
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ws_access_mng_id", nullable = true)
    private WS_AccessManagement access_management;
    


  
}
