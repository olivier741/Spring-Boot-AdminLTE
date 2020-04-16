/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import javax.persistence.Entity;
import java.util.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


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
@Table(name = "promotion")
@Check(constraints = "promotion_reg_fee >= 0 and promotion_ext_fee >=0")
public class Promotion extends AbstractModel<Long> {
    
    @Column(nullable = false, unique = true)
    private String promotion_name;
    
    @Enumerated(EnumType.STRING)
    private Promo_Filter promotion_filter;
        
    @Column(nullable = true)
    private String msisdn_table;
    
    @Column(nullable = true)
    private String msisdn_regex;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date start_time;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date end_time;
    
    @Enumerated(EnumType.STRING)
    private Reduction_Type reduction_mode;

    @Column(nullable = true)
    private long promotion_reg_fee;
    
    @Column(nullable = true)
    private long percentage_reg;
    
    @Column(nullable = true)
    private boolean isExtend;
    
    @Column(nullable = true)
    private long promotion_ext_fee;
    
    @Column(nullable = true)
    private long percentage_ext;
    
    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    @CreationTimestamp
    private Timestamp create_time;
    
    @Column(nullable = true)
    @UpdateTimestamp
    private Timestamp update_time;
    
    @OneToMany(mappedBy = "promotion")
    protected Set<Product> listProduct = new HashSet<>();

    public boolean isIsExtend() {
        return isExtend;
    }

    public void setIsExtend(boolean isExtend) {
        this.isExtend = isExtend;
    }

   
}
