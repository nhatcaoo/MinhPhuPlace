package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.EmployeeInfo;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.common.MailConstants;
import com.ncl.backend.model.BookingRequest;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.EmployeeInfoRepository;
import com.ncl.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Override
    public ServiceResult sendDemoMail(BookingRequest bookingRequest) throws Exception {
        if (bookingRequest == null) {
            throw new NullObjectException("Thông tin không chính xác");
        }

        //Declare
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        //Get Employee Email
        List<EmployeeInfo> listEmployee = employeeInfoRepository.findAll();
        if (listEmployee.isEmpty()) {
            throw new Exception();
        }

        String[] toEmail = new String[listEmployee.size()];
        int count = 0;
        for (EmployeeInfo employeeInfo : listEmployee) {
            toEmail[count] = employeeInfo.getEmail();
            count++;
        }
        helper.setTo(toEmail);
        // Mail Content
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        String text = "\tFull name: " + bookingRequest.getFullName() + "\n"
                + "\tPhone number: " + bookingRequest.getPhoneNumber() + "\n"
                + "\tService order: " + bookingRequest.getServiceName() + "\n"
                + "\tOrder time: " + dtf.format(now);

        helper.setFrom(MailConstants.MY_EMAIL);
        helper.setSubject("Service request!");
        helper.setText(text);

        /*
        *Attach File Method
        *
        *
        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
        */

        //Send Email
        emailSender.send(message);

        return new ServiceResult(null, ServiceResult.SUCCESS, "Thông tin đã được ghi nhận thành công.");
    }
}
