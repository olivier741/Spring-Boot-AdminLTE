/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Parameter;
import com.tatsinktech.web.model.register.Register;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */
@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {

    Page<Register> findByMsisdnContainingIgnoreCase(String msisdn, Pageable pageable);

    Page<Register> findByTransactionIdContainingIgnoreCase(String transactionId, Pageable pageable);

    Page<Register> findByStatusContainingIgnoreCase(int status, Pageable pageable);

    Page<Register> findByAutoextendContainingIgnoreCase(boolean autoextend, Pageable pageable);

    List<Register> findByRegTimeContainingIgnoreCase(Date reg_time, Pageable pageable);

    List<Register> findByRenewTimeContainingIgnoreCase(Date renewTime, Pageable pageable);

    List<Register> findByExpireTimeContainingIgnoreCase(Date expireTime, Pageable pageable);

    List<Register> findByUnregTimeContainingIgnoreCase(Date unregTime, Pageable pageable);

    List<Register> findByCancelTimeContainingIgnoreCase(Date cancelTime, Pageable pageable);

    List<Register> findByNumberRegContainingIgnoreCase(long numberReg, Pageable pageable);

    Page<Register> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Register> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<Register> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);

    List<Register> findAllByRegTimeBetween(Date regTimeStart, Date regTimeEnd, Pageable pageable);

    List<Register> findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String msisdn, Pageable pageable);

   List<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, Pageable pageable);

   List<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId,String msisdn, Pageable pageable);

}
