/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.model.register;

import com.tatsinktech.web.model.AbstractModel;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "parameter")
public class Parameter extends AbstractModel<Long> {

    @Column(nullable = false, unique = true)
    private String param_name;

    @Column(nullable = true)
    private int param_length;

    @Column(nullable = true)
    private String param_pattern;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    @CreationTimestamp
    private Timestamp create_time;

    @Column(nullable = true)
    @UpdateTimestamp
    private Timestamp update_time;

    @OneToMany(mappedBy = "parameter")
    private Set<Command> listCommand = new HashSet<>();

}
