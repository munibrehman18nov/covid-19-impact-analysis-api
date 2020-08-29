package io.munib.covid19impactanalysisapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.munib.covid19impactanalysisapi.dataprovider.DataProvider;
import io.munib.covid19impactanalysisapi.dataprovider.Domain;
import io.munib.covid19impactanalysisapi.models.CovidDataRow;
import io.munib.covid19impactanalysisapi.models.CovidDataRowHeader;

@RunWith(MockitoJUnitRunner.class)
public class ApiServiceImplTest {

	@InjectMocks
	ApiServiceImpl apiService;

	@Mock
	DataProvider dataProvider;

	private Domain getDomainObject() {
		CovidDataRowHeader header = new CovidDataRowHeader();
		header.setCountry("Country/Region");
		header.datesList.add(LocalDate.now().minusDays(1));
		header.datesList.add(LocalDate.now());

		ArrayList<CovidDataRow> data = new ArrayList<CovidDataRow>();
		CovidDataRow row = new CovidDataRow();
		row.setCountry("Pakistan");
		row.dataList.add(100);
		row.dataList.add(210);
		data.add(row);
		row = new CovidDataRow();
		row.setCountry("India");
		row.dataList.add(500);
		row.dataList.add(810);
		data.add(row);
		return new Domain(header, data);
	}

	@Before
	public void init() {
		when(dataProvider.getCovidData()).thenReturn(getDomainObject());
		apiService.getDataFromDataProvider();
	}

	@Test
	public void testGetDataFromDataProvider() {
		CovidDataRowHeader header = new CovidDataRowHeader();
		header.setCountry("Country/Region");
		header.datesList.add(LocalDate.now().minusDays(1));
		header.datesList.add(LocalDate.now());

		ArrayList<CovidDataRow> data = new ArrayList<CovidDataRow>();
		CovidDataRow row = new CovidDataRow();
		row.setCountry("Pakistan");
		row.dataList.add(100);
		row.dataList.add(210);
		data.add(row);
		row = new CovidDataRow();
		row.setCountry("India");
		row.dataList.add(500);
		row.dataList.add(810);
		data.add(row);

		Domain expectedData = new Domain(header, data);
		Domain actualData = dataProvider.getCovidData();

		assertEquals(actualData.getCovidDataHeader().getCountry(), expectedData.getCovidDataHeader().getCountry());
		assertEquals(actualData.getCovidDataHeader().datesList.get(0),
				expectedData.getCovidDataHeader().datesList.get(0));
		assertEquals(actualData.getCovidData().get(0).getCountry(), expectedData.getCovidData().get(0).getCountry());
		assertEquals(actualData.getCovidData().get(0).dataList.get(0),
				expectedData.getCovidData().get(0).dataList.get(0));

	}

	@Test
	public void testGetCasesReportedToday() {
		int cases = apiService.getCasesReportedToday();
		assertEquals(1020, cases);
	}

	@Test
	public void testGetCasesReportedTodayCountryWise() {
		Map<String, Integer> expectedData = new HashMap<>();
		expectedData.put("Pakistan", 110);
		expectedData.put("India", 310);
		Map<String, Integer> actualData = apiService.getCasesReportedTodayCountryWise();

		assertEquals(expectedData.get("Pakistan"), actualData.get("Pakistan"));
		assertEquals(expectedData.get("India"), actualData.get("India"));

	}

	@Test
	public void testGetCasesReportedTodayString() {
		int expectedData = 110;
		int actualData = apiService.getCasesReportedToday("Pakistan");
		assertEquals(expectedData, actualData);

	}

	@Test
	public void testGetCasesReportedSince() {
		String formattedDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("d-M-yy"));
		int expectedData = 110;
		int actualData = apiService.getCasesReportedSince("Pakistan", formattedDate);
		assertEquals(expectedData, actualData);

	}

	@Test
	public void testGetTopCountries() {
		Map<String, Integer> expectedData = new HashMap<>();
		expectedData.put("India", 310);
		expectedData.put("Pakistan", 110);
		Map<String, Integer> actualData = apiService.getCasesReportedTodayCountryWise();

		assertEquals(expectedData.get("Pakistan"), actualData.get("Pakistan"));
		assertEquals(expectedData.get("India"), actualData.get("India"));

	}

}
