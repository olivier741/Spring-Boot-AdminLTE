/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Column;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"transaction_id", "msisdn", "product_id"})})
public class Register extends AbstractModel<Long> {

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "msisdn")
    private String msisdn;

    /**
     * 1 = active|active, 
     * 0 = active|cancel, 
     * 2 = active|pending, 
     * -1= cancel|cancel (state in network|state in service)
     */
    @Column(name = "status")
    private int status;

    @Column(name = "autoextend")
    private boolean autoextend;

    @Column(name = "reg_time")
    private Timestamp regTime;

    @UpdateTimestamp
    @Column(name = "renew_time")
    private Timestamp renewTime;

    @Column(name = "expire_time")
    private Timestamp expireTime;

    @Column(name = "unreg_time")
    private Timestamp unregTime;

    @Column(name = "cancel_time")
    private Timestamp cancelTime;

    @Column(name = "number_reg")
    private int numberReg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

}
