package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.check.corona_prototype.FaceDetection.FaceActivity;
import com.check.corona_prototype.QR.CreateQR;
import com.check.corona_prototype.QR.ScanQR;

import static com.check.corona_prototype.R.drawable.check_ok;

// 로그인 완료, 메인
public class MainActivity extends AppCompatActivity {
    TextView get_id;
    String id, store;
    Button btn_picture, btn_qrcode;
    int check_camera = 0, check_qr = 0;
    ImageView imageView1 = null, imageView2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_id = findViewById(R.id.get_id);
        btn_picture = findViewById(R.id.btn_picture);
        btn_qrcode = findViewById(R.id.btn_qrcode);
        imageView1 = findViewById(R.id.iv_check1);
        imageView2 = findViewById(R.id.iv_check2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        id = bundle.getString("id");
        getIntent().getExtras().clear();

        get_id.setText(name + "님 안녕하세요.");

        // 사진버튼
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
            }
        });

        // QR코드 버튼
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, CreateQR.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                btn_picture.setEnabled(false);
                check_camera = 1;
                imageView1.setImageResource(check_ok);
            }
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                btn_qrcode.setEnabled(false);
                check_qr = 1;
                imageView2.setImageResource(check_ok);


//                해결 포인트 -> store 가져올 것
//                Intent intent = getIntent();
//                Bundle bundle = intent.getExtras();
//                store = bundle.getString("store");
//                Toast.makeText(getApplicationContext(), "Store : " + store, Toast.LENGTH_SHORT).show();
            }
        }
        if (check_camera == 1 && check_qr == 1){
            Toast.makeText(getApplicationContext(), "인증 완료", Toast.LENGTH_SHORT).show();
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {}
            }, 1500);
            Intent intent = new Intent(this, FinishActivity.class);
            intent.putExtra("store", store);
            startActivity(intent);
            onDestroy();
        }
    }
}