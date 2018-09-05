package com.cqnu.wuq.Activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cqnu.wuq.Dao.ContactinfoDao;
import com.cqnu.wuq.R;
import com.cqnu.wuq.alert.TransactionTool;
import com.cqnu.wuq.bean.ContactInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 联系人详情页
 */
public class AddressBookActivity extends AppCompatActivity {

    private ContactinfoDao dao;
    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtEmail;
    private EditText mEtHome;
    private EditText mEtNickname;
    private EditText mEtTransaction;
    private Button mBtAdd;
    private Button mBtDelete;

    private Button mBtUpdate;
    private Button mBtTransaction;
    Calendar mCalendar = Calendar.getInstance();

    public static TransactionTool mAlarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        initView();//先调用initView方法,初始化界面布局
        initData();//然后调用initData方法，初始化数据
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dao = new ContactinfoDao(this);  //new 一个Dao出来,contactinfodao里面的方法
        Intent intent = getIntent();//可以获取键值对
        String transaction = intent.getStringExtra("transaction");
        String name = intent.getStringExtra("name");  //取得另一个activity传送的参数
        if (!TextUtils.isEmpty(name)) {//如果姓名不为空
            ContactInfo info = dao.query(name); //根据姓名查询
            if (info != null) {
                info.setName(name);
                setContactInfo(info); //置界面所有EditText文本内容
            }
        }
        if ( !TextUtils.isEmpty(transaction) ) {// 事务内容不为空说明不是第一次打开，是设置了事务内容
            mEtTransaction.setText(transaction);
        }
        mAlarmHelper = new TransactionTool(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_address_book);  //部署界面
        //寻找控件
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtHome = (EditText) findViewById(R.id.et_home);
        mEtNickname = (EditText) findViewById(R.id.et_nickname);
        mEtTransaction = (EditText) findViewById(R.id.et_transaction);
        mBtAdd = (Button) findViewById(R.id.bt_add);
        mBtDelete = (Button) findViewById(R.id.bt_delete);

