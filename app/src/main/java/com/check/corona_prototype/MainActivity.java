package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.check.corona_prototype.Fragment.Authentication;
import com.check.corona_prototype.Fragment.Fragment2;
import com.check.corona_prototype.Fragment.WebView;
import com.check.corona_prototype.Fragment.Fragment4;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// 로그인 완료, 메인
public class MainActivity extends AppCompatActivity {
    String id, name;


    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Authentication fragment1 = new Authentication();
    private Fragment2 fragment2 = new Fragment2();
    private WebView webView = new WebView();
    private Fragment4 fragment4 = new Fragment4();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 -> 메인 값 받기(아이디, 이름)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        id = bundle.getString("id");
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
                        setFrag(0);
                        break;
                    case R.id.navigation_menu2:
                        setFrag(1);
                        break;
                    case R.id.navigation_menu3:
                        setFrag(2);
                        break;
                    case R.id.navigation_menu4:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        setFrag(0); // 초기 프래그먼트 지정
    }

    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.container, fragment1);
                ft.commit();

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("id", id);
                fragment1.setArguments(bundle);
                break;

            case 1:
                ft.replace(R.id.container, fragment2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.container, webView);
                ft.commit();
                break;

            case 3:
                ft.replace(R.id.container, fragment4);
                ft.commit();
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