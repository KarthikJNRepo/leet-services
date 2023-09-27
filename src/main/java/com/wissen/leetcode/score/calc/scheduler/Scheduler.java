package com.wissen.leetcode.score.calc.scheduler;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wissen.leetcode.score.calc.service.ServiceClass;

@Component
public class Scheduler {

	@Autowired
	private ServiceClass missingService;
	private boolean isTaskRunning = false;

//    ("0 0 0 0 SUN")
	@Scheduled(cron = "* 0 11 * * *")
	public void scheduler() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);
		if (!isTaskRunning) {
			try {
				missingService.addMissingToTable(week, year);
				missingService.updateUserMissedCount(week, year);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				isTaskRunning = true;
			}
		}
	}

	@Scheduled(cron = "59 59 10 * * *")
	public void flagScheduler() {
		isTaskRunning = false;
	}
}