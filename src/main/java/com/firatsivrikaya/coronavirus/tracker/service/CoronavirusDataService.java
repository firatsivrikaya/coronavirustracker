package com.firatsivrikaya.coronavirus.tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.firatsivrikaya.coronavirus.tracker.model.LocationStatistic;

@Service
public class CoronavirusDataService {
	private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	// this is stateful and should never be in the production code :)
	private List<LocationStatistic> locationStatistics = new ArrayList<>();

	public List<LocationStatistic> getLocationStatistics() {
		return locationStatistics;
	}

	@PostConstruct
	@Scheduled(cron = "0 0 * ? * *") // every hour
	public void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStatistic> newStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		String responseBody = httpResponse.body();

		StringReader csvBodyReader = new StringReader(responseBody);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

		for (CSVRecord record : records) {
			String state = record.get("Province/State");
			String country = record.get("Country/Region");
			int totalCaseIndex = record.size() - 1;
			int previousTotalCaseIndex = record.size() - 2;
			int latestTotalCases = Integer.parseInt(record.get(totalCaseIndex));
			int previousTotalCases = Integer.parseInt(record.get(previousTotalCaseIndex));

			LocationStatistic locationStatistic = new LocationStatistic();
			locationStatistic.setState(state);
			locationStatistic.setCountry(country);
			locationStatistic.setLatestTotalCases(latestTotalCases);
			int differenceFromPreviousDay = latestTotalCases - previousTotalCases;
			locationStatistic.setDifferenceFromPreviousDay(differenceFromPreviousDay);

			newStats.add(locationStatistic);
		}

		this.locationStatistics = newStats;

	}

}
