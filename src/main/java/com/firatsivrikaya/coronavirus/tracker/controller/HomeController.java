package com.firatsivrikaya.coronavirus.tracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.firatsivrikaya.coronavirus.tracker.model.LocationStatistic;
import com.firatsivrikaya.coronavirus.tracker.service.CoronavirusDataService;

@Controller
public class HomeController {

	@Autowired
	private CoronavirusDataService coronavirusDataService;

	@GetMapping("/")
	public String home(Model model) throws IOException, InterruptedException {
		List<LocationStatistic> locationStatistics = coronavirusDataService.getLocationStatistics();
		int totalReportedCases = locationStatistics.stream().mapToInt(LocationStatistic::getLatestTotalCases).sum();
		int totalNewCases = locationStatistics.stream().mapToInt(LocationStatistic::getDifferenceFromPreviousDay).sum();
		model.addAttribute("locationStatistics", locationStatistics);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		return "home";
	}

}