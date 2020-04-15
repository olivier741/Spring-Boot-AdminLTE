/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Promotion;
import com.tatsinktech.web.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class PromotionService extends AbstractService<Promotion, Long>{
     @Autowired
    private PromotionRepository promotionRepository;

    @Override
    protected JpaRepository<Promotion, Long> getRepository() {
        return promotionRepository;
    }
}
