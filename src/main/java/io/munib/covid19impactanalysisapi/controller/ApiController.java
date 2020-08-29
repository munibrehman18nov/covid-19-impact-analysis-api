package io.munib.covid19impactanalysisapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.munib.covid19impactanalysisapi.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiController {

//	@Autowired
	private ApiService apiService;

	public ApiController(ApiService apiService) {
		this.apiService = apiService;
	}

	@RequestMapping("/totalcasestoday")
	public ResponseEntity<?> getCasesReportedToday() {
		Map<String, Integer> response = new HashMap<>();
		response.put("cases", apiService.getCasesReportedToday());
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/totalcasestoday-countrywise")
	public ResponseEntity<?> getCasesReportedTodayCountryWise() {
		Map<String, Map<String, Integer>> response = new HashMap<>();
		response.put("result", apiService.getCasesReportedTodayCountryWise());
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/totalcasestoday/{country}")
	public ResponseEntity<?> getCasesReportedToday(@PathVariable String country) {
		Map<String, Integer> response = new HashMap<>();
		response.put("cases", apiService.getCasesReportedToday(country));
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/totalcasessince/{country}/{since}")
	public ResponseEntity<?> getCasesReportedSince(@PathVariable String country, @PathVariable String since) {
		Map<String, Integer> response = new HashMap<>();
		response.put("cases", apiService.getCasesReportedSince(country, since));
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/top-n-countries/{topNCountries}")
	public ResponseEntity<?> getTopCountries(@PathVariable int topNCountries) {
		Map<String, Map<String, Integer>> response = new HashMap<>();
		response.put("cases", apiService.getTopCountries(topNCountries));
		return ResponseEntity.ok(response);
	}

	@RequestMapping("/active-users")
	public ResponseEntity<?> getActiveUsers() {
		return ResponseEntity.ok(apiService.getActiveUsers());
	}

}
