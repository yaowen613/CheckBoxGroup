package com.yaowen.checkboxgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YAOWEN on 2015/10/15.
 */
public class RadioGroup extends LinearLayout implements CompoundButton.OnCheckedChangeListener, ViewGroup.OnHierarchyChangeListener {
    private TextView textView;
    private GridLayout grouplayout;
    private String grouptitle;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private String lastCheckRadioValue;
    private int columnCount;
    private Context context;

    public RadioGroup(Context context) {
        super(context);
        this.context = context;
    }


    public RadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs);
    }

    public RadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);
    }

    public int getColumnCount() {
        return grouplayout.getColumnCount();
    }

    public void setColumnCount(int columnCount) {
        grouplayout.setColumnCount(columnCount);
    }

    /**
     * 初始化所有view控件
     *
     * @param context Context
     * @param attrs   AttributeSet
     **/
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.radiogrouplayout, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioGroup);
        grouptitle = typedArray.getString(R.styleable.RadioGroup_checkboxGroupTitle);
        columnCount = typedArray.getInteger(R.styleable.RadioGroup_columnCount, 2);
        float titleWidth = typedArray.getDimension(R.styleable.RadioGroup_titleWidth, -1);
        textView = (TextView) view.findViewById(R.id.grouptitle);
        grouplayout = (GridLayout) view.findViewById(R.id.grouplayout);
        grouplayout.setColumnCount(columnCount);
        grouplayout.setOnHierarchyChangeListener(this);
        if (grouptitle != null) {
            if (titleWidth != -1) {
                textView.setWidth((int) titleWidth);
            }
            textView.setText(grouptitle);
        }
        typedArray.recycle();
    }

    /**
     * 实现了监听的函数
     **/
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (lastCheckRadioValue != null && !lastCheckRadioValue.equals("null")) {
                RadioButton button = getRadioButton(lastCheckRadioValue);
                if (button != null) {
                    button.setChecked(false);
                }
            }
            lastCheckRadioValue = String.valueOf(buttonView.getTag());
        }
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, (RadioButton) buttonView, isChecked,
                    String.valueOf(buttonView.getTag()), getIndex((RadioButton) buttonView));
        }
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        {
            if (parent == grouplayout && child instanceof RadioButton) {
                if (((RadioButton) child).isChecked()) {
                    //当配置出错造成有多个选中的radio时，只保持第一个，其他的全部取消选中
                    //当新增的radio是选中状态且此前已经有选中的radio时，强制取消新增的radio的选中
                    if (lastCheckRadioValue != null && !lastCheckRadioValue.equals("null")) {
                        ((RadioButton) child).setChecked(false);
                    } else {
                        lastCheckRadioValue = String.valueOf(child.getTag());
                    }
                }
                ((RadioButton) child).setOnCheckedChangeListener(this);
            }
        }
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        if (parent == grouplayout && child instanceof RadioButton) {
            if (((RadioButton) child).isChecked()) {
                lastCheckRadioValue = null;
            }
            ((RadioButton) child).setOnCheckedChangeListener(null);
        }

    }

    /**
     * 实现了OnCheckedChangeListener的接口
     **/
    public interface OnCheckedChangeListener {
        /**
         * 实现onCheckedChanged的监听
         *
         * @param group the group in which the checked radio button has changed
         * @param radio radiobutton
         * @param check boolean类型，判断radio的状态是否为选中或者不选中状态
         * @param value radiobutton的String值
         * @param index 该radiobutton所在的index值
         */
        public void onCheckedChanged(RadioGroup group, RadioButton radio, boolean check,
                                     String value, @IdRes int index);
    }

    /**
     * 获取所有radiobutton
     *
     * @return radioes ArrayList<RadioButton>
     **/
    public ArrayList<RadioButton> getAllRadioButton() {
        ArrayList<RadioButton> radioes = new ArrayList<RadioButton>();
        for (int i = 0; i < grouplayout.getChildCount(); i++) {
            View child = grouplayout.getChildAt(i);
            if (child instanceof RadioButton) {
                radioes.add((RadioButton) child);
            }
        }
        return radioes;
    }

    /**
     * 通过偏移量vlaue来获取radio
     *
     * @param value String
     * @return radio 当radio不存在时返回null；
     **/
    public RadioButton getRadioButton(String value) {
        RadioButton radio = null;
        for (int i = 0; i < grouplayout.getChildCount(); i++) {
            View child = grouplayout.getChildAt(i);
            if (child instanceof RadioButton) {
                String temp = (String) child.getTag();
                if (temp != null && temp.equals(value)) {
                    radio = (RadioButton) child;
                }
            }
        }
        return radio;
    }

    /**
     * 通过偏移量index来获取radio
     *
     * @param index int类型
     * @return radio 当不存在时候返回null；
     **/
    public RadioButton getRadioButton(int index) {
        RadioButton radio = null;
        if (index > 0 && index <= grouplayout.getChildCount() + 1) {
            for (int i = 0; i < grouplayout.getChildCount(); i++) {
                View child = grouplayout.getChildAt(i);
                if (child instanceof RadioButton) {
                    if (index - 1 == i) {
                        radio = (RadioButton) child;
                    }
                }
            }
        }
        return radio;
    }

    /**
     * 获取grouplayout里子控件的Index值
     *
     * @param radio RadioButton控件
     * @return grouplayout.indexOfChild(radio)
     * 返回grouplayout里子控件的Index值，int类型
     **/
    public int getIndex(RadioButton radio) {
        return grouplayout.indexOfChild(radio);
    }

    /**
     * 取消所有选中的radio控件
     **/
    public void clearAllCheck() {
        ArrayList<RadioButton> array = getAllRadioButton();
        for (int i = 0; i < array.size(); i++) {
            RadioButton item = array.get(i);
            item.setChecked(false);
        }
    }

    /**
     * 动态增加radio控件
     *
     * @param child  view
     * @param index  int
     * @param params ViewGroup.LayoutParams
     * @deprecated 只要遍历出来的view是radio才加入到grouplayout中
     **/
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioButton) {
            grouplayout.addView(child);
            return;
        }
        super.addView(child, index, params);
    }
    public void removeView(View child){
        if (child instanceof RadioButton) {
            grouplayout.removeView(child);
            return;
        }
        super.removeView(child);
    }
    /**
     * 设置单个radio是否选中;
     * 如果radio不为null时，radio的才被选中；
     *
     * @param value String
     * @param check booblean
     **/
    public void setCheck(String value, boolean check) {
        RadioButton radio = getRadioButton(value);
        if (radio != null) {//判断radio是否为null，当其不为null时需要做的处理事件
            clearAllCheck();
            if (radio.isChecked() != check) {
                radio.setChecked(check);//设置该radio为选中状态
            }
        }
    }

    /**
     * 获取已经被选中的radio的对应的值
     *
     * @return values ArrayList
     **/
    public ArrayList<String> getCheckedValues() {
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<RadioButton> allRadioButtones = getAllRadioButton();
        for (int i = 0; i < allRadioButtones.size(); i++) {
            RadioButton radio = allRadioButtones.get(i);
            if (radio.isChecked()) {
                values.add(String.valueOf(radio.getTag()));
            }
        }
        return values;
    }

    /**
     * 获取已经被选中的radio的对应的值
     *
     * @return value String类型 获取的是选中的radioButton的tag值；
     **/
    public String getCheckedValue() {
        String value = "";
        ArrayList<RadioButton> allRadioButtones = getAllRadioButton();
        for (int i = 0; i < allRadioButtones.size(); i++) {
            RadioButton radio = allRadioButtones.get(i);
            if (radio.isChecked()) {
                value = String.valueOf(radio.getTag());
            }
        }
        return value;
    }

    /**
     * 通过参数value的值来判断该radio是否为选中状态
     *
     * @param value String类型
     * @return check   当值为true时，该radio是处于选中状态，否则，radio为未选中
     * 如果不存在对应的radio时，返回false；
     **/
    public boolean isChecked(String value) {
        boolean check = false;
        RadioButton radio = getRadioButton(value);
        if (radio != null) {
            check = radio.isChecked();
        }
        return check;
    }

    /**
     * 通过参数index的值来判断该radio是否为选中状态
     *
     * @param index int类型
     * @return check   当值为true时，该radio是处于选中状态，否则，radio为未选中
     * 如果不存在对应的radio时，返回false；
     **/
    public boolean isChecked(int index) {
        boolean isCheck = false;
        RadioButton radiobutton = getRadioButton(index);
        if (radiobutton != null) {
            isCheck = radiobutton.isChecked();
        }
        return isCheck;
    }

    /**
     * 通过传入values的值，判断radio是否全为选中状态
     *
     * @param values String[] 传入的参数
     * @return check 当check为true时，说明radio全为选中
     **/
    public boolean isAllChecked(String[] values) {
        boolean check = true;
        for (int i = 0; i < values.length; i++) {
            RadioButton radio = getRadioButton(values[i]);
            if (radio != null) {
                check = radio.isChecked();
                if (!check) {
                    break;
                }
            }
        }
        return check;
    }

    /**
     * 设置textView的title值
     *
     * @param value String类型
     *              通过传入一个value来设置textView的值
     **/
    public void setTitle(String value) {
        textView.setText(value);
    }

    /**
     * 获取radio的title值
     *
     * @return textViewText String类型，为textView的title的值；
     * 如果没有没有返回值，默认为null
     **/
    public String getTitle() {
        String textViewText = null;
        textViewText = (String) textView.getText();
        return textViewText;
    }
}

