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

public class ChallengeConsoleApp {

	public static final String URL = "http://localhost:8080/";
	public static final String PRODUCTS = "products";
	
	public static void readAll() {
		System.out.println("---Read All---");
		try {
			URL url = new URL(URL + PRODUCTS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static void readBySKU(String sku) {
		System.out.println("---Read by SKU---");
		try {
			URL url = new URL(URL + PRODUCTS + "/" + sku);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	
	public static void add(String sku, String name, Set<String> eans) {
		System.out.println("---Add---");
		try {
			URL url = new URL(URL + PRODUCTS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
				
			String eansString = "[";
			for(String ean : eans) {
				eansString += "\""+ean+"\"";
			}
			eansString += "]";
			
			String input = "{\"sku\":\""+sku+"\",\"name\":\""+name+"\",\"eans\":"+eansString+"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			System.out.println("input: " + input);
			System.out.println("Return: " + conn.getResponseCode());
			
			if (conn.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static void put(String sku, String name, Set<String> eans) {
		System.out.println("---Put---");
		try {
			URL url = new URL(URL + PRODUCTS + "/" + sku);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
				
			String eansString = "[";
			for(String ean : eans) {
				eansString += "\""+ean+"\"";
			}
			eansString += "]";
			
			String input = "{\"sku\":\""+sku+"\",\"name\":\""+name+"\",\"eans\":"+eansString+"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			System.out.println("input: " + input);
			
			if (conn.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static void main(String[] args) {

		readAll();
		readBySKU("102");
		Set<String> eans = new HashSet<String>();
		eans.add("01010101");
		add("666", "Ipad", eans);
		readBySKU("666");
		
//		put("666", "Ipad2", eans);
//		readBySKU("666");

	}
	
}
