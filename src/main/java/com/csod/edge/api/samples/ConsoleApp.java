package com.csod.edge.api.samples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.csod.edge.api.samples.dto.EdgeApiODataPayload;

public class ConsoleApp {
	private static final int PRINT_LENGTH = 500;

	private static final String API_BASE_ADDRESS = "http://DOMAIN.csod.com:81";

	private static final String CLIENT_ID = "";
	
	private static final String CLIENT_SECRET = "";
	
	private static final String API_VIEWS_PATH = "/services/api/x/odata/api/views";
	
	private static EdgeApiClient client;

	public static void main(String[] args) throws Exception {
		init();

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println();
				System.out.println("Edge OData API samples");
				System.out.println();
				System.out.println("Select an action:");
				System.out.println();
				System.out.println("0. Exit");
				System.out.println("1. Run all");
				System.out.println("2. Get metadata");
				System.out.println("3. Get only count from vw_rpt_user");
				System.out.println("4. Get all data from vw_rpt_user");
				System.out.println("5. Get data from vw_rpt_user by pages");
				System.out.println();
				System.out.print("Input number and press Enter: ");

				int number;
				String input = scanner.nextLine();
				number = Integer.parseInt(input);

				switch (number) {
				case 0:
					return;
				case 1:
					executeMetadata();
					executeCount();
					executeAllData();
					executePaging();
					break;
				case 2:
					executeMetadata();
					break;
				case 3:
					executeCount();
					break;
				case 4:
					executeAllData();
					break;
				case 5:
					executePaging();
					break;
				default:
					System.out.println("Wrong input");
					break;
				}
			}
		}
	}

	private static void init() throws MalformedURLException {
		client = new EdgeApiClient(new URL(API_BASE_ADDRESS), CLIENT_ID, CLIENT_SECRET);
	}

	private static void executeMetadata() throws Exception {
		System.out.println("Getting metadata...");
		String stringContent = client.getString(API_VIEWS_PATH + "/$metadata", 0);
		System.out.println(String.format("Response length is %s. First %s characters: %s", stringContent.length(),
				PRINT_LENGTH, stringContent.substring(0, PRINT_LENGTH - 1)));
	}

	private static void handleError(EdgeApiODataPayload payload) {
		System.out.println(payload.errorValue != null ? String.format("Error occurred. Code: %s, message: %s",
				payload.errorValue.error.code, payload.errorValue.error.message) : "Data is retrieved without errors.");
	}

	private static void executeCount() throws Exception {
		System.out.println("Getting only count from vw_rpt_user...");
		EdgeApiODataPayload payload = client.getODataPayload(API_VIEWS_PATH + "/vw_rpt_user?$count=true&$top=0", 0);
		handleError(payload);
		System.out.println(String.format("Got count %s", payload.count));
	}

	private static void executeAllData() throws Exception {
		System.out.println("Getting all data from vw_rpt_user...");
		EdgeApiODataPayload payload = client.getODataPayload(API_VIEWS_PATH + "/vw_rpt_user?$count=true&$top=0", 0);
		payload = client.getODataPayload(API_VIEWS_PATH + "/vw_rpt_user", payload.count);
		handleError(payload);
		System.out.println(String.format("Got %s values", payload.value.length));
	}

	private static void executePaging() throws Exception {
		System.out.println("Getting data from vw_rpt_user by pages...");
		EdgeApiODataPayload payload = client.getODataPayload(API_VIEWS_PATH + "/vw_rpt_user", 10);
		handleError(payload);
		System.out.println(String.format("Got %s values", payload.value.length));
		System.out.println(String.format("Next link is '%s'", payload.nextLink));

		String address = payload.nextLink.substring(API_BASE_ADDRESS.length());
		System.out.println("Getting data by next link...");
		payload = client.getODataPayload(address, 10);
		handleError(payload);
		System.out.println(String.format("Got %s values", payload.value.length));
		System.out.println(String.format("Next link is '%s'", payload.nextLink));
		System.out.println("...");
	}
}
