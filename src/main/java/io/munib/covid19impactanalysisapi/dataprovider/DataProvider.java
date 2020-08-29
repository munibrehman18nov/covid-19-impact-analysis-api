package io.munib.covid19impactanalysisapi.dataprovider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import io.munib.covid19impactanalysisapi.models.CovidDataRow;
import io.munib.covid19impactanalysisapi.models.CovidDataRowHeader;

@Component
public class DataProvider {
	private URL SRC_FILE_URL;

	@Value("${SRC_FILE_NAME}")
	private String SRC_FILE_NAME;

	public Domain getCovidData() {
		CovidDataRowHeader header = new CovidDataRowHeader();
		ArrayList<CovidDataRow> tempCovidData = new ArrayList<>();
		try {
			SRC_FILE_URL = new URL(SRC_FILE_NAME);
			InputStreamReader inputStreamReader = new InputStreamReader(SRC_FILE_URL.openStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			CSVReader csvReader = new CSVReader(bufferedReader);
			List<String[]> data = csvReader.readAll();
			inputStreamReader.close();
			bufferedReader.close();
			csvReader.close();

			header.setProvince(data.get(0)[0]);
			header.setCountry(data.get(0)[1]);
			header.setLatitude(data.get(0)[2]);
			header.setLongitude(data.get(0)[3]);
			for (int i = 4; i < data.get(0).length; i++) {
				LocalDate d = LocalDate.parse(data.get(0)[i], DateTimeFormatter.ofPattern("M/d/yy"));
				header.datesList.add(d);
			}
			for (int i = 1; i < data.size(); i++) {
				CovidDataRow row = new CovidDataRow();
				row.setProvince(data.get(i)[0]);
				row.setCountry(data.get(i)[1]);
				row.setLatitude(Double.parseDouble(data.get(i)[2]));
				row.setLongitude(Double.parseDouble(data.get(i)[3]));
				for (int j = 4; j < data.get(i).length; j++) {
					row.dataList.add(Integer.parseInt(data.get(i)[j]));
				}
				tempCovidData.add(row);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Domain(header, tempCovidData);
	}

}
