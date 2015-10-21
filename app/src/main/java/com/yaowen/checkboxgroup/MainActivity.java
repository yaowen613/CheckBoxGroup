package com.yaowen.checkboxgroup;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init_checkBox();
        init_groupRadio();
    }

    private void init_groupRadio() {
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setTitle("请做出单项选择：");
        String str = radioGroup.getTitle();
        //Toast.makeText(MainActivity.this, "titel:" + str, Toast.LENGTH_SHORT).show();
        final RadioButton radio1 = radioGroup.getRadioButton("Hello");
        final RadioButton radio2 = radioGroup.getRadioButton(3);
        Button addRadio= (Button) findViewById(R.id.btn_Add_radio2);
        ArrayList<RadioButton> lists = new ArrayList<RadioButton>();
        lists = radioGroup.getAllRadioButton();
        radioGroup.setCheck("Hello", true);
        // radioGroup.setCheck("4",true);
        String str2 = radioGroup.getCheckedValue();
        boolean b3 = radioGroup.isChecked("Hello");
        boolean b4 = radioGroup.isChecked(2);
        int count = radioGroup.getColumnCount();
        Button btn = (Button) findViewById(R.id.btn_radio);
        Button btn2 = (Button) findViewById(R.id.btn_Remove_radio2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.setTitle("你好，世界！");
                radioGroup.setColumnCount(3);
                radioGroup.setCheck("Hello", false);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, RadioButton radio, boolean check,
                                         String value, @IdRes int index) {
                Toast.makeText(MainActivity.this, "这是调试语句", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "check:" + check + "tag:" +
                        value, Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radioGroup.addView();
                radioGroup.removeView(radio1);
                radioGroup.removeView(radio2);
                Toast.makeText(MainActivity.this, "这是调试语句！！！", Toast.LENGTH_SHORT).show();
            }
        });
        addRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RadioButton radioButton=new RadioButton(MainActivity.this);
                radioButton.setText("new bu");
                radioButton.setTag("ww");
                radioGroup.addView(radioButton);
                //radioButton.setChecked(true);
            }
        });
    }

    private void init_checkBox() {
        final CheckboxGroup checkBoxGroup = (CheckboxGroup) findViewById(R.id.checkboxGroup);
        checkBoxGroup.setTitle("hello test!");
        Log.d("TAG", checkBoxGroup.getTitle());
        final CheckBox box1 = checkBoxGroup.getCheckBox("YY");
        CheckBox box2 = checkBoxGroup.getCheckBox(2);
        ArrayList<CheckBox> list = new ArrayList<CheckBox>();
        list = checkBoxGroup.getAllCheckBox();
        checkBoxGroup.setCheck("text", true);
        checkBoxGroup.setCheck("4", true);
        String[] strings = {"YY", "2", "test", "3", "hello"};
        String[] strings2 = {"YY", "2", "3"};
        checkBoxGroup.setCheck(strings, true);
        checkBoxGroup.setCheck("3", false);
        ArrayList<String> list2 = checkBoxGroup.getCheckedValues();
        boolean b1 = checkBoxGroup.isAllChecked(strings);
        boolean b2 = checkBoxGroup.isAllChecked(strings2);
        checkBoxGroup.isChecked(1);
        checkBoxGroup.isChecked("YY");
        int i = checkBoxGroup.getColumnCount();
        Button test = (Button) findViewById(R.id.btn);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxGroup.setColumnCount(3);
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBoxGroup.setTitle("hello !!!!");
            }
        });
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxGroup.setCheck("4", false);
            }
        });
        final Button listener = (Button) findViewById(R.id.btn_listener);
        listener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxGroup.setCheck("3", true);

            }
        });
        checkBoxGroup.setOnCheckedChangeListener(new CheckboxGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CheckboxGroup group, CheckBox checkBox, boolean check, String value, @IdRes int index) {
                Toast.makeText(MainActivity.this, "这是调试语句", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "check:" + check + "tag:" + checkBox.getTag().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "group" + group.toString() + "checkBox" + checkBox.toString() + "value" + value + "index" + String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        });
        Button btn4 = (Button) findViewById(R.id.btn_4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ColumnCount:" + checkBoxGroup.getColumnCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
