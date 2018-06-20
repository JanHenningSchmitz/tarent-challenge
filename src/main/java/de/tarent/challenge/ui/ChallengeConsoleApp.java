package de.tarent.challenge.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class ChallengeConsoleApp {

	public static final String URL = "http://localhost:8080/";
	public static final String PRODUCTS = "products";

	public static void readAll() {
		System.out.println("   ---Read All---");
		try {
			URL url = new URL(URL + PRODUCTS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				System.out.println("      Failed : HTTP error code : " + conn.getResponseCode());
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("      " + output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void readBySKU(String sku) {
		System.out.println("   ---Read by SKU---");

		HttpURLConnection conn = createConnection(sku, false, "GET", new String[] { "Accept", "application/json" });

		if (handleRespondeCode(conn, 200)) {

			printRespond(conn);
		}

		conn.disconnect();

	}

	public static void add(String sku, String name, double price, Set<String> eans) {
		System.out.println("   ---Add---");
		try {

			HttpURLConnection conn = createConnection(null, true, "POST",
					new String[] { "Content-Type", "application/json" });

			String input = createJSON(sku, name, price, eans);

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

	public static void put(String sku, String name, double price, Set<String> eans) {
		System.out.println("   ---Put---");
		try {

			HttpURLConnection conn = createConnection(sku, true, "POST",
					new String[] { "Content-Type", "application/json" });

			String input = createJSON(sku, name, price, eans);

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

	private static void printRespond(HttpURLConnection conn) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println("      " + output);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	private static HttpURLConnection createConnection(String sku, boolean doOutput, String requestMethod,
			String[] requestProperty) {

		URL url;
		try {
			url = new URL(URL + PRODUCTS + (sku != null ? "/" + sku : ""));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(doOutput);
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty(requestProperty[0], requestProperty[1]);
			return conn;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String createJSON(String sku, String name, double price, Set<String> eans) {
		JSONObject json = new JSONObject();
		try {
			json.put("sku", sku);
			json.put("name", name);
			json.put("price", price);
			json.put("eans", eans);

			System.out.println("      input: " + json.toString());
			return json.toString();
		} catch (JSONException jse) {
			jse.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		System.out.println("Read all the predefined values");
		// Read all the predefined values
		readAll();

		// Read all one specific predefined value
		System.out.println("ead all one specific predefined value");
		readBySKU("102");

		// Add a new product
		Set<String> eans = new HashSet<String>();
		eans.add("01010101");
		System.out.println("Add a new product");
		add("666", "Ipad", 6.66, eans);

		// read the new Product
		System.out.println("read the new Product");
		readBySKU("666");

		// try to add the same product again - should be an error
		System.out.println("try to add the same product again - should be an error");
		add("666", "Ipad", 6.66, eans);

		// try to add an null sku - should be an error
		System.out.println("try to add an null sku - should be an error");
		add(null, "Ipad", 6.66, eans);

		// try to add an empty sku - should be an error
		System.out.println("try to add an empty sku - should be an error");
		add("   ", "Ipad", 6.66, eans);

		// try to add an null name - should be an error
		System.out.println("try to add an null name - should be an error");
		add("777", null, 6.66, eans);

		// try to add an empty name - should be an error
		System.out.println("try to add an empty name - should be an error");
		add("777", "   ", 6.66, eans);

		// try to add an null ean set - should be an error
		System.out.println("try to add an null ean set - should be an error");
		add("777", "nullEans", 6.66, null);

		// try to add an empty ean set - should be an error
		System.out.println("try to add an empty ean set - should be an error");
		add("777", "nullEans", 6.66, new HashSet<String>());

	}

}
