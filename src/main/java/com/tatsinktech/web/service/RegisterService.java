/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Register;
import com.tatsinktech.web.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class RegisterService extends AbstractService<Register, Long>{
     @Autowired
    private RegisterRepository registerRepository;

    @Override
    protected JpaRepository<Register, Long> getRepository() {
        return registerRepository;
    }
}
