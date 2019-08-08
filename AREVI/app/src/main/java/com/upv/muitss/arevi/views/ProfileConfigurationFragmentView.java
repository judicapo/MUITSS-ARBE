package com.upv.muitss.arevi.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.upv.muitss.arevi.R;
import com.upv.muitss.arevi.helpers.AppState;
import com.upv.muitss.arevi.helpers.Utils;

public class ProfileConfigurationFragmentView extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ProfileConfigurationFragmentView() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProfileConfigurationFragmentView newInstance(int sectionNumber) {
        ProfileConfigurationFragmentView fragment = new ProfileConfigurationFragmentView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_pager_profile_config, container, false);
        AppState appState = AppState.getInstance();

        EditText mEmailTxt = rootView.findViewById(R.id.text_input_email);
        mEmailTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUser().email = Utils.validateInput(mEmailTxt);
            }
        });

        EditText passwordTxt = rootView.findViewById(R.id.text_input_password);
        passwordTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUser().password = Utils.validateInput(passwordTxt);
            }
        });

        EditText fullNameTxt = rootView.findViewById(R.id.text_input_full_name);
        fullNameTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUserInfo().fullName = Utils.validateInput(fullNameTxt);
            }
        });

        EditText otherGenderNameTxt = rootView.findViewById(R.id.text_input_gender);
        otherGenderNameTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUserInfo().genderOther = Utils.validateInput(otherGenderNameTxt);
            }
        });

        EditText occupationTxt = rootView.findViewById(R.id.text_input_occupation);
        occupationTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUserInfo().occupation = Utils.validateInput(occupationTxt);
            }
        });

        EditText visualIllnessTxt = rootView.findViewById(R.id.text_input_visual_illness);
        visualIllnessTxt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                appState.getUserInfo().visualIllness =  Utils.validateInput(visualIllnessTxt);
            }
        });

        Spinner genderSpinner = rootView.findViewById(R.id.input_spinner_gender);
        TextInputLayout otherGenderLayout = rootView.findViewById(R.id.text_input_layout_gender);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otherGenderLayout.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                appState.getUserInfo().gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Spinner ageSpinner = rootView.findViewById(R.id.input_spinner_age);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appState.getUserInfo().age = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner educationSpinner = rootView.findViewById(R.id.input_spinner_education);
        educationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appState.getUserInfo().education = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.input_spinner_education_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_wrap_text_adapter);
        educationSpinner.setAdapter(adapter);

        return rootView;
    }
}