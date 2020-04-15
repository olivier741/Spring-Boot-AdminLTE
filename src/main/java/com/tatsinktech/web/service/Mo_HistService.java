/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Mo_Hist;
import com.tatsinktech.web.repository.Mo_HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class Mo_HistService extends AbstractService<Mo_Hist, Long>{
     @Autowired
    private Mo_HistRepository mo_histRepository;

    @Override
    protected JpaRepository<Mo_Hist, Long> getRepository() {
        return mo_histRepository;
    }
}
