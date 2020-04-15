/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Mt_Hist;
import com.tatsinktech.web.repository.Mt_HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */

@Service
public class Mt_HistService extends AbstractService<Mt_Hist, Long>{
     @Autowired
    private Mt_HistRepository mt_histRepository;

    @Override
    protected JpaRepository<Mt_Hist, Long> getRepository() {
        return mt_histRepository;
    }
}