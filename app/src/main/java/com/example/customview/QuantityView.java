package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class QuantityView extends FrameLayout {
    ImageView add,subtract;
    EditText mEditQuantity;
    private int minQuantity;
    private int maxQuantity;
    private int startQuantity;
    private int currentQuantity;
    private int deltaQuantity;
    private int textColorRes;
    private int colorRes;
    private boolean isOutlined;

    public QuantityView(@NonNull Context context) {
        super(context);
        init(context);

    }



    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttributes(context,attrs,0,0);
        init(context);

    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(context,attrs,defStyleAttr,0);
        init(context);


    }

    public QuantityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        obtainStyledAttributes(context,attrs,defStyleAttr,defStyleRes);
        init(context);


    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       if(attrs!=null){
           TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,R.styleable.QuantityView,defStyleAttr,defStyleRes);
           minQuantity = ta.getInteger(R.styleable.QuantityView_minQuantity,0);
           maxQuantity = ta.getInteger(R.styleable.QuantityView_maxQuantity,100);
           startQuantity = ta.getInteger(R.styleable.QuantityView_startQuantity,0);
           deltaQuantity = ta.getInteger(R.styleable.QuantityView_deltaQuantity,10);
           colorRes = ta.getResourceId(R.styleable.QuantityView_colorOfQuantity,R.color.black);
           textColorRes = ta.getResourceId(R.styleable.QuantityView_colorOfText,R.color.black);
           isOutlined = ta.getBoolean(R.styleable.QuantityView_isOutlined,false);
           return;
       }
       minQuantity = 0;
       maxQuantity = 100;
       startQuantity = 0;
       deltaQuantity = 10;
       colorRes = R.color.black;
       textColorRes = R.color.black;
       isOutlined = false;

    }
    public int getQuantity() {
        return currentQuantity;
    }

    private void init(Context context) {
        View rootView = inflate(context,R.layout.layout_quantity,this);
        add = rootView.findViewById(R.id.add);
        subtract = rootView.findViewById(R.id.minus);
        mEditQuantity = rootView.findViewById(R.id.quantity);

        setupView();
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             setQuantity(false);
            }
        });

        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              setQuantity(true);
            }
        });
    }

    private void setQuantity(boolean isSubtract) {
        currentQuantity = isSubtract?subTractQuantity(currentQuantity):addQuantity(currentQuantity);
        mEditQuantity.setText(String.valueOf(currentQuantity));
    }

    private int addQuantity(int currentQuantity) {
        if((currentQuantity + deltaQuantity >= maxQuantity)){
            currentQuantity = maxQuantity;
            modifyViewClickable(false, false);

        }else{
            currentQuantity += deltaQuantity;
            modifyViewClickable(true, true);

        }
        return currentQuantity;
    }

    private void modifyViewClickable(boolean isEnabled, boolean isSubtract) {
        int defColor = isEnabled ? colorRes : R.color.colorDisabled;
        int color = ContextCompat.getColor(getContext(), defColor);
        if (isSubtract) {
            subtract.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            subtract.setEnabled(isEnabled);
            return;
        }
        add.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        add.setEnabled(isEnabled);
    }

    private int subTractQuantity(int currentQuantity) {
        if((currentQuantity - deltaQuantity) <= minQuantity){
            currentQuantity = minQuantity;
            modifyViewClickable(false, true);

        }else{
            currentQuantity -= deltaQuantity;
            modifyViewClickable(true, false);

        }
        return currentQuantity;
    }

    private void setupView() {
//        if (!isOutlined) {
//            add.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add));
//            subtract.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_subtract));
//        } else {
//            add.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_outline));
//            subtract.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_subtract_outline));
//        }

        if (startQuantity <= minQuantity) {
            currentQuantity = minQuantity;
            subtract.setEnabled(false);
            add.setEnabled(true);
            setupColors(false, true);
        } else if (startQuantity >= maxQuantity) {
            currentQuantity = maxQuantity;
            subtract.setEnabled(true);
            add.setEnabled(false);
            setupColors(true, false);
        } else {
            currentQuantity = startQuantity;
            subtract.setEnabled(true);
            add.setEnabled(true);
            setupColors(true, true);
        }

        mEditQuantity.setText(String.valueOf(currentQuantity));
    }

    private void setupColors(boolean isSubtractEnabled, boolean isAddEnabled) {
        int disabledColor = ContextCompat.getColor(getContext(), R.color.colorDisabled);
        int color = ContextCompat.getColor(getContext(), colorRes);
        if (color != ContextCompat.getColor(getContext(), R.color.black)) {
            add.getDrawable().setColorFilter(isAddEnabled ? color : disabledColor, PorterDuff.Mode.SRC_ATOP);
            mEditQuantity.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            subtract.getDrawable().setColorFilter(isSubtractEnabled ? color : disabledColor, PorterDuff.Mode.SRC_ATOP);
        }
        int textColor = ContextCompat.getColor(getContext(), textColorRes);
        if (textColor != ContextCompat.getColor(getContext(), R.color.black)) {
            mEditQuantity.setTextColor(textColor);
        }
    }
}
