package com.check.corona_prototype.Request;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ScanRequest extends StringRequest{

    // 서버 URL
//    final static private String URL = "http://10.0.2.2/scan.php";
    final static private String URL = "http://aa9334.cafe24.com/scan.php";
    private Map<String, String> parameters;

    //생성자
    public ScanRequest(String id, String time, Double la, Double lo, String store,
                           Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("time", time);
        parameters.put("la", la + "");
        parameters.put("lo", lo + "");
        parameters.put("store", store);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
