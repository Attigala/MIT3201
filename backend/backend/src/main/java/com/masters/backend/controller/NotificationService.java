package com.masters.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.OnlineMeetingDto;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.ExtraClassNotification;
import com.masters.backend.model.NotifyClassSubscriber;
import com.masters.backend.model.RequestSubscription;
import com.masters.backend.model.Student;
import com.masters.backend.model.StudentClass;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.ExtraClassNotificationRepository;
import com.masters.backend.repository.NotifyClassSubscriberRepository;
import com.masters.backend.repository.OnsiteExtraRepository;
import com.masters.backend.repository.RequestSubscriptionRepository;
import com.masters.backend.repository.StudentClassRepository;
import com.masters.backend.repository.StudentRepository;
import com.masters.backend.service.EmailService;
import com.masters.backend.service.GoogleCalendarService;

@Service
public class NotificationService {
	
	@Autowired private ExtraClassNotificationRepository iExtraClassNotificationRepository;
	@Autowired private OnsiteExtraRepository iOnsiteExtraRepository;
	@Autowired private ClassContractRepository iClassContractRepository;
	@Autowired private EmailService iEmailService;
	@Autowired private StudentClassRepository iStudentClassRepository;
	@Autowired private StudentRepository iStudentRepository;
	@Autowired private NotifyClassSubscriberRepository iNotifyClassSubscriberRepository;
	@Autowired private RequestSubscriptionRepository iRequestSubscriptionRepository;
	@Autowired private GoogleCalendarService iGoogleCalendarService;
	
	@Scheduled(fixedRate = 300000)
	public void sendNotification() {
		System.out.println("Scheduled task started - Extra Class Notification");
		List<ExtraClassNotification> extraClassNotifications = iExtraClassNotificationRepository.findByStatus("NEW");
		for (ExtraClassNotification extraClassNotification : extraClassNotifications) {

			List<StudentClass> studentClasses = iStudentClassRepository
					.findByClassContractId(extraClassNotification.getClassId());
			String message = "Class has been scheduled for " + extraClassNotification.getClassName() + " on "
					+ extraClassNotification.getDate() + " at " + extraClassNotification.getTime();
			String subject = "An Extra Class has been scheduled";
			for (StudentClass studentClass : studentClasses) {
				Student student = iStudentRepository.findById(studentClass.getStudentId()).orElse(null);

				if (student != null) {
					iEmailService.sendNotification("devniattigala@gmail.com", subject, message);
				}
			}
			if (extraClassNotification.getMode().equalsIgnoreCase("ONLINE")) {
				OnlineMeetingDto dto = new OnlineMeetingDto();
				dto.setClassId(extraClassNotification.getClassId());
				//TODO: Replace this with teacher email. Added because there are dummy data for email 
				//which will throw an error, when creating a meeting
				dto.setEmail("anuthara.attigala@gmail.com");
				dto.setMeetingDate(extraClassNotification.getDate().toString());
				String times[] = extraClassNotification.getTime().split("-");
				//"2022-09-28T17:00:00-07:00" DATE TIME FORMAT
				dto.setMeetingStartTime(times[0]+":00");
				dto.setMeetingEndTime(times[1]+":00");
				dto.setSubject("Extra Class " + extraClassNotification.getClassName() );
				try {
					iGoogleCalendarService.createEvent(dto);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			extraClassNotification.setStatus("PROCESSED");
			iExtraClassNotificationRepository.save(extraClassNotification);

		}
		System.out.println("Scheduled task Ended - Extra Class Notification");
		System.out.println("Processed Requests "+ extraClassNotifications.size());
	}
	
	@Scheduled(fixedRate = 180000)
	public void sendNotificationToSubscriber() {
		System.out.println("Scheduled task started - Send Notification to subscriber");
		List<NotifyClassSubscriber> pendingNotifications = iNotifyClassSubscriberRepository.findByStatus("NEW");
		for (NotifyClassSubscriber pendingNotification : pendingNotifications) {
			List<RequestSubscription> subscribers = iRequestSubscriptionRepository.findBySubjectAndMediumAndGrade(
					pendingNotification.getSubject(), pendingNotification.getMedium(), pendingNotification.getGrade());
			for(RequestSubscription requestSubscription: subscribers) {
				if (requestSubscription.getEmail() != null) {
					//TODO : change email to requestSubscription.getEmail()
					iEmailService.sendNotification("devniattigala@gmail.com", "Hello From Widumaga!" , pendingNotification.getMessage());
				}
			}
			pendingNotification.setStatus("PROCESSED");
			iNotifyClassSubscriberRepository.save(pendingNotification);
		}
		System.out.println("Scheduled task ended - Send Notification to subscriber. Requests processed" + pendingNotifications.size());
	}

}
