package com.example.lesson1_5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Constants {
    private Account account;
    private EditText txtName;

    ActivityResultLauncher<Intent> startForResult =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {
                assert result.getData() != null;
                account = result.getData().getParcelableExtra(REQUEST_ACCOUNT);
                populateView();
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = new Account();
        initView();
    }

    private void initView() {
        MaterialButton btnGreetings = findViewById(R.id.btnGreetings);
        MaterialButton btnSettings = findViewById(R.id.btnSettings);
        txtName = findViewById(R.id.textName);
        final TextView txtGreetings = findViewById(R.id.textHello);

        btnGreetings.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String sayHello = getString(R.string.say_hello) + name;
            txtGreetings.setText(sayHello);
        });

        btnSettings.setOnClickListener(v -> {
            Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
            populateAccount();
            runSettings.putExtra(YOUR_ACCOUNT, account);
            startForResult.launch(runSettings);

        });
    }

    private void populateView() {
        txtName.setText(account.getName());
    }

    private void populateAccount() {
        account.setName(txtName.getText().toString());
    }
}