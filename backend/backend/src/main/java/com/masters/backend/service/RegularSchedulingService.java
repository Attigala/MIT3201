package com.masters.backend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.masters.backend.dto.RegularSchedulingDto;
import com.masters.backend.dto.TimeTableSlot;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.OnSiteRegular;
import com.masters.backend.model.OnlineExtra;
import com.masters.backend.model.OnlineRegular;
import com.masters.backend.model.OnsiteExtra;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.OnSiteRegularRepository;
import com.masters.backend.repository.OnlineExtraRepository;
import com.masters.backend.repository.OnlineRegularRepository;
import com.masters.backend.repository.OnsiteExtraRepository;

@Service
public class RegularSchedulingService {
	@Autowired
	OnSiteRegularRepository iOnSiteRegularRepository;
	@Autowired
	OnlineRegularRepository iOnlineRegularRepository;
	@Autowired
	OnsiteExtraRepository iOnsiteExtraRepository;
	@Autowired
	ClassContractRepository iClassContractRepository;
	@Autowired
	OnlineExtraRepository iOnlineExtraRepository;

	public RegularSchedulingService(OnSiteRegularRepository iOnsiteRegularRepository,
			OnlineRegularRepository iOnlineRegularRepository, OnsiteExtraRepository iOnsiteExtraRespostiory,
			ClassContractRepository classContractRepository, OnlineExtraRepository iOnlineExtraRepository) {
		this.iClassContractRepository = classContractRepository;
		this.iOnlineRegularRepository = iOnlineRegularRepository;
		this.iOnsiteExtraRepository = iOnsiteExtraRespostiory;
		this.iOnSiteRegularRepository = iOnsiteRegularRepository;
		this.iOnlineExtraRepository = iOnlineExtraRepository;
	}

	public RegularSchedulingDto createSchedule(RegularSchedulingDto schedule) {
		if (schedule.getMode() != null) {
			if ("Online".equalsIgnoreCase(schedule.getMode())) {
				OnlineRegular iOnlineRegular = new OnlineRegular();
				iOnlineRegular.setClassContract(schedule.getClassContractId());
				iOnlineRegular.setTeacherId(schedule.getTeacherId());
				iOnlineRegular.setDay(schedule.getDate());
				iOnlineRegular.setTimeSlot(schedule.getTimeSlot());
				iOnlineRegular = iOnlineRegularRepository.save(iOnlineRegular);
				schedule.setId(iOnlineRegular.getId());
			} else if ("onsite".equalsIgnoreCase(schedule.getMode())) {
				OnSiteRegular onsiteRegular = new OnSiteRegular();
				onsiteRegular.setTeacherId(schedule.getTeacherId());
				onsiteRegular.setClassContract(schedule.getClassContractId());
				onsiteRegular.setDay(schedule.getDate());
				onsiteRegular.setTimeSlot(schedule.getTimeSlot());
				onsiteRegular.setHallId(schedule.getHallId());
				ExampleMatcher matcher = ExampleMatcher.matching().withIncludeNullValues();
				OnSiteRegular available = iOnSiteRegularRepository.findOne(Example.of(onsiteRegular)).orElse(null);
				if (available == null) {
					onsiteRegular = iOnSiteRegularRepository.save(onsiteRegular);
				}
				schedule.setId(onsiteRegular.getId());
			}
		}
		return schedule;
	}

