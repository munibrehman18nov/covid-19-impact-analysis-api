package io.munib.covid19impactanalysisapi.service;

import java.util.List;
import java.util.Map;

import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;

public interface ApiService {

	public int getCasesReportedToday();

	public Map<String, Integer> getCasesReportedTodayCountryWise();

	public int getCasesReportedToday(String country);

	public int getCasesReportedSince(String country, String date);

	public Map<String, Integer> getTopCountries(int topNCountries);

	public Map<String, List<MyUserDetails>> getActiveUsers();

}
