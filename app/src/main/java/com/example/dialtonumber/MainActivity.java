package com.example.dialtonumber;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拨打号码
 */
public class MainActivity extends AppCompatActivity {
    //    第一个拨号按钮
    Button button1;
    //    第二个拨号按钮
    Button button2;
    //    获取editText
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) this.findViewById(R.id.button);
        button2 = (Button) this.findViewById(R.id.button2);
        editText = (EditText) this.findViewById(R.id.editText);
        button1.setOnClickListener(new doTest());
        button2.setOnClickListener(new doTest2());

    }

    //    拨号方法（10086）
    class doTest implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //            创建打电话的意图(new Intent),设置拨打电话的动作(Intent.ACTION_CALL),设置要拨打的电话号码(Uri.parse("tel:") + phone)
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
            //            在调用打电话权限前要增加权限的判断，如果没有就要弹出提示框让用户来选择是否允许打电话
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //                判断是否获取了权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                    //                  让用户手动授权
                    Toast.makeText(MainActivity.this, "请授权", Toast.LENGTH_LONG).show();
                    //            开启打电话的意图
                    startActivity(intent);

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            } else {
                //            开启打电话的意图
                startActivity(intent);

            }
        }

    }

    // 拨打任意号码
    class doTest2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            //获取输入的电话号码
            String phone = editText.getText().toString().trim();
            boolean isMatcher = false;
//            判断是否为合法的大陆手机号码
            isMatcher = isMobile(phone);
            if (isMatcher) {
                intent.setData(Uri.parse("tel:" + phone));
//               调用打电话的权限前要增加对权限的判断，如果没有就要弹出提示框是否开启权限
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    判断是否获取了权限
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CALL_PHONE)){
//                        让用户手动授权
                        Toast.makeText(MainActivity.this, "请授权", Toast.LENGTH_SHORT).show();
//                        开启打电话的意图
                        startActivity(intent);
                    }else{
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                }else{
                    startActivity(intent);
                }
            } else {
                editText.setText("输入不合法!");
            }
        }
    }

    //    验证是否为11位手机号码
    public static boolean isMobile(String numbers) {
        Pattern pattern = null;
        Matcher matcher = null;
        boolean isMatch = false;
        //        规定
        String regexs = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
        //        创建
        pattern = Pattern.compile(regexs);
        matcher = pattern.matcher(numbers);
        isMatch = matcher.matches();

        return isMatch;
    }

    /**
     *
     * 拨打号码的方法可以提出来
     */
}
