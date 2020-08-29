package io.munib.covid19impactanalysisapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.munib.covid19impactanalysisapi.dataprovider.DataProvider;
import io.munib.covid19impactanalysisapi.dataprovider.Domain;
import io.munib.covid19impactanalysisapi.models.CovidDataRow;
import io.munib.covid19impactanalysisapi.models.CovidDataRowHeader;
import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;
import io.munib.covid19impactanalysisapi.security.repository.MyUserDetailsRepository;

@Service
public class ApiServiceImpl implements ApiService {

	private CovidDataRowHeader covidDataHeader;
	private ArrayList<CovidDataRow> covidData;

//	@Autowired
	private DataProvider dataProvider;

//	@Autowired
	private MyUserDetailsRepository myUserDetailsRepository;

	public ApiServiceImpl(DataProvider dataProvider, MyUserDetailsRepository myUserDetailsRepository) {
		this.dataProvider = dataProvider;
		this.myUserDetailsRepository = myUserDetailsRepository;
	}

	@PostConstruct
	@Scheduled(fixedRate = 60 * 60 * 24 * 1000)
	public void getDataFromDataProvider() {
		/*
		 * This method gets data from Source URL. This method is @PostConstruct
		 * and @Scheduled method. This method is called each 24th hour after the
		 * construction of the ApiService object
		 */
		Domain domainData = dataProvider.getCovidData();
		covidDataHeader = domainData.getCovidDataHeader();
		covidData = domainData.getCovidData();
	}

	@Override
	public int getCasesReportedToday() {
		/*
		 * This method returns total cases reported today around the world
		 */
		return covidData.stream()
				.mapToInt(row -> row.dataList.get(row.dataList.size() - 1) - row.dataList.get(row.dataList.size() - 2))
				.sum();
	}

	@Override
	public Map<String, Integer> getCasesReportedTodayCountryWise() {
		/*
		 * This method returns cases reported today in each country
		 */
		Map<String, Integer> result = new HashMap<>();
		for (CovidDataRow row : covidData) {
			if (result.get(row.getCountry()) == null) {
				result.put(row.getCountry(),
						row.dataList.get(row.dataList.size() - 1) - row.dataList.get(row.dataList.size() - 2));
			} else {
				result.put(row.getCountry(), result.get(row.getCountry())
						+ (row.dataList.get(row.dataList.size() - 1) - row.dataList.get(row.dataList.size() - 2)));
			}
		}
		return sortByCases(result);
	}

	@Override
	public int getCasesReportedToday(String country) {
		/*
		 * This method returns cases reported today in a specific country
		 */
		int sum = 0;
		for (CovidDataRow row : covidData) {
			if (row.getCountry().equalsIgnoreCase(country)) {
				sum += row.dataList.get(row.dataList.size() - 1) - row.dataList.get(row.dataList.size() - 2);
			}
		}
		return sum;
	}

	@Override
	public int getCasesReportedSince(String country, String date) {
		/*
		 * This method returns cases reported till today since a specific date. Date
		 * should be in format d-M-yy i.e. 7-8-20
		 */
		int result = 0;
		LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("d-M-yy"));
		int index = covidDataHeader.datesList.indexOf(d);
		if (index >= 0) {
			for (CovidDataRow row : covidData) {
				if (row.getCountry().equalsIgnoreCase(country)) {
					result += row.dataList.get(row.dataList.size() - 1) - row.dataList.get(index);
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Integer> getTopCountries(int topNCountries) {
		/*
		 * This method returns most affected top-n countries
		 */
		Map<String, Integer> result = new HashMap<>();
		for (CovidDataRow row : covidData) {
			if (result.get(row.getCountry()) == null) {
				result.put(row.getCountry(), row.dataList.get(row.dataList.size() - 1));
			} else {
				result.put(row.getCountry(), result.get(row.getCountry()) + row.dataList.get(row.dataList.size() - 1));
			}
		}
		return sortByCases(sortByCases(result).entrySet().stream().limit(topNCountries)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}

	private Map<String, Integer> sortByCases(Map<String, Integer> map) {
		/*
		 * This method sorts the map by cases reported in descending order
		 */
		List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	@Override
	public Map<String, List<MyUserDetails>> getActiveUsers() {
		Map<String, List<MyUserDetails>> result = new HashMap<>();
		List<MyUserDetails> list = (List<MyUserDetails>) myUserDetailsRepository.findAll();
		result.put("active-users", list);
		return result;
	}

}
