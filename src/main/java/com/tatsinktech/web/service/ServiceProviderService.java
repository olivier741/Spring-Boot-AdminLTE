/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.tatsinktech.web.repository.ServiceProviderRepository;

/**
 *
 * @author olivier
 */
@Service
public class ServiceProviderService  extends AbstractService<ServiceProvider, Long>{
     @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Override
    protected JpaRepository<ServiceProvider, Long> getRepository() {
        return serviceProviderRepository;
    }
}
