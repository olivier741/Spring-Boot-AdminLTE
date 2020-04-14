/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hendisantika.adminlte.model.register;


import com.hendisantika.adminlte.model.AbstractModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "service")
public class Service extends AbstractModel<Long> {

    @Column(nullable = false, unique = true)
    private String service_name;
    
    @Column(nullable = true)
    private String receive_channel;
    
    @Column(nullable = true)
    private String send_channel;
    
    @Column(nullable = true)
    private String service_provider;
    
    @Column(nullable = true)
    private String service_description;
    
    @Column(nullable = true)
    @CreationTimestamp
    private Timestamp create_time;
    
    @Column(nullable = true)
    @UpdateTimestamp
    private Timestamp update_time;
    
    
    @OneToMany(mappedBy = "service")
    private Set<Product> listProduct = new HashSet<>();

  
}
