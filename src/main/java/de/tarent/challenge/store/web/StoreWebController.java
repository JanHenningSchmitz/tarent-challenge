package de.tarent.challenge.store.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tarent.challenge.store.chart.Chart;

@Controller
public class StoreWebController {

	@GetMapping()
	public String showAllCheckedOutCharts(Model model) throws Exception {

		List<Chart> charts = readAllCharts();

		List<ChartForThymeleaf> chartsForThymeleaf = new ArrayList<ChartForThymeleaf>();
		for (Chart chart : charts) {
			chartsForThymeleaf.add(new ChartForThymeleaf(chart));
		}

		model.addAttribute("chartsize", charts.size());
		if (charts.size() > 0)
			model.addAttribute("chartsForThymeleaf", chartsForThymeleaf);

		return "allproducts";
	}

	private static String URL = "http://localhost:8080/";
	private static String CHARTS = "/charts";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Chart> getChartFromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		ArrayList value = mapper.readValue(json, ArrayList.class);
		List<Chart> result = new ArrayList<Chart>();

		for (Object obj : value) {

			LinkedHashMap linkedHashMap = (LinkedHashMap) obj;

			String name = (String) linkedHashMap.get("name");
			double totalprice = (double) linkedHashMap.get("totalprice");
			boolean checkedout = (boolean) linkedHashMap.get("checkedout");
			long checkoutdate = (long) linkedHashMap.get("checkoutdate");
			Set<String> items = new HashSet<>();
			for (String s : (ArrayList<String>) linkedHashMap.get("items")) {
				items.add(s);
			}

			Chart chart = new Chart(name, items, totalprice, checkedout, checkoutdate);
			result.add(chart);
		}

		return result;
	}

	private List<Chart> readAllCharts() throws Exception {

		List<Chart> result = new ArrayList<Chart>();

		URL url = new URL(URL + CHARTS);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			result = getChartFromJson(output);
		}

		conn.disconnect();

		return result;
	}
}
