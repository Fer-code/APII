package com.example.api;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomView extends AppCompatEditText {
    Drawable mClearButtonImage;

    public CustomView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.clear_button, null);

        setOnTouchListener(new OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart;
                    float clearButtonEnd;
                    AtomicBoolean isClearButtonClicked = new AtomicBoolean(false);
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked.set(true);
                        }
                    } else {
                        clearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked.set(true);
                        }
                        if (isClearButtonClicked.get()) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                /*mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                        R.drawable.clear_button, null);*/
                                showClearButton();
                            }
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                /*mClearButtonImage =
                                        ResourcesCompat.getDrawable(getResources(),
                                                R.drawable.clear_button, null);*/
                                // limpa o texto
                                getText().clear();
                                //esconde o botão
                                hideClearButton();
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }

                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Inicio do texto
                        null,      // Topo do texto
                        null,      // Fim do texto
                        null);     // Abaixo do texto.
    }

    private void showClearButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCompoundDrawablesRelativeWithIntrinsicBounds
                    (null,                      // Inicio do texto
                            null,               // Topo do texto.
                            mClearButtonImage,  // Fim do Texto
                            null);              // Abaixo do texto
        }
    }
}


