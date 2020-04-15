/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Charge_Hist;
import com.tatsinktech.web.repository.Charge_HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class Charge_HistService extends AbstractService<Charge_Hist, Long> {

    @Autowired
    private Charge_HistRepository charge_histRepository;

    @Override
    protected JpaRepository<Charge_Hist, Long> getRepository() {
        return charge_histRepository;
    }

}
