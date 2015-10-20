package com.yaowen.checkboxgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
       /* checkBox1= (CheckBox) findViewById(R.id.checkbox1);
        checkBox2= (CheckBox) findViewById(R.id.checkbox2);
        checkBox3= (CheckBox) findViewById(R.id.checkbox3);
        checkBox4= (CheckBox) findViewById(R.id.checkbox4);
        //checkBox1.setChecked("YY",true);*/
        final CheckboxGroup checkBoxGroup= (CheckboxGroup) findViewById(R.id.checkboxGroup);
        checkBoxGroup.setTitle("hello test!");
        Log.d("TAG", checkBoxGroup.getTitle());
        CheckBox box1=checkBoxGroup.getCheckBox("YY");
        CheckBox box2=checkBoxGroup.getCheckBox(2);
        ArrayList<CheckBox> list=new ArrayList<CheckBox>();
        list=checkBoxGroup.getAllCheckBox();
        checkBoxGroup.setCheck("text",true);
        checkBoxGroup.setCheck("4",true);
        String[] strings={"YY","2","test","3","hello"};
        String[] strings2={"YY","2","3"};
        checkBoxGroup.setCheck(strings, true);
        checkBoxGroup.setCheck("3", false);
        ArrayList<String> list2=checkBoxGroup.getCheckedValues();
        boolean b1=checkBoxGroup.isAllChecked(strings);
        boolean b2=checkBoxGroup.isAllChecked(strings2);
        checkBoxGroup.isChecked(1);
        checkBoxGroup.isChecked("YY");
        int i=checkBoxGroup.getColumnCount();
        Button test= (Button) findViewById(R.id.btn);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxGroup.setColumnCount(3);
            }
        });
        Button btn2= (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBoxGroup.setTitle("hello !!!!");
            }
        });
        Button btn3= (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxGroup.setCheck("4",false);
            }
        });
    }
}
