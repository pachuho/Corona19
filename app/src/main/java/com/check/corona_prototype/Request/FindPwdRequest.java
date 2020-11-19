package com.check.corona_prototype.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindPwdRequest extends StringRequest {

    // 서버 URL
    final static private String URL = "http://52.79.235.161/findpwd.php";
    private Map<String, String> parameters;

    //생성자
    public FindPwdRequest(String id, String name, int age, String sex, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("name", name);
        parameters.put("age", age +"");
        parameters.put("sex", sex);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
