package com.mockproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mockproject.service.TrainingClassService;

@SpringBootTest
public class TrainingClassTest {
	long tmp = 1;
	
	@Autowired
    private TrainingClassService classService;
	
//	@Test
//	public void findClassDetail() {
//		System.out.printf("Detail: "+ classService.findClassDetail(tmp));
//	}

	@Test
	public void findProgramDetail() {
		System.out.println("Detail: "+ classService.findClassDetail(tmp));
		System.out.println("Program Detail: "+ classService.findProgramDetail(tmp));
		System.out.println("Attendee Detail: "+ classService.findAttendee(tmp));
		System.out.println("User Detail: "+ classService.findUser(tmp));
		System.out.println("Class Schedule Detail: "+ classService.findClassShedule(tmp));
	}
}
