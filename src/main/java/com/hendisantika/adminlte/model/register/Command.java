/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hendisantika.adminlte.model.register;

import com.hendisantika.adminlte.model.AbstractModel;
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
import javax.persistence.UniqueConstraint;
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
@Table(name = "command",uniqueConstraints={@UniqueConstraint(columnNames = {"command_code","action_id"})})
public class Command extends AbstractModel<Long> {
        
    @Column(nullable = false, unique = true)
    private String command_name;
    
    @Column(nullable = false, unique = false)
    private String command_code;
    
    @Column(nullable = true)
    private String split_separator;
     
    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    @CreationTimestamp
    private Timestamp create_time;
    
    @Column(nullable = true)
    @UpdateTimestamp
    private Timestamp update_time;
   
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_id", nullable = true)
    private Action action;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parameter_id", nullable = true)
    private Parameter parameter;
    
    @OneToMany(mappedBy = "command")
    private Set<Mo_Hist> listMo_Hist = new HashSet<>();
    
    @OneToMany(mappedBy = "command")
    private Set<Mt_Hist> listMt_Hist = new HashSet<>();
    
    @OneToMany(mappedBy = "command")
    private Set<Notification_Conf> listConfig = new HashSet<>();

     
}
