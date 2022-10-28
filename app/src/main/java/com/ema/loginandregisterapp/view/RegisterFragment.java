package com.ema.loginandregisterapp.view;

import static android.content.Context.MODE_PRIVATE;
import static com.ema.loginandregisterapp.Constants.INTENT_FIRSTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_LASTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ema.loginandregisterapp.Constants;
import com.ema.loginandregisterapp.R;
import com.ema.loginandregisterapp.model.Gender;
import com.ema.loginandregisterapp.User;
import com.ema.loginandregisterapp.UserDataStoreImpl;
import com.ema.loginandregisterapp.UserUtil;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RegisterFragment extends BaseFragment {
    SharedPreferences sharedPreferences;
    UserDataStoreImpl userDataStoreImpl;
    SimpleDateFormat simpleDateFormat;

    EditText etUsername;
    EditText etPassword;
    EditText etFirstname;
    EditText etLastname;
    TextView tvBirthdayDate;
    Spinner spinnerGender;
    Button btnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            sharedPreferences = requireActivity().getSharedPreferences(Constants.KEY_MAIN_SHARED_PREFERENCES, MODE_PRIVATE);
            userDataStoreImpl = new UserDataStoreImpl(sharedPreferences);
        }
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        onRestoreInstanceState(savedInstanceState);
        getSendDateFromLoginFragment(etUsername, etPassword);
        setAdapterOfSpinner();
    }

    private void setupUI(View view) {
        etUsername = view.findViewById(R.id.et_username_register);
        etPassword = view.findViewById(R.id.et_pass_register);
        etFirstname = view.findViewById(R.id.et_firstName_register);
        etLastname = view.findViewById(R.id.et_lastName_register);
        tvBirthdayDate = view.findViewById(R.id.et_date_register);
        btnRegister = view.findViewById(R.id.btn_register);
        spinnerGender = view.findViewById(R.id.spinner_gender_register);

        tvBirthdayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateChooser();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                String gender = spinnerGender.getSelectedItem().toString();
                saveUsers(username, pass, firstname, lastname,gender);

                goBackToLoginFragment(view, username, pass, firstname, lastname);
            }
        });


    }

    private void  setAdapterOfSpinner(){
        ArrayAdapter<Gender> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,Gender.values());
        spinnerGender.setAdapter(adapter);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(INTENT_USERNAME, etUsername.getText().toString());
        outState.putString(INTENT_PASSWORD, etPassword.getText().toString());
        outState.putString(INTENT_FIRSTNAME, etFirstname.getText().toString());
        outState.putString(INTENT_LASTNAME, etLastname.getText().toString());
        super.onSaveInstanceState(outState);
    }


    private void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            etUsername.setText(savedInstanceState.getString(INTENT_USERNAME, ""));
        }
        if (savedInstanceState != null) {
            etPassword.setText(savedInstanceState.getString(INTENT_PASSWORD, ""));
        }
        if (savedInstanceState != null) {
            etFirstname.setText(savedInstanceState.getString(INTENT_FIRSTNAME, ""));
        }
        if (savedInstanceState != null) {
            etLastname.setText(savedInstanceState.getString(INTENT_LASTNAME, ""));
        }
    }

    private void saveUsers(String username, String password, String firstname, String lastname,String gender) {
        List<User> currentlySaveUserList = loadUsers();
        User user = new User(username, password, firstname, lastname, getDateFromString(tvBirthdayDate.getText().toString()),Gender.valueOf(gender));
        Log.i("DATEEEEEEE", String.valueOf(getDateFromString(tvBirthdayDate.getText().toString())));

        currentlySaveUserList.add(user);
        String userListToString = UserUtil.userListToString(currentlySaveUserList);

        userDataStoreImpl.addUsers(userListToString);
    }

    private List<User> loadUsers() {
        return userDataStoreImpl.loadUsers();
    }

    /**date picker   **/
    private void dateChooser() {
        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
                .build();

        Date selectedDate = new Date();
        if (tvBirthdayDate.getText() != null && !tvBirthdayDate.getText().toString().isEmpty()) {
            selectedDate = getDateFromString(tvBirthdayDate.getText().toString());
        }

            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                    .datePicker()
                    .setCalendarConstraints(constraints)
                    .setSelection(selectedDate != null ? selectedDate.getTime() : 0)
                    .setTitleText("SELECT BIRTHDAY")
                    .build();

            datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {

                @Override
                public void onPositiveButtonClick(Long selection) {
                    tvBirthdayDate.setText(simpleDateFormat.format(selection));
                }
            });
            datePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");


    }

    private Date getDateFromString(String date) {

            try {
                return simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return null;
    }

}