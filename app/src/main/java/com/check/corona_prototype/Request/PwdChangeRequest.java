package com.check.corona_prototype.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PwdChangeRequest extends StringRequest {
    // 서버 URL
//        final static private String URL = "http://10.0.2.2/login.php";
    final static private String URL = "http://aa9334.cafe24.com/pwdchange.php";
    private Map<String, String> parameters;

    //생성자
    public PwdChangeRequest(String id, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("id", id);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
