package io.munib.covid19impactanalysisapi.models;

import java.util.ArrayList;

public class CovidDataRow {

	private String province;
	private String country;
	private double latitude;
	private double longitude;
	public ArrayList<Integer> dataList;

	public CovidDataRow() {
		dataList = new ArrayList<>();
	}

	public CovidDataRow(String province, String country, double latitude, double longitude,
			ArrayList<Integer> dataList) {
		this.province = province;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.dataList = dataList;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public ArrayList<Integer> getData() {
		return dataList;
	}

	public void setData(ArrayList<Integer> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getProvince() + ", ");
		sb.append(getCountry() + ", ");
		sb.append(getLatitude() + ", ");
		sb.append(getLongitude());
		for (int n : dataList) {
			sb.append(", " + n);
		}
		return sb.toString();
	}

}
