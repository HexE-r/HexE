import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse
{
	public String parseData(String JSON_DAT) throws JSONException {
		final String JSON_DATA = JSON_DAT;
		final JSONObject dat = new JSONObject(JSON_DATA);
		//final int n = dat.length();
		String res = dat.getString("IpfsHash");
		//System.out.println(val.getString("IpfsHash"));
		return res;
	}
}
