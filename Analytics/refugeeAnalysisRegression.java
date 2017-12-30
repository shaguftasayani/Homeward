import java.io.*;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class refugeeAnalysis {
	public static void main(String[] args) throws IOException {

		// NOTE: you must manually construct wml_credentials hash map below
		// using information retrieved from your IBM Cloud Watson Machine Learning Service instance

		Object[] refugee1 = {13, 30, "FEMALE", "YES", "Incomplete_High_School", 8, 5, 8, 8, 5, "3_Year_Degree", 6, 8, 7, 9, 2, 6, 1, 10, 7, 8, 7, "3_Year_Degree", "None" ,"NO", 0};
		Object[] refugee2 = {2, 26, "FEMALE", "YES", "3_Year_Degree", 7, 6, 6, 8, 1, "3_Year_Degree", 7, 6, 9, 8, 5, 2, 0, 5, 8, 12, 5, "2_Year_Degree", "None", "NO", 0};
		Object[] refugee3 = {40, 34, "FEMALE", "NO", "Incomplete_High_School", 9, 7, 7, 8, 0, 0, 0, 0, 0, 0, 0, 1, 1, 5, 11, 6, 1, "None", "None", "YES", 0};
		Object[] refugee4 = {1130, 32, "FEMALE", "YES", "3_Year_Degree", 9, 8, 9, 8, 4, "High_School", 7, 7, 8, 7, 2, 2, 3, 9, 8, 9, 8, "None", "None", "NO", 0};
		Object[] refugee5 = {4724, 26, "FEMALE", "NO", "Masters", 9, 9, 8, 9, 1, 0, 0, 0, 0, 0, 0, 2, 0, 9, 9, 8, 8, "1_Year_Degree", "None", "NO", 1};

		Map<String, String> wml_credentials = new HashMap<String, String>()
		{{
			put("url", "https://ibm-watson-ml.mybluemix.net");
			put("username", "662f0c0b-432e-4340-ad38-a5cd2b443390");
			put("password", "eada98f2-b0d2-443a-8dc6-7e770ea4f5e6");
		}};
		String wml_auth_header = "Basic " +
				Base64.getEncoder().encodeToString((wml_credentials.get("username") + ":" +
					wml_credentials.get("password")).getBytes(StandardCharsets.UTF_8));
		String wml_url = wml_credentials.get("url") + "/v3/identity/token";
		HttpURLConnection tokenConnection = null;
		HttpURLConnection scoringConnection = null;
		BufferedReader tokenBuffer = null;
		BufferedReader scoringBuffer = null;
		try {
			// Getting WML token
			URL tokenUrl = new URL(wml_url);
			tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
			tokenConnection.setDoInput(true);
			tokenConnection.setDoOutput(true);
			tokenConnection.setRequestMethod("GET");
			tokenConnection.setRequestProperty("Authorization", wml_auth_header);
			tokenBuffer = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = tokenBuffer.readLine()) != null) {
				jsonString.append(line);
			}
			// Scoring request
			String myURL = "https://ibm-watson-ml.mybluemix.net/v3/wml_instances/c77e1a17-acc9-4957-8434-2771cf21c9ef/published_models/91ecd869-7460-4638-9080-00d879b38dc9/deployments/2aa38558-ea17-4251-b4d1-cfde8317139a/online";
			URL scoringUrl = new URL(myURL);
			String wml_token = "Bearer " +
					jsonString.toString()
							.replace("\"","")
							.replace("}", "")
							.split(":")[1];
			scoringConnection = (HttpURLConnection) scoringUrl.openConnection();
			scoringConnection.setDoInput(true);
			scoringConnection.setDoOutput(true);
			scoringConnection.setRequestMethod("POST");
			scoringConnection.setRequestProperty("Accept", "application/json");
			scoringConnection.setRequestProperty("Authorization", wml_token);
			scoringConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(scoringConnection.getOutputStream(), "UTF-8");

			// NOTE: manually define and pass the array(s) of values to be scored in the next line

			String payload = "{\"fields\": [\"refugee_ID\", \"Age\", \"Sex\", \"Spouse\", \"Level_Education\", \"CLB_Reading\", \"CLB_Writing\", \"CLB_Speaking\", \"CLB_Listening\", \"Canadian_Work_Experience\", \"Spouse_Level_Education\", \"CLB_Reading_Spouse\", \"CLB_Writing_Spouse\", \"CLB_Speaking_Spouse\", \"CLB_Listening_Spouse\", \"Spouse_Canadian_Work_Experience\", \"Foreign_Work_Experience\", \"Canadian_Siblings\", \"NCLC_Reading\", \"NCLC_Writing\", \"NCLC_Speaking\", \"NCLC_Listening\", \"Post_Secondary_Canada\", \"Arranged_Employment\", \"PN_Nomination\", \"Trade_Certification\"], \"values\":[" + refugee1 + "," + refugee2 + "," + refugee3 + "," + refugee4 + "," + refugee5 + "]}";
			writer.write(payload);
			writer.close();

			scoringBuffer = new BufferedReader(new InputStreamReader(scoringConnection.getInputStream()));
			StringBuffer jsonStringScoring = new StringBuffer();
			String lineScoring;
			while ((lineScoring = scoringBuffer.readLine()) != null) {
				jsonStringScoring.append(lineScoring);
			}
			System.out.println(jsonStringScoring);
		} catch (IOException e) {
			System.out.println("The URL is not valid.");
			System.out.println(e.getMessage());
		}
		finally {
			if (tokenConnection != null) {
				tokenConnection.disconnect();
			}
			if (tokenBuffer != null) {
				tokenBuffer.close();
			}
			if (scoringConnection != null) {
				scoringConnection.disconnect();
			}
			if (scoringBuffer != null) {
				scoringBuffer.close();
			}
		}
	}


}