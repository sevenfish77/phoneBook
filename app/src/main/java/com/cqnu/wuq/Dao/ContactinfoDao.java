package com.cqnu.wuq.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cqnu.wuq.Sqlite.MyABOpenHelper;
import com.cqnu.wuq.bean.ContactInfo;

import java.util.ArrayList;

public class ContactinfoDao {

    private final MyABOpenHelper helper;
    //构造方法
    public ContactinfoDao(Context context){
        helper = new MyABOpenHelper(context);//创建一个成员helper
    }

    /**
     * 添加一条记录
     * @param name
     *          联系人姓名
     * @param phone
     *          联系人电话
     * @param email
     *          联系人邮箱
     * @param home
     *          联系人家庭地址
     * @param nickname
     *          联系人昵称
     * @return
     */
    /**
     *添加一条记录
     * @param info　　
     *           联系人信息
     * @return
     *          返回的是数据库的行号， -1代表失败
     */
    public long add(ContactInfo info){
        SQLiteDatabase db = helper.getWritableDatabase(); //返回一个数据库对象
        //db.execSQL("");
        ContentValues values = new ContentValues();//NEW一个内容值，并返回value变量
        values.put("name",info.getName());//键值对,运用ContactInfo里面的值
        values.put("phone",info.getPhone());
        values.put("email",info.getEmail());
        values.put("home", info.getHome());
        values.put("nickname",info.getNickname());
        //调用insert方法,添加值
        long rowid = db.insert("contactinfo", null, values);//插入成功返回rowid
        //关闭数据库释放资源
        db.close();
        return rowid;
    }

    /**
     * 删除一条记录
     * @param name
     *          联系人姓名
     * @return
     *          返回0代表未删除数据，返回整数int代表删除了多少行信息
     */
    public int delete(String name){
        SQLiteDatabase db = helper.getWritableDatabase();//返回回数据库对象
        int rowcount = db.delete("contactinfo", "name=?", new String[]{name});//返回到影响了多少行（删除姓名为？的人的信息）
        //记得关闭数据库释放资源
        db.close();
        return rowcount;  //返回行数（int）
    }

    /**
     * 修改联系人信息
     * @param info
     *          联系人信息
     * @return
     *          影响的总行数
     */
    public int update(ContactInfo info){
        SQLiteDatabase db = helper.getWritableDatabase();//返回回数据库对象
        ContentValues values = new ContentValues();//NEW一个内容值，并返回value变量
        values.put("phone",info.getPhone());
        values.put("email",info.getEmail());
        values.put("home",info.getHome());
        values.put("nickname",info.getNickname());

        int rowcount = db.update("contactinfo", values, "name=?", new String[]{info.getName()});

        //记得关闭数据库释放资源
        db.close();
        return rowcount;  //返回行数（int）
    }

    /**
     * 查询联系人信息
     * @param name
     *           联系人姓名
     * @return
     *         返回联系人信息
     */

    public ContactInfo query(String name){
        SQLiteDatabase db = helper.getReadableDatabase();//返回回数据库对象
        //返回一个游标结果集
        Cursor cursor = db.query("contactinfo", new String[]{"phone", "email", "home", "nickname",}, "name=?", new String[]{name}, null, null, null);


        if (cursor.getCount() == 0) return null; //cursor获取列数为0，返回空
        ContactInfo info = new ContactInfo(name);
        if (cursor.moveToNext()) {//如果数据存在
            info.setPhone(cursor.getString(0));
            info.setEmail(cursor.getString(1));
            info.setHome(cursor.getString(2));
            info.setNickname(cursor.getString(3));
        }
        //记得关闭数据库释放资源
        cursor.close(); //关闭游标
        db.close();
        return info; //返回
    }

    public ArrayList<ContactInfo> queryAll(){
        SQLiteDatabase db = helper.getReadableDatabase();  //对数据库进行读和写的功能
        //返回一个游标结果集
        Cursor cursor = db.query("contactinfo", new String[]{"name, phone, email, home, nickname"}, null, null, null, null, null);
        ArrayList<ContactInfo> infos = new ArrayList<>();
        while (cursor.moveToNext()) {//如果数据存在
            ContactInfo info = new ContactInfo();
            info.setName(cursor.getString(0));
            info.setPhone(cursor.getString(1));
            info.setEmail(cursor.getString(2));
            info.setHome(cursor.getString(3));
            info.setNickname(cursor.getString(4));

            infos.add(info);//将info里面的信息添加到infos里面
        }
        //记得关闭数据库释放资源
        cursor.close();
        db.close();
        return infos;//返回
    }

}
