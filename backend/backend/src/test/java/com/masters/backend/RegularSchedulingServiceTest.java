package com.masters.backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.masters.backend.dto.TimeTableSlot;
import com.masters.backend.model.ClassContract;
import com.masters.backend.model.OnSiteRegular;
import com.masters.backend.model.OnsiteExtra;
import com.masters.backend.model.Teacher;
import com.masters.backend.repository.ClassContractRepository;
import com.masters.backend.repository.OnSiteRegularRepository;
import com.masters.backend.repository.OnlineExtraRepository;
import com.masters.backend.repository.OnlineRegularRepository;
import com.masters.backend.repository.OnsiteExtraRepository;
import com.masters.backend.service.RegularSchedulingService;

public class RegularSchedulingServiceTest {
	
	private static OnSiteRegularRepository iOnSiteRegularRepository = Mockito.mock(OnSiteRegularRepository.class);
	private static OnlineRegularRepository iOnlineRegularRepository = Mockito.mock(OnlineRegularRepository.class);
	private static OnsiteExtraRepository iOnsiteExtraRepository = Mockito.mock(OnsiteExtraRepository.class);
	private static ClassContractRepository iClassContractRepository = Mockito.mock(ClassContractRepository.class);
	private static OnlineExtraRepository iOnlineExtraRepository = Mockito.mock(OnlineExtraRepository.class);
	
	private static RegularSchedulingService iRegularSchedulingService;
	
	@BeforeAll
	public static void setup() {
		iRegularSchedulingService = new RegularSchedulingService(iOnSiteRegularRepository, iOnlineRegularRepository, iOnsiteExtraRepository, iClassContractRepository, iOnlineExtraRepository);
	}

	@Test
	public void it_returns_regular_schedules_between_dates() {
		String start_date = "2022-11-23";
		String end_date = "2022-11-24";
		Date startDate = Date.valueOf(start_date);
		Date endDate = Date.valueOf(end_date);
		Long hallId = 1L;
		Mockito.when(iOnSiteRegularRepository.findByDayAndHallId("Wednesday", hallId)).thenReturn(createDummySchedule());
		Mockito.when(iClassContractRepository.findById(1L)).thenReturn(createDummyClassContract());
		Mockito.when(iOnsiteExtraRepository.findByHallIdAndDateBetween(hallId, startDate, endDate)).thenReturn(new ArrayList<OnsiteExtra>());
		List<TimeTableSlot> timeTableSlots = new ArrayList<>();
		TimeTableSlot timeTableSlot = new TimeTableSlot();
		timeTableSlot.setStart("2022-11-23 10:30");
		timeTableSlot.setEnd("2022-11-23 12:30");
		timeTableSlot.setTitle("Sujatha");
		timeTableSlots.add(timeTableSlot);
		
		List<TimeTableSlot> actual = iRegularSchedulingService.getSchedules(start_date, end_date, hallId);
		assertEquals(1, actual.size());
		
	}

	private Optional<ClassContract> createDummyClassContract() {
		ClassContract classContract = new ClassContract();
		Teacher teacher = new Teacher();
		teacher.setFirstName("Sujatha");
		teacher.setLastName("Perera");
		classContract.setTeacher(teacher);
		classContract.setId(1L);
		classContract.setCapacity(20);
		classContract.setMedium("English");
		classContract.setSubject("ICT");
		classContract.setGrade(10);
		// TODO Auto-generated method stub
		return Optional.ofNullable(classContract);
	}

	private List<OnSiteRegular> createDummySchedule() {
		
		List<OnSiteRegular> schedules = new ArrayList<>();
		OnSiteRegular schedule = new OnSiteRegular();
		schedule.setClassContract(1L);
		schedule.setDay("Wednesday");
		schedule.setHallId(1L);
		schedule.setTeacherId(1L);
		schedule.setTimeSlot("10:30-12:30");
		schedules.add(schedule);
		// TODO Auto-generated method stub
		return schedules;
	}
}
