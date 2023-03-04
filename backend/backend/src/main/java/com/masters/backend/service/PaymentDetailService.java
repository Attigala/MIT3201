package com.masters.backend.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.backend.controller.PaymentDetail;
import com.masters.backend.dto.PaymentDto;
import com.masters.backend.model.Student;
import com.masters.backend.repository.PaymentDetailRepository;
import com.masters.backend.repository.StudentRepository;

@Service
public class PaymentDetailService {
	@Autowired PaymentDetailRepository iPaymentDetailRepository;
	@Autowired StudentRepository iStudentRepository;
	
	//TODO: Check if amount is equal to class amount else don't save payment details send error
	public PaymentDto create(PaymentDto payment) {
		return null;
	}

	private PaymentDetail convertToPaymentDetail(PaymentDto payment) {
		PaymentDetail paymentDetail = new PaymentDetail();
		paymentDetail.setStudentId(payment.getStudentId());
		paymentDetail.setClassId(payment.getClassId());
		paymentDetail.setDatePayed(new Date(Calendar.getInstance().getTime().getTime()));
		paymentDetail.setMonth(payment.getMonth());
		paymentDetail.setYear(payment.getYear());
		paymentDetail.setAmount(payment.getAmount());
		
		return paymentDetail;
	}
	

	
	private PaymentDto covertToDto(PaymentDetail paymentDetail) {
		PaymentDto payment = new PaymentDto();
		if(paymentDetail!=null) {
			payment.setPaymentId(paymentDetail.getId());
			payment.setClassId(paymentDetail.getClassId());
			payment.setStudentId(payment.getStudentId());
			payment.setStatus("SUCCESS");
		} else {
			payment.setStatus("ERROR");
		}
		return payment;
	}

}
