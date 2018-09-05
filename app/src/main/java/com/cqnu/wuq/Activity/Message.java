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

public class Message extends AppCompatActivity {

    private EditText mEtPhoneNum;
    private Button mBtSendmessage;
    private EditText mEtMessageCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mEtPhoneNum = (EditText) findViewById(R.id.et_phoneNum);
        mBtSendmessage = (Button) findViewById(R.id.bt_sendmessage);
        mEtMessageCon = (EditText) findViewById(R.id.et_messsageCon);
    }
    public void send(View view) {
        String  Num =  mEtPhoneNum.getText().toString().trim();//读取值
        String  Mess=  mEtMessageCon.getText().toString().trim();//读取值
        if ("".equals(mEtPhoneNum) || "".equals(mEtMessageCon)) {
            Toast.makeText(Message.this,"号码和内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto://"+Num));
            intent.putExtra("sms_body",Mess);
            startActivity(intent);
        }

    }
}
