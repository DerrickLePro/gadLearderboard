package com.hdvision.gadleaderboard;

import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hdvision.gadleaderboard.model.SubmitBean;
import com.hdvision.gadleaderboard.utils.ConfirmationDialog;
import com.hdvision.gadleaderboard.utils.TextValidator;

import java.util.regex.Pattern;

public class SubmitActivity extends AppCompatActivity {

    private static final String TAG = SubmitActivity.class.getSimpleName();


    private EditText mEdFirstName;
    private EditText mEdLastName;
    private EditText mEdEmail;
    private EditText mEdProjectLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> onBackPressed());

        Button submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(view -> actionSubmit());

        mEdFirstName = findViewById(R.id.editTextFirstName);
        mEdLastName = findViewById(R.id.editTextLastName);
        mEdEmail = findViewById(R.id.editTextEmail);
        mEdProjectLink = findViewById(R.id.editTextProjectLink);

        mEdLastName.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitActivity.this, "Last name required", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEdEmail.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitActivity.this, "Email required", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEdProjectLink.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitActivity.this, "Your project link in Github required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void actionSubmit() {

        String lastName = mEdLastName.getText().toString();
        String firstName = mEdFirstName.getText().toString();
        String email = mEdEmail.getText().toString();
        String projectLink = mEdProjectLink.getText().toString();

        SubmitBean bean = new SubmitBean();
        bean.setLastName(lastName);
        bean.setFirstName(firstName);
        bean.setBusinessEmail(email);
        bean.setProjectLink(projectLink);

        if (bean.getFirstName().isEmpty()){
            Toast.makeText(SubmitActivity.this, "First name required", Toast.LENGTH_LONG).show();
            return;
        }
        if (bean.getLastName().isEmpty()){
            Toast.makeText(SubmitActivity.this, "Last name required", Toast.LENGTH_LONG).show();
            return;
        }
        if (bean.getBusinessEmail().isEmpty()){
            Toast.makeText(SubmitActivity.this, "Email required", Toast.LENGTH_LONG).show();
            return;
        }else if (!isValidEmail(bean.getBusinessEmail())){
            Toast.makeText(SubmitActivity.this, "Email address no valid", Toast.LENGTH_LONG).show();
            return;
        }
        if (bean.getProjectLink().isEmpty()){
            Toast.makeText(SubmitActivity.this, "Project link required", Toast.LENGTH_LONG).show();
            return;
        }
        ConfirmationDialog dialog = new ConfirmationDialog(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.field_bean), bean);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_confirm_submit));

        Log.d(TAG, bean.toString());

    }

    private boolean isValidEmail(String email) {
        Pattern validator = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return validator.matcher(email).find();
    }


}