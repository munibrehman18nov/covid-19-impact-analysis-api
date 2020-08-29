package io.munib.covid19impactanalysisapi.models;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class CovidDataRowHeader {

	private String province;
	private String country;
	private String latitude;
	private String longitude;
	public ArrayList<LocalDate> datesList;

	public CovidDataRowHeader() {
		datesList = new ArrayList<>();
	}

	public CovidDataRowHeader(String province, String country, String latitude, String longitude,
			ArrayList<LocalDate> datesList) {
		this.province = province;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datesList = datesList;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public ArrayList<LocalDate> getDatesList() {
		return datesList;
	}

	public void setData(ArrayList<LocalDate> datesList) {
		this.datesList = datesList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getProvince() + ", ");
		sb.append(getCountry() + ", ");
		sb.append(getLatitude() + ", ");
		sb.append(getLongitude());
		for (LocalDate d : datesList) {
			sb.append(", " + d);
		}
		return sb.toString();
	}

}
