package com.cqnu.wuq.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cqnu.wuq.R;

public class Phone extends AppCompatActivity {


    // private EditText et_name;
    private EditText mEtPhone;
    private Button mBtDail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        //et_name = (EditText) findViewById(R.id.et_name);u
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtDail = (Button) findViewById(R.id.bt_dail);
    }

    public void call(View view) {
        String PH = mEtPhone.getText().toString().trim();
        if ("".equals(mEtPhone)) {
            Toast.makeText(Phone.this, "号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel://" + PH));
            startActivity(intent);
        }

    }


}
