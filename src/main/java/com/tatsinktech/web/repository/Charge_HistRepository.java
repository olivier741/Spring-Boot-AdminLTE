/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Charge_Hist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface Charge_HistRepository extends JpaRepository<Charge_Hist, Long>{
    
}
