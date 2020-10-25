package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.check.corona_prototype.FaceDetection.FaceActivity;
import com.check.corona_prototype.QR.CreateQR;
import com.check.corona_prototype.QR.ScanQR;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.check.corona_prototype.R.drawable.check_ok;

// 로그인 완료, 메인
public class MainActivity extends AppCompatActivity {
    String id, name, store;
    Button btn_picture, btn_qrcode;
    int check_camera = 0, check_qr = 0;
    ImageView imageView1 = null, imageView2 = null;

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment1 fragment1 = new Fragment1();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    private Fragment4 fragment4 = new Fragment4();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btn_picture = findViewById(R.id.btn_picture);
//        btn_qrcode = findViewById(R.id.btn_qrcode);
//        imageView1 = findViewById(R.id.iv_check1);
//        imageView2 = findViewById(R.id.iv_check2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        id = bundle.getString("id");
        getIntent().getExtras().clear();



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



//        // 사진버튼
//        btn_picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, FaceActivity.class);
//                intent.putExtra("id", id);
//                startActivityForResult(intent, 1);
//            }
//        });
//
//        // QR코드 버튼
//        btn_qrcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScanQR.class);
//                intent.putExtra("id", id);
//                startActivityForResult(intent, 2);
//            }
//        });
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
                fragment1.setArguments(bundle);
                break;

            case 1:
                ft.replace(R.id.container, fragment2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.container, fragment3);
                ft.commit();
                break;

            case 3:
                ft.replace(R.id.container, fragment4);
                ft.commit();
                break;
        }
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1){
//            if (resultCode == RESULT_OK){
//                btn_picture.setEnabled(false);
//                check_camera = 1;
//                imageView1.setImageResource(check_ok);
//            }
//        } else if (requestCode == 2){
//            if (resultCode == RESULT_OK){
//                btn_qrcode.setEnabled(false);
//                check_qr = 1;
//                imageView2.setImageResource(check_ok);


//                해결 포인트 -> store 가져올 것
//                Intent intent = getIntent();
//                Bundle bundle = intent.getExtras();
//                store = bundle.getString("store");
//                Toast.makeText(getApplicationContext(), "Store : " + store, Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (check_camera == 1 && check_qr == 1){
//            Toast.makeText(getApplicationContext(), "인증 완료", Toast.LENGTH_SHORT).show();
//            Handler delayHandler = new Handler();
//            delayHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {}
//            }, 1500);
//            Intent intent = new Intent(this, FinishActivity.class);
//            intent.putExtra("store", store);
//            startActivity(intent);
//            onDestroy();
//        }
//    }
}