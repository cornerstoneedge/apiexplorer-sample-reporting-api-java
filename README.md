Dev Guide
--------------------------------------------
ConsoleApp.java - This class requires following fields to call the Reporting API.

		API_BASE_ADDRESS :  User/developer should provide domain URL for example (https://[CORPNAME].csod.com) for Production, (https://[CORPNAME]-Pilot.csod.com) for Pilot & (https://[CORPNAME]-stg.csod.com) for Stage
		CLIENT_ID : This can be get/generated from login to Cornerstone. 
		CLIENT_SECRET : This can be get/generated from login to Cornerstone. 
		GRANT_TYPE : The default value should be "client_credentials".
		SCOPE : The default value should be "all".

After providing relevant values to above fields "AccessToken" is generated which is used to call the Reporting API.

It accepts action and calls the Reporting API accordingly using the "AccessToken" generated.Following are the actions that can be performed like (0,1,2,3,4,5):
		0. Exit
		1. Run all
		2. Get metadata
		3. Get only count from vw_rpt_user
		4. Get all data from vw_rpt_user
		5. Get data from vw_rpt_user by pages


EdgeApiClient.java - This is a helper class which sending HTTP requests and receiving HTTP responses from a resource identified by a URI.

HttpHeaders.java - This class contains the properties for Request Headers like Accept, Authorization & preference

Note: Do not change values for GRANT_TYPE & SCOPE.