        mBtUpdate = (Button) findViewById(R.id.bt_update);
        mBtTransaction = (Button) findViewById(R.id.bt_transaction);
        //添加事件监听
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfo info = getContactInfo();
                if (info != null) {
                    String name = info.getName();
                    ContactInfo queryInfo = dao.query(name);
                   // Toast.makeText(AddressBookActivity.this, "添加联系人成功", Toast.LENGTH_SHORT).show();
                    if (queryInfo == null) {
                        dao.add(info);
                        Toast.makeText(AddressBookActivity.this, "添加联系人添加成功", Toast.LENGTH_SHORT).show();
                    }
                    setResult(1); //返回码为1，更新activity_main界面
                    finish();
                }
            }
        });
        //删除事件监听
        mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfo info = getContactInfo();
                if (info != null) {
                    String name = info.getName();
                    ContactInfo queryInfo = dao.query(name);
                    if (queryInfo != null) {
                        dao.delete(name);
                        Toast.makeText(AddressBookActivity.this, "联系人删除成功", Toast.LENGTH_SHORT).show();
                    }
                    setResult(1); //返回码为1，更新activity_main界面
                    finish();
                }
            }
        });

        //修改事件监听
        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactInfo info = getContactInfo();
                if (info != null) {
                    String name = info.getName();
                    ContactInfo queryInfo = dao.query(name);
                    if (queryInfo != null) {
                        dao.update(info);
                        Toast.makeText(AddressBookActivity.this, "更新联系人信息成功", Toast.LENGTH_SHORT).show();
                    }
                    setResult(1);  //返回码为1，更新activity_main界面
                    finish();
                }

            }
        });

        /*事务按钮的点击事件*/
        mBtTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String transaction =  mEtTransaction.getText().toString().trim();
                final String name = mEtName.getText().toString();
                final String phone = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(transaction) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(AddressBookActivity.this,"姓名、电话、事务内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                mCalendar.setTimeInMillis(System.currentTimeMillis());  ///将一个日历实例的时间设置为当前系统时间
                int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);  ////获取实例的小时
                int mMinute = mCalendar.get(Calendar.MINUTE);  ////获取实例的分钟
                //显示时间选择对话框（TimePicker）
                new TimePickerDialog(AddressBookActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {    //新建一个TimePickerDialog实例的设置时间的监听器
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {    ////实现监听器类的设置时间的方法
                                mCalendar.setTimeInMillis(System.currentTimeMillis());//将mCalendar的时间设置为系统当前时间的毫秒
                                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);//将mCalendar的小时设置为通过timePicker设置的小时值
                                mCalendar.set(Calendar.MINUTE, minute);//将mCalendar的分钟设置为通过timePicker设置的分钟值
                                mCalendar.set(Calendar.SECOND, 0);//将mCalendar的秒设置为0
                                mCalendar.set(Calendar.MILLISECOND, 0);//将mCalendar的毫秒设置为0
                                mAlarmHelper.setAlarm(1, name,
                                        phone, transaction, mCalendar.getTimeInMillis());   //返回相关信息,设置显示
                                finish();
                            }
                        }, mHour, mMinute, false).show();//显示闹钟
            }
        });
    }


    /**
     * 获取界面所有EditText文本内容
     *
     * @return 返回联系人信息对象
     */
    private ContactInfo getContactInfo() {
        //获取信息
        String name = mEtName.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();
        String home = mEtHome.getText().toString().trim();
        String nickname = mEtNickname.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "该信息不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new ContactInfo(name, phone, email, home, nickname);
    }

    /**
     * 设置界面所有EditText文本内容
     *
     * @param info 联系人信息对象
     */
    private void setContactInfo(ContactInfo info) {
        mEtName.setText(info.getName());
        mEtPhone.setText(info.getPhone());
        mEtEmail.setText(info.getEmail());
        mEtHome.setText(info.getHome());
        mEtNickname.setText(info.getNickname());

    }


    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater()获得MenuIflater对象，inflate()方法给当前活动创建菜单(1.使用菜单填充器将一个菜单资源设置给menu对象)
        getMenuInflater().inflate(R.menu.menu_address, menu); //添加menu_address菜单
        //true 代表允许创建的菜单显示出来
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//点击选择项促发事件
        switch (item.getItemId()) {
            // 点击了菜单添加联系人
            case R.id.menu_phone:
                //Toast.makeText(this, "lianxi", Toast.LENGTH_SHORT).show();
                //获取号码拨打电话
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel://" + mEtPhone.getText().toString().trim()));
                startActivity(intent);
                break;
            case R.id.menu_message:
                //获取号码发送短信
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SENDTO);
                intent1.setData(Uri.parse("smsto://" + mEtPhone.getText().toString().trim()));
                intent1.putExtra("sms_body", "你好");
                startActivity(intent1);

                break;
            //导入
            case R.id.menu_inport:
                inputContacts();
                break;
            //导出
            case R.id.menu_output:
                ouptputContacts();
                break;
            //清空
            case R.id.menu_clear:
                // 弹出一个对话框循环清空数据库内容
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("要清空所有联系人吗？").setMessage("点击确定清空，点击取消不清空")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<ContactInfo> infos = dao.queryAll();
                                for (int i = 0; i < infos.size(); i++) {
                                    dao.delete(infos.get(i).getName());
                                }
                                Toast.makeText(AddressBookActivity.this,"清空完毕", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 导入联系人
     */
    private void inputContacts() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "contacts.txt");
            // 如果存在则读取
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String s = "";
                // 循环读取
                while ((s = br.readLine()) != null && s.length() > 1) {
                    //以 :分割字符串
                    String[] split = s.split(":");
                    String name = split[0];
                    String phone = split[1];
                    String email = split[2];
                    String home = split[3];
                    String nickname = split[4];
                    dao.add(new ContactInfo(name, phone, email, home, nickname));
                }
                br.close();
                Toast.makeText(this,"导入完毕", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出联系人
     */
    private void ouptputContacts() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "contacts.txt");
            if (file.exists()) {
                file.delete();
                file = new File(Environment.getExternalStorageDirectory(), "contacts.txt");
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            ArrayList<ContactInfo> infos = dao.queryAll();
            //循环写入到sd卡上的contacts.txt文件
            for (int i = 0; i < infos.size(); i++) {
                ContactInfo info = infos.get(i);
                String name = info.getName();
                String phone = info.getPhone();
                String email = info.getEmail();
                String home = info.getHome();
                String nickname = info.getNickname();
                String s;
                //以:间隔
                s = name + ":" + phone + ":" + email + ":" + home + ":" + nickname;
                bw.write(s);
                // 换行
                bw.newLine();
                // 刷新写入到sd卡
                bw.flush();
            }
            bw.close();
            Toast.makeText(this,"导出完毕", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
