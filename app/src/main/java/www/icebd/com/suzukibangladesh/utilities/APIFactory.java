package www.icebd.com.suzukibangladesh.utilities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class APIFactory {

	static ArrayList<NameValuePair> nameValuePairs;

	public ArrayList<NameValuePair> getBikeListInfo(String auth_key)
	{

		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> getQuizResultInfo(String auth_key,String user_id,String quiz_id, String quiz_answer)
	{

		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));
		nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
		nameValuePairs.add(new BasicNameValuePair("quiz_id", quiz_id));
		nameValuePairs.add(new BasicNameValuePair("quiz_answer", quiz_answer));


		return nameValuePairs;
	}

	public ArrayList<NameValuePair> Registration(String GCMkey,String DeviceID,String email) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("GCMkey", GCMkey));
		nameValuePairs.add(new BasicNameValuePair("DeviceID", DeviceID));
		nameValuePairs.add(new BasicNameValuePair("Email", email));

		return nameValuePairs;
	}

	public ArrayList<NameValuePair> Logout(String agent_id, String auth_key) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> setPin(String agent_id, String auth_key, String strSetPin) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("PIN", strSetPin));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> changePin(String agent_id, String auth_key, String strOldPin, String strNewPin) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("oldPIN", strOldPin));
		nameValuePairs.add(new BasicNameValuePair("newPIN", strNewPin));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> registered_user_verify(String agent_id, String auth_key) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));

		return nameValuePairs;
	}

	public ArrayList<NameValuePair> getPolicyHolderInfo(String agent_id, String auth_key, String strPolicyNo) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("PolicyNumber", strPolicyNo));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> submitPremiumJoma(String agent_id, String auth_key, String strPolicyNo,String premiumAmount,
													  String installment,String strRasidNo,String strGuponPinNo) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("PolicyNumber", strPolicyNo));
		nameValuePairs.add(new BasicNameValuePair("PremiumAmount", premiumAmount));
		nameValuePairs.add(new BasicNameValuePair("Installment", installment));
		nameValuePairs.add(new BasicNameValuePair("PRNumber", strRasidNo));
		nameValuePairs.add(new BasicNameValuePair("PIN", strGuponPinNo));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> submitFlexiLoadJoma(String agent_id, String auth_key, String mobile_no,
														String operatorID,String amount,String pin) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("MSISDN", mobile_no));
		nameValuePairs.add(new BasicNameValuePair("OperatorID", operatorID));
		nameValuePairs.add(new BasicNameValuePair("Denomination", amount));
		nameValuePairs.add(new BasicNameValuePair("PIN", pin));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> etStockBlance(String agent_id, String auth_key, String pin) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("AgentID", agent_id));
		nameValuePairs.add(new BasicNameValuePair("AuthKey", auth_key));
		nameValuePairs.add(new BasicNameValuePair("PIN", pin));

		return nameValuePairs;
	}

	public ArrayList<NameValuePair>insert_item(String user_id,String category_id, String description,String image,String price,String active_duration) {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("func_id", "6"));
		nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
		nameValuePairs.add(new BasicNameValuePair("category_id", category_id));
		nameValuePairs.add(new BasicNameValuePair("description", description));
		nameValuePairs.add(new BasicNameValuePair("image", image));
		nameValuePairs.add(new BasicNameValuePair("price", price));
		nameValuePairs.add(new BasicNameValuePair("active_duration", active_duration));

		return nameValuePairs;
	}

	public ArrayList<NameValuePair> get_all_item() {

		nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("func_id", "7"));

		return nameValuePairs;
	}







}//end of main class