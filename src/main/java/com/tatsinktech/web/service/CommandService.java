/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Command;
import com.tatsinktech.web.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */

@Service
public class CommandService extends AbstractService<Command, Long>{
     @Autowired
    private CommandRepository commandRepository;

    @Override
    protected JpaRepository<Command, Long> getRepository() {
        return commandRepository;
    }
}
