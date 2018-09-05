package com.cqnu.wuq.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cqnu.wuq.R;

import java.io.File;
import java.io.FileOutputStream;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtNumber;
    private EditText mEtPassword;
    private CheckBox mCbRemenber;
    private Button button;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //部署到页面
        //通过上下文得到一个共享参数的实例对象
       // sp = this.getSharedPreferences("info,txt", this.MODE_PRIVATE);  //第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。

        //寻找控件
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mCbRemenber = (CheckBox) findViewById(R.id.cb_remenber);

        button = (Button) findViewById(R.id.button);
    }


    public void login(View view) {


        //读取值
        String number = mEtNumber.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        //判断是否为空
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show(); //弹出一个吐司
            return;  //返回值
        } else {
            //判断是否记住用户名和密码
            if (mCbRemenber.isChecked()) {
                //被选中状态，需要记录用户名密码
                try {
                    File file = new File(this.getFilesDir(), "info,txt");  //new一个File
                    FileOutputStream fos = new FileOutputStream(file); //文件输出流,用于将数据写入File
                    String info = number + "##" + password;
                    fos.write(info.getBytes());//得到一个系统默认的编码格式的字节数组。
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //登陆操作，模拟登陆，数据应该提交给服务器判断是否正确
                if ("2015051002020".equals(number) && "123456".equals(password)) {
                    // Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                    //跳转下一个界面的语句
                    Intent mainIntent = new Intent(LoginActivity.this, welcome.class);  //到welcome界面
                    LoginActivity.this.startActivity(mainIntent);

                } else {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


