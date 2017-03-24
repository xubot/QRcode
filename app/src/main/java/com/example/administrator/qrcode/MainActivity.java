package com.example.administrator.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.uuzuche.lib_zxing.activity.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Bitmap mBitmap;
    private TextView scan;
    private TextView create;
    private EditText editText;
    private ImageView imageView;
    private List<TextView> textViewList=new ArrayList<>();
    private TextView logocreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView()
    {
        scan = (TextView) findViewById(R.id.scan);
        create = (TextView) findViewById(R.id.create);
        logocreate = (TextView) findViewById(R.id.logocreate);
        editText = (EditText) findViewById(R.id.et);
        imageView = (ImageView) findViewById(R.id.iv);
        textViewList.add(scan);
        textViewList.add(create);
        textViewList.add(logocreate);
        for(TextView t:textViewList)
        {
            t.setOnClickListener(this);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 处理二维码扫描结果
        if (requestCode == 1000) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Log.d("zzz", result);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.scan :
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1000);
                break;

            case R.id.create :
                //生成 二维码 的点击监听
                String create = editText.getText().toString();
                if (TextUtils.isEmpty(create)) {
                    Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //editText.setText("");
                mBitmap = CodeUtils.createImage(create, 400, 400, null);
                imageView.setImageBitmap(mBitmap);
                break;
            case R.id.logocreate :
                String logocreate = editText.getText().toString();
                if (TextUtils.isEmpty(logocreate)) {
                    Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //editText.setText("");
                mBitmap = CodeUtils.createImage(logocreate, 400, 400, BitmapFactory.decodeResource(getResources(),R.drawable.touxiang));
                imageView.setImageBitmap(mBitmap);
                break;
        }
    }
}
