/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hendisantika.adminlte.repository;

import com.hendisantika.adminlte.model.register.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

}
