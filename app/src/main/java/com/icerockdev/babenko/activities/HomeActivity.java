package com.icerockdev.babenko.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;

public class HomeActivity extends AppCompatActivity {
    private EditText mRequestUrlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        mRequestUrlEditText = (EditText) findViewById(R.id.fieldsRequestUrlEditText);
        Button requestFieldsButton = (Button) findViewById(R.id.getFieldsButton);
        mRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mRequestUrlEditText.setSelection(mRequestUrlEditText.getText().length());
        requestFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataFields();
            }
        });
    }

    private void requestDataFields() {
        String url = mRequestUrlEditText.getText().toString();
        if (!URLUtil.isValidUrl(url)) {
            mRequestUrlEditText.setError(getString(R.string.url_error));
            return;
        }
    }
}
