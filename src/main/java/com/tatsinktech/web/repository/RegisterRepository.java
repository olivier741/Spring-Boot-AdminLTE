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
    
    Register findRegisterById(long id);

    Page<Register> findByMsisdnContainingIgnoreCase(String msisdn, Pageable pageable);
    
    Page<Register> findByMsisdnIgnoreCase(String msisdn, Pageable pageable);

    Page<Register> findByTransactionIdContainingIgnoreCase(String transactionId, Pageable pageable);
    
    Page<Register> findByTransactionIdIgnoreCase(String transactionId, Pageable pageable);

    Page<Register> findByStatusContainingIgnoreCase(int status, Pageable pageable);

    Page<Register> findByAutoextendContainingIgnoreCase(boolean autoextend, Pageable pageable);

    Page<Register> findByRegTimeContainingIgnoreCase(Date reg_time, Pageable pageable);

    Page<Register> findByRenewTimeContainingIgnoreCase(Date renewTime, Pageable pageable);

    Page<Register> findByExpireTimeContainingIgnoreCase(Date expireTime, Pageable pageable);

    Page<Register> findByUnregTimeContainingIgnoreCase(Date unregTime, Pageable pageable);

    Page<Register> findByCancelTimeContainingIgnoreCase(Date cancelTime, Pageable pageable);

    Page<Register> findByNumberRegContainingIgnoreCase(long numberReg, Pageable pageable);

    Page<Register> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Page<Register> findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);

    Page<Register> findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);
    
    // search repository

    Page<Register> findByRegTimeBetween(Date regTimeStart, Date regTimeEnd, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndMsisdnContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String msisdn, Pageable pageable);
    
    Page<Register> findAllByRegTimeBetweenAndMsisdnIgnoreCase(Date regTimeStart, Date regTimeEnd, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, Pageable pageable);
    
     Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnContainingIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdContainingIgnoreCaseAndMsisdnIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, String msisdn, Pageable pageable);

    Page<Register> findAllByRegTimeBetweenAndTransactionIdIgnoreCaseAndMsisdnIgnoreCase(Date regTimeStart, Date regTimeEnd, String transactionId, String msisdn, Pageable pageable);

}
