/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
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
@Check(constraints = "charge_fee >= 0")
public class Charge_Hist extends AbstractModel<Long> {

    private String transaction_id;
    private String msisdn;

    @Enumerated(EnumType.STRING)
    private Charge_Event charge_mode;

    @UpdateTimestamp
    private Timestamp charge_time;
    private long charge_fee;

    @Lob
    private String charge_request;

    @Lob
    private String charge_response;

    private long duration;

    private String charge_error;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    private String process_unit;
    private String IP_unit;

}
