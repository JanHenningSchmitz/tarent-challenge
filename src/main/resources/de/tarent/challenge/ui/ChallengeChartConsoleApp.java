package de.tarent.challenge.ui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Doesn't work anymore
 * @author Jan-Henning Schmitz
 *
 */
@Deprecated
public class ChallengeChartConsoleApp {

	public static final String URL = "http://localhost:8080/";
	public static final String CHARTS = "charts";

	public static void main(String[] args) {

		// Read all one specific predefined value
		System.out.println("add item to chart");
		additemtochart(1l, "102", 2);

	}
	
	public static void additemtochart(Long id, String sku, int quantity) {
		System.out.println("   ---Put---");
		try {

			URL url = new URL(URL + CHARTS + "/" + id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = createChartItemJSON(sku, quantity);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (handleRespondeCode(conn, 201)) {
				printRespond(conn);
			}
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	private static boolean handleRespondeCode(HttpURLConnection conn, int acceptedCode) {

		try {
			if (conn.getResponseCode() != acceptedCode) {
				System.out.println("      Failed : HTTP error code : " + conn.getResponseCode() + " - "
						+ conn.getResponseMessage());
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	private static void printRespond(HttpURLConnection conn) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("      " + output);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String createChartItemJSON(String sku, int quantity) {
		JSONObject json = new JSONObject();
		try {
			json.put("sku", sku);
			json.put("quantity", quantity);

			System.out.println("      input: " + json.toString());
			return json.toString();
		} catch (JSONException jse) {
			jse.printStackTrace();
			return null;
		}
	}

}
