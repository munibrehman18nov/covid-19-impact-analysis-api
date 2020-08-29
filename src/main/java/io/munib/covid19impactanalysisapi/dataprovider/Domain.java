package io.munib.covid19impactanalysisapi.dataprovider;

import java.util.ArrayList;

import io.munib.covid19impactanalysisapi.models.CovidDataRow;
import io.munib.covid19impactanalysisapi.models.CovidDataRowHeader;

public class Domain {
	
	private CovidDataRowHeader covidDataHeader;
	private ArrayList<CovidDataRow> covidData;
	public CovidDataRowHeader getCovidDataHeader() {
		return covidDataHeader;
	}
	public void setCovidDataHeader(CovidDataRowHeader covidDataHeader) {
		this.covidDataHeader = covidDataHeader;
	}
	public ArrayList<CovidDataRow> getCovidData() {
		return covidData;
	}
	public void setCovidData(ArrayList<CovidDataRow> covidData) {
		this.covidData = covidData;
	}
	public Domain(CovidDataRowHeader covidDataHeader, ArrayList<CovidDataRow> covidData) {
		this.covidDataHeader = covidDataHeader;
		this.covidData = covidData;
	}
	public Domain() {
		this.covidDataHeader = new CovidDataRowHeader();
		this.covidData = new ArrayList<CovidDataRow>();
	}
	
	

}
