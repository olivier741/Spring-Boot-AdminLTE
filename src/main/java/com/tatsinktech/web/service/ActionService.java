package com.tatsinktech.web.service;

import com.tatsinktech.web.model.register.Action;
import com.tatsinktech.web.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier.tatsinkou
 */
@Service
public class ActionService extends AbstractService<Action, Long> {

    @Autowired
    private ActionRepository actionRepository;

    @Override
    protected JpaRepository<Action, Long> getRepository() {
        return actionRepository;
    }
}
