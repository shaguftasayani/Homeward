import urllib3, requests, json

# retrieve your wml_service_credentials_username, wml_service_credentials_password, and wml_service_credentials_url from the
# Service credentials associated with your IBM Cloud Watson Machine Learning Service instanceIBM Cloud Watson Machine Learning Service instance 
wml_credentials={
	"url": "https://ibm-watson-ml.mybluemix.net",
	"username": "662f0c0b-432e-4340-ad38-a5cd2b443390",
	"password": "eada98f2-b0d2-443a-8dc6-7e770ea4f5e6"
}

refugee1 = [13, 30, "FEMALE", "YES", "Incomplete_High_School", 8, 5, 8, 8, 5, "3_Year_Degree", 6, 8, 7, 9, 2, 6, 1, 10, 7, 8, 7, "3_Year_Degree", "None" ,"NO", 0]
refugee2 = [2, 26, "FEMALE", "YES", "3_Year_Degree", 7, 6, 6, 8, 1, "3_Year_Degree", 7, 6, 9, 8, 5, 2, 0, 5, 8, 12, 5, "2_Year_Degree", "None", "NO", 0]
refugee3 = [40, 34, "FEMALE", "NO", "Incomplete_High_School", 9, 7, 7, 8, 0, 0, 0, 0, 0, 0, 0, 1, 1, 5, 11, 6, 1, "None", "None", "YES", 0]
refugee4 = [1130, 32, "FEMALE", "YES", "3_Year_Degree", 9, 8, 9, 8, 4, "High_School", 7, 7, 8, 7, 2, 2, 3, 9, 8, 9, 8, "None", "None", "NO", 0]
refugee5 = [4724, 26, "FEMALE", "NO", "Masters", 9, 9, 8, 9, 1, 0, 0, 0, 0, 0, 0, 2, 0, 9, 9, 8, 8, "1_Year_Degree", "None", "NO", 1]

headers = urllib3.util.make_headers(basic_auth='{username}:{password}'.format(username=wml_credentials['username'], password=wml_credentials['password']))
url = '{}/v3/identity/token'.format(wml_credentials['url'])
response = requests.get(url, headers=headers)
mltoken = json.loads(response.text).get('token')

header = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + mltoken}

# NOTE: manually define and pass the array(s) of values to be scored in the next line
payload_scoring = {"fields": ["refugee_ID", "Age", "Sex", "Spouse", "Level_Education", "CLB_Reading", "CLB_Writing", "CLB_Speaking", "CLB_Listening", "Canadian_Work_Experience", "Spouse_Level_Education", "CLB_Reading_Spouse", "CLB_Writing_Spouse", "CLB_Speaking_Spouse", "CLB_Listening_Spouse", "Spouse_Canadian_Work_Experience", "Foreign_Work_Experience", "Canadian_Siblings", "NCLC_Reading", "NCLC_Writing", "NCLC_Speaking", "NCLC_Listening", "Post_Secondary_Canada", "Arranged_Employment", "PN_Nomination", "Trade_Certification"], "values": [refugee1, refugee2, refugee3, refugee4, refugee5]}

response_scoring = requests.post('https://ibm-watson-ml.mybluemix.net/v3/wml_instances/c77e1a17-acc9-4957-8434-2771cf21c9ef/published_models/0c6b4798-e50d-4f93-8a08-03368c9c43ff/deployments/fe7272c5-4c7e-4f24-a0ab-4751f04a79b7/online', json=payload_scoring, headers=header)

print(response_scoring)