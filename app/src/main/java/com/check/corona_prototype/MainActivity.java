package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.check.corona_prototype.Fragment.AuthenticationFragment;
import com.check.corona_prototype.Fragment.CoronaMapFragment;
import com.check.corona_prototype.Fragment.WebViewFragment;
import com.check.corona_prototype.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// 로그인 완료, 메인
public class MainActivity extends AppCompatActivity {
    String id, name, pwd;


    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private AuthenticationFragment authentication = new AuthenticationFragment();
    private CoronaMapFragment location = new CoronaMapFragment();
    private WebViewFragment webView = new WebViewFragment();
    private SettingFragment setting = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 -> 메인 값 받기(아이디, 이름)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        id = bundle.getString("id");
        pwd = bundle.getString("pwd");
        getIntent().getExtras().clear();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_menu1:
                        setFragment(0);
                        break;
                    case R.id.navigation_menu2:
                        setFragment(1);
                        break;
                    case R.id.navigation_menu3:
                        setFragment(2);
                        break;
                    case R.id.navigation_menu4:
                        setFragment(3);
                        break;
                }
                return true;
            }
        });
        setFragment(0); // 초기 프래그먼트 지정
    }

    // 프레그먼트 교체
    private void setFragment(int select)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (select)
        {
            case 0:
                ft.replace(R.id.container, authentication);
                ft.commit();

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("id", id);
                authentication.setArguments(bundle);
                break;

            case 1:
                ft.replace(R.id.container, location);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.container, webView);
                ft.commit();
                break;

            case 3:
                ft.replace(R.id.container, setting);
                ft.commit();

                Bundle bundle2 = new Bundle();
                bundle2.putString("id", id);
                bundle2.putString("pwd", pwd);
                setting.setArguments(bundle2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}