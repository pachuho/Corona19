package com.check.corona_prototype.Manger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.check.corona_prototype.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateQR extends AppCompatActivity {
    private String store;
    private ImageView iv;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);

        // 인자 전달받기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        store = bundle.getString("stroe");

//        Toast.makeText(CreateQR.this, store, Toast.LENGTH_SHORT).show();

        iv = (ImageView)findViewById(R.id.qrcode);
        text = "37.402629/126.922028/" + store;




    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
                text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                iv.setImageBitmap(bitmap);
                }catch (Exception e){}
                }
                }