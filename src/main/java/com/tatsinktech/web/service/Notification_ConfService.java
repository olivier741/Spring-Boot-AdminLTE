/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Notification_Conf;
import com.tatsinktech.web.repository.Notification_ConfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */

@Service
public class Notification_ConfService extends AbstractService<Notification_Conf, Long>{
     @Autowired
    private Notification_ConfRepository notifConfRepository;

    @Override
    protected JpaRepository<Notification_Conf, Long> getRepository() {
        return notifConfRepository;
    }
}