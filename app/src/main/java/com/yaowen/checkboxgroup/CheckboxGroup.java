package com.yaowen.checkboxgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YAOWEN on 2015/10/15.
 */
public class CheckboxGroup extends LinearLayout implements CompoundButton.OnCheckedChangeListener, ViewGroup.OnHierarchyChangeListener {
    private TextView textView;
    private GridLayout grouplayout;
    private String grouptitle;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int columnCount;
    private Context context;

    public CheckboxGroup(Context context) {
        super(context);
        this.context = context;
    }

    public CheckboxGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs);
    }

    public CheckboxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
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
        View view = View.inflate(context, R.layout.checkboxgrouplayout, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckboxGroup);
        grouptitle = typedArray.getString(R.styleable.CheckboxGroup_checkboxGroupTitle);
        columnCount = typedArray.getInteger(R.styleable.CheckboxGroup_columnCount, 2);
        float titleWidth = typedArray.getDimension(R.styleable.CheckboxGroup_titleWidth, -1);
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
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, (CheckBox) buttonView, isChecked,
                    String.valueOf(buttonView.getTag()), getIndex((CheckBox) buttonView));
        }
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        {
            if (parent == grouplayout && child instanceof CheckBox) {
                ((CheckBox) child).setOnCheckedChangeListener(this);
            }
        }
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        if (parent == grouplayout && child instanceof CheckBox) {
            ((CheckBox) child).setOnCheckedChangeListener(null);
        }

    }

    /**
     * 实现了OnCheckedChangeListener的接口
     **/
    public interface OnCheckedChangeListener {
        /**
         * 实现onCheckedChanged的监听
         *
         * @param group    the group in which the checked radio button has changed
         * @param checkBox checkBox
         * @param check    boolean类型，判断checkbox的状态是否为选中或者不选中状态
         * @param value    checkBox的String值
         * @param index    该checkBox所在的index值
         */
        public void onCheckedChanged(CheckboxGroup group, CheckBox checkBox, boolean check,
                                     String value, @IdRes int index);
    }

    /**
     * 获取所有checkBox
     *
     * @return checkboxes ArrayList<CheckBox>
     **/
    public ArrayList<CheckBox> getAllCheckBox() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for (int i = 0; i < grouplayout.getChildCount(); i++) {
            View child = grouplayout.getChildAt(i);
            if (child instanceof CheckBox) {
                checkBoxes.add((CheckBox) child);
            }
        }
        return checkBoxes;
    }

    /**
     * 通过偏移量vlaue来获取checkBox
     *
     * @param value String
     * @return checkBox 当checkBox不存在时返回null；
     **/
    public CheckBox getCheckBox(String value) {
        CheckBox checkBox = null;
        for (int i = 0; i < grouplayout.getChildCount(); i++) {
            View child = grouplayout.getChildAt(i);
            if (child instanceof CheckBox) {
                String temp = (String) child.getTag();
                if (temp != null && temp.equals(value)) {
                    checkBox = (CheckBox) child;
                }
            }
        }
        return checkBox;
    }

    /**
     * 通过偏移量index来获取checkBox
     *
     * @param index int类型
     * @return checkBox 当不存在时候返回null；
     **/
    public CheckBox getCheckBox(int index) {
        CheckBox checkBox = null;
        if (index > 0 && index <= grouplayout.getChildCount() + 1) {
            for (int i = 0; i < grouplayout.getChildCount(); i++) {
                View child = grouplayout.getChildAt(i);
                if (child instanceof CheckBox) {
                    if (index - 1 == i) {
                        checkBox = (CheckBox) child;
                    }
                }
            }
        }
        return checkBox;
    }

    /**
     * 获取grouplayout里子控件的Index值
     *
     * @param checkbox CheckBox控件
     * @return grouplayout.indexOfChild(checkboxlayout)
     * 返回grouplayout里子控件的Index值，int类型
     **/
    public int getIndex(CheckBox checkbox) {
        return grouplayout.indexOfChild(checkbox);
    }

    /**
     * 取消所有选中的checkBox控件
     **/
    public void clearAllCheck() {
        ArrayList<CheckBox> array = getAllCheckBox();
        for (int i = 0; i < array.size(); i++) {
            CheckBox item = array.get(i);
            item.setChecked(false);
        }
    }

    /**
     * 动态添加CheckBox，使新加的checkBox的样式和原先的一样
     *
     * @param text  String
     *              将要设置checkBox的Text值，传入的参数
     * @param value String
     *              将要设置checkBox的Tag值，传入的参数
     * @return checkBox 返回CheckBox的控件
     **/
    public CheckBox addCheckBox(String text, String value) {
        CheckBox checkBox = (CheckBox) View.inflate(context, R.layout.checkboxlayout, null);
        checkBox.setText(text);
        checkBox.setTag(value);
        grouplayout.addView(checkBox);
        return checkBox;
    }

    /**
     * 动态增加checkBox控件
     *
     * @param child  view
     * @param index  int
     * @param params ViewGroup.LayoutParams
     * @deprecated 只要遍历出来的view是checkBox才加入到grouplayout中
     **/
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof CheckBox) {
            grouplayout.addView(child);
            return;
        }
        super.addView(child, index, params);
    }

    /**
     * 设置单个checkBox是否选中;
     * 如果checkBox不为null时，checkBox的才被选中；
     *
     * @param value String
     * @param check booblean
     **/
    public void setCheck(String value, boolean check) {
        CheckBox checkBox = getCheckBox(value);
        if (checkBox != null) {//判断checkBox是否为null，当其不为null时需要做的处理事件
            if (checkBox.isChecked() != check) {
                checkBox.setChecked(check);//设置该checkBox为选中状态
            }
        }
    }

    /**
     * 设置多个checkBox是否选中
     *
     * @param values String[]
     * @param check  booblean
     **/
    public void setCheck(String[] values, boolean check) {
        for (int i = 0; i < values.length; i++) {
            CheckBox checkBox = getCheckBox(values[i]);
            setCheck(values[i], check);
        }
    }

    /**
     * 获取已经被选中的checkBox的对应的值
     *
     * @return values ArrayList
     **/
    public ArrayList<String> getCheckedValues() {
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<CheckBox> allCheckBoxes = getAllCheckBox();
        for (int i = 0; i < allCheckBoxes.size(); i++) {
            CheckBox checkbox = allCheckBoxes.get(i);
            if (checkbox.isChecked()) {
                values.add(String.valueOf(checkbox.getTag()));
            }
        }
        return values;
    }

    /**
     * 通过参数value的值来判断该checkBox是否为选中状态
     *
     * @param value String类型
     * @return check   当值为true时，该checkBox是处于选中状态，否则，checkBox为未选中
     * 如果不存在对应的checkBox时，返回false；
     **/
    public boolean isChecked(String value) {
        boolean check = false;
        CheckBox box = getCheckBox(value);
        if (box != null) {
            check = box.isChecked();
        }
        return check;
    }

    /**
     * 通过参数index的值来判断该checkBox是否为选中状态
     *
     * @param index int类型
     * @return check   当值为true时，该checkBox是处于选中状态，否则，checkBox为未选中
     * 如果不存在对应的checkBox时，返回false；
     **/
    public boolean isChecked(int index) {
        boolean isCheck = false;
        CheckBox box = getCheckBox(index);
        if (box != null) {
            isCheck = box.isChecked();
        }
        return isCheck;
    }

    /**
     * 通过传入values的值，判断checkBox是否全为选中状态
     *
     * @param values String[] 传入的参数
     * @return check 当check为true时，说明checkBox全为选中
     **/
    public boolean isAllChecked(String[] values) {
        boolean check = true;
        for (int i = 0; i < values.length; i++) {
            CheckBox checkbox = getCheckBox(values[i]);
            if (checkbox != null) {
                check = checkbox.isChecked();
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
     * 获取checkBox的title值
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

