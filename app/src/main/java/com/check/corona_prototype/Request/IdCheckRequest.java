package com.check.corona_prototype.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IdCheckRequest extends StringRequest {

    // 서버 URL
//        final static private String URL = "http://10.0.2.2/login.php";
    final static private String URL = "http://aa9334.cafe24.com/idcheck.php";
    private Map<String, String> parameters;

    //생성자
    public IdCheckRequest(String id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("id", id);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
