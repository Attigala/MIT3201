package com.masters.backend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.masters.backend.dto.OnlineMeetingDto;
import com.masters.backend.model.Meeting;
import com.masters.backend.repository.MeetingRepository;

@Service
public class GoogleCalendarService {
	
	private static final String APPLICATION_NAME = "widumaga-calendar";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final java.io.File DATA_STORE_DIR =
		      new java.io.File(System.getProperty("user.home"), ".store/calendar_sample");
	private static FileDataStoreFactory dataStoreFactory;
	
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new java.io.InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("Widumanga-Web");
        //returns an authorized Credential object.
        return credential;
    }
	
	public static Event createEvent(OnlineMeetingDto onlineMeetingDto) throws Throwable {
		NetHttpTransport HTTP_TRANSPORT;
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
			//"2022-09-28T17:00:00-07:00" DATE TIME FORMAT
			System.out.println(onlineMeetingDto.getMeetingDate()+"T"+onlineMeetingDto.getMeetingEndTime()+"+05:30");
			Event event = new Event().setSummary(onlineMeetingDto.getSubject());
			DateTime startDateTime = new DateTime(onlineMeetingDto.getMeetingDate()+"T"+onlineMeetingDto.getMeetingStartTime()+"+05:30");
			EventDateTime start = new EventDateTime()
			    .setDateTime(startDateTime)
			    .setTimeZone("Asia/Colombo");
			event.setStart(start);

			DateTime endDateTime = new DateTime(onlineMeetingDto.getMeetingDate()+"T"+onlineMeetingDto.getMeetingEndTime()+"+05:30");
			EventDateTime end = new EventDateTime()
			    .setDateTime(endDateTime)
			    .setTimeZone("Asia/Colombo");
			event.setEnd(end);

//			String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
//			event.setRecurrence(Arrays.asList(recurrence));

			EventAttendee[] attendees = new EventAttendee[] {
			    new EventAttendee().setEmail(onlineMeetingDto.getEmail())
			};
			event.setAttendees(Arrays.asList(attendees));

			EventReminder[] reminderOverrides = new EventReminder[] {
			    new EventReminder().setMethod("email").setMinutes(24 * 60),
			    new EventReminder().setMethod("popup").setMinutes(10),
			};
			Event.Reminders reminders = new Event.Reminders()
			    .setUseDefault(false)
			    .setOverrides(Arrays.asList(reminderOverrides));
			event.setReminders(reminders);

			String calendarId = "primary";
			event = service.events().insert(calendarId, event).execute();
			
			System.out.printf("Event created: %s\n", event.getHtmlLink());
			return event;
	}
	
	public static void main(String args[]) {
		OnlineMeetingDto onlineMeetingDto = new OnlineMeetingDto();
		try {
			createEvent(onlineMeetingDto);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