	public List<TimeTableSlot> getSchedules(String startDate, String endDate, Long hallId) {
		List<TimeTableSlot> timetableSlots = new ArrayList<TimeTableSlot>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = formatter.parse(startDate);
			Date end = formatter.parse(endDate);

			LocalDate startLD = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endLD = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			for (LocalDate date = startLD; date.isBefore(endLD); date = date.plusDays(1)) {
				String dayOfWeek = null;
				switch (date.getDayOfWeek()) {
				case MONDAY:
					dayOfWeek = "Monday";
					break;
				case TUESDAY:
					dayOfWeek = "Tuesday";
					break;
				case WEDNESDAY:
					dayOfWeek = "Wednesday";
					break;
				case THURSDAY:
					dayOfWeek = "Thursday";
					break;
				case FRIDAY:
					dayOfWeek = "Friday";
					break;
				case SATURDAY:
					dayOfWeek = "Saturday";
					break;
				case SUNDAY:
					dayOfWeek = "Sunday";
					break;
				default:
					break;
				}
				if (dayOfWeek != null) {
					List<OnSiteRegular> schedules = iOnSiteRegularRepository.findByDayAndHallId(dayOfWeek, hallId);
					for (OnSiteRegular schedule : schedules) {
						ClassContract classContract = iClassContractRepository.findById(schedule.getClassContract())
								.orElse(null);
						TimeTableSlot timeTableSlot = new TimeTableSlot();
						String[] times = schedule.getTimeSlot().split("-");
						timeTableSlot.setStart(date.toString() + " " + times[0]);
						timeTableSlot.setEnd(date.toString() + " " + times[1]);
						timeTableSlot.setTitle(classContract.getTeacher().getFirstName());
						timetableSlots.add(timeTableSlot);
					}

				}
			}
			List<OnsiteExtra> extraClasses = iOnsiteExtraRepository.findByHallIdAndDateBetween(hallId,
					java.sql.Date.valueOf(startLD), java.sql.Date.valueOf(endLD));
			for (OnsiteExtra extraClass : extraClasses) {
				ClassContract classContract = iClassContractRepository.findById(extraClass.getClassContract())
						.orElse(null);
				TimeTableSlot timeTableSlot = new TimeTableSlot();
				String[] times = extraClass.getTimeSlot().split("-");
				timeTableSlot.setStart(extraClass.getDate().toString() + " " + times[0]);
				timeTableSlot.setEnd(extraClass.getDate().toString() + " " + times[1]);
				timeTableSlot.setTitle(classContract.getTeacher().getFirstName());
				timetableSlots.add(timeTableSlot);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timetableSlots;
	}

	public List<TimeTableSlot> getOnlineSchedules(String startDate, String endDate) {
		List<TimeTableSlot> timetableSlots = new ArrayList<TimeTableSlot>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = formatter.parse(startDate);
			Date end = formatter.parse(endDate);

			LocalDate startLD = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endLD = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			for (LocalDate date = startLD; date.isBefore(endLD); date = date.plusDays(1)) {
				String dayOfWeek = null;
				switch (date.getDayOfWeek()) {
				case MONDAY:
					dayOfWeek = "Monday";
					break;
				case TUESDAY:
					dayOfWeek = "Tuesday";
					break;
				case WEDNESDAY:
					dayOfWeek = "Wednesday";
					break;
				case THURSDAY:
					dayOfWeek = "Thursday";
					break;
				case FRIDAY:
					dayOfWeek = "Friday";
					break;
				case SATURDAY:
					dayOfWeek = "Saturday";
					break;
				case SUNDAY:
					dayOfWeek = "Sunday";
					break;
				default:
					break;
				}
				if (dayOfWeek != null) {
					List<OnlineRegular> schedules = iOnlineRegularRepository.findByDay(dayOfWeek);
					for (OnlineRegular schedule : schedules) {
						ClassContract classContract = iClassContractRepository.findById(schedule.getClassContract())
								.orElse(null);
						TimeTableSlot timeTableSlot = new TimeTableSlot();
						String[] times = schedule.getTimeSlot().split("-");
						timeTableSlot.setStart(date.toString() + " " + times[0]);
						timeTableSlot.setEnd(date.toString() + " " + times[1]);
						timeTableSlot.setTitle(classContract.getTeacher().getFirstName() + classContract.getSubject());
						timetableSlots.add(timeTableSlot);
					}

				}
			}
			List<OnlineExtra> extraClasses = iOnlineExtraRepository.findByDateBetween(java.sql.Date.valueOf(startLD), java.sql.Date.valueOf(endLD));
			for (OnlineExtra extraClass : extraClasses) {
				ClassContract classContract = iClassContractRepository.findById(extraClass.getClassContract())
						.orElse(null);
				TimeTableSlot timeTableSlot = new TimeTableSlot();
				String[] times = extraClass.getTimeSlot().split("-");
				timeTableSlot.setStart(extraClass.getDate().toString() + " " + times[0]);
				timeTableSlot.setEnd(extraClass.getDate().toString() + " " + times[1]);
				timeTableSlot.setTitle(classContract.getTeacher().getFirstName() + " " + classContract.getSubject());
				timetableSlots.add(timeTableSlot);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timetableSlots;
	}
}
