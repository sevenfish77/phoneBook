package com.cqnu.wuq.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cqnu.wuq.Dao.ContactinfoDao;
import com.cqnu.wuq.R;
import com.cqnu.wuq.bean.ContactInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ContactinfoDao dao;
    private ArrayList<ContactInfo> mInfoList;
    private ListView mLvContact;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //initData();//调用
        // initView();

        //1.先初始化布局
        setContentView(R.layout.activity_main); //界面部署
        //寻找控件
        mLvContact = (ListView) findViewById(R.id.lv_contact);

        //2，初始化数据
        dao = new ContactinfoDao(this);
        mInfoList = dao.queryAll();
        //创建一个内部类MyAdapter
        mAdapter = new MyAdapter();
        mLvContact.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater()获得MenuIflater对象，inflate()方法给当前活动创建菜单(1.使用菜单填充器将一个菜单资源设置给menu对象)
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //true 代表允许创建的菜单显示出来
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//点击选择项促发事件
        switch (item.getItemId()) {
            // 点击了菜单添加联系人
            case R.id.menu_add:
                //Toast.makeText(this, "lianxi", Toast.LENGTH_SHORT).show();
                //跳转下一个界面的语句
                Intent mainIntent = new Intent(MainActivity.this, AddressBookActivity.class);  //到AddressBookActivity界面
                MainActivity.this.startActivity(mainIntent);
                break;

            case R.id.menu_phone:
                Intent intent1 = new Intent(MainActivity.this, Phone.class); //跳转到Phone页面
                MainActivity.this.startActivity(intent1);
                break;
            case R.id.menu_message:
                Intent intent2 = new Intent(MainActivity.this, Message.class); //跳转到Message页面
                MainActivity.this.startActivity(intent2);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    //需要刷新数据列表的代码就应该写在数据列表Activity的onStart()函数里面，而不能写在onCreate()函数里面，因为回到数据列表Activity时调用的是onStart()
    protected void onStart() {
        // 返回到MainActivity需要更新界面
        mInfoList = dao.queryAll();
        mAdapter.notifyDataSetChanged();    //如果适配器的内容改变，notifyDataSetChanged方法将会通过一个外部方法强制调用getView来刷新每个Item的内容。
        super.onStart();
    }

    /**
     *
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     *
     *
     *
     * @param requestCode
     *          requestCode 请求码，即调用startActivityForResult()传递过去的值
     * @param resultCode
     *          resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     * @param data
     */
    @Override
    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 在该Activity会打开ResultActivity和NewActivity

        // AddressInfoActivity返回码是1则需要更新界面
        if (resultCode == 1) {
            mInfoList = dao.queryAll();
            mAdapter.notifyDataSetChanged();//刷新，获取适配器中信息
        }
        super.onActivityResult(requestCode, resultCode, data);  //重写父类的方法
    }


    //创建一个内部类MyAdapter(修改其继承自BaseAdapter)
    //定义自己的适配器,Adapter实现后端数据和前端显示的重要适配接口，将数据信息显示到界面上 定义自己的适配器

    private class MyAdapter extends BaseAdapter {
        //创建方法

        /**
         * 获取列表里面一共有多少条记录
         *
         * @return
         */
        @Override
        public int getCount() {
            return mInfoList != null ? mInfoList.size() : 0; //不限制确定的记录行数
            // return  3;
        }

        /**
         * 获取一条item
         *
         * @param position item的位置
         * @return item
         */
        @Override
        public ContactInfo getItem(int position) {
            return mInfoList.get(position);
        }

        /**
         * 获取一条item的id
         *
         * @param position item的位置
         * @return item的id
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 返回一个view对象，这个view对象显示在指定的位置
         *
         * @param position    item的位置
         * @param convertView 回收的view
         * @param parent      父容器
         * @return 返回的view对象
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(MainActivity.this, R.layout.item_con, null); //chengxiN到item_con页面

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);//
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            ImageView iv_tu = (ImageView) findViewById(R.id.iv_tu);
            tv_name.setText(getItem(position).getName());   //tv_name中显示姓名
            tv_phone.setText(getItem(position).getPhone()); //tv_phone中显示电话

            //设置点击监听事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddressBookActivity.class);
                    intent.putExtra("name", getItem(position).getName());//向下一个活动传递了一个数据
                    startActivityForResult(intent, 0);//打开新的Activity，新的Activity 关闭后会向前面的Activity传回数据，为了得到传回的数据.

                }
            });

            return view;
        }
            /*TextView tv;
            if (convertView == null) {
                tv = new TextView(MainActivity.this);
                System.out.println("创建新的view:" + position);
            } else {
                tv = (TextView) convertView;
                System.out.println("使用回收的view:" + position);
            }
            tv.setText("我是文本：" + position);
            tv.setTextColor(Color.RED);
            tv.setTextSize(20);
            return tv;
            */

    }

}
