/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
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
    
    @Column(nullable = true)
    private Timestamp start_time;
    
    @Column(nullable = true)
    private Timestamp end_time;
    
    @Enumerated(EnumType.STRING)
    private Reduction_Type reduction_mode;

    @Column(nullable = true)
    private long promotion_reg_fee;
    
    @Column(nullable = true)
    private long percentage_reg;
    
    @Column(nullable = true)
    private boolean isExtend = false;
    
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

   
}
