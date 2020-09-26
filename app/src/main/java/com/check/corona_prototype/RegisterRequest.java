package com.check.corona_prototype;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL
    // 10.0.2.2
    final static private String URL = "http://namweb.iptime.org/register.php";
    private Map<String, String> parameters;

    //생성자
    public RegisterRequest(String name, String id, String pwd,
                           String address, int age, String sex,
                           Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("id", id);
        parameters.put("pwd", pwd);
        parameters.put("address", address);
        parameters.put("age", age + "");
        parameters.put("sex", sex);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
