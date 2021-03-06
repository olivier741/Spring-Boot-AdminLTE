/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import com.tatsinktech.web.model.register.Notification_Conf;
import com.tatsinktech.web.model.register.Parameter;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */

@Repository
public interface Notification_ConfRepository extends PagingAndSortingRepository<Notification_Conf, String> {
    Notification_Conf findNotification_ConfById(long id);
    Page<Notification_Conf>findByIdContainingIgnoreCase(long id, Pageable pageable);
    Page<Notification_Conf>findByNoficationNameContainingIgnoreCase(String noficationName, Pageable pageable);
    Page<Notification_Conf>findByNotificationValueContainingIgnoreCase(String notificationValue, Pageable pageable);
    Page<Notification_Conf>findByDefaultValueContainingIgnoreCase(String defaultValue, Pageable pageable);
    
    Page<Notification_Conf>findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Notification_Conf>findByCreateTimeContainingIgnoreCase(Date create_time, Pageable pageable);
    Page<Notification_Conf>findByUpdateTimeContainingIgnoreCase(Date update_time, Pageable pageable);

}
