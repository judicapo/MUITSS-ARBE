package com.upv.muitss.arevi.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.upv.muitss.arevi.R;
import com.upv.muitss.arevi.entities.Profile;
import com.upv.muitss.arevi.helpers.AppState;
import com.upv.muitss.arevi.helpers.Utils;
import com.upv.muitss.arevi.logic.web.implementations.AREVIRepository;
import com.upv.muitss.arevi.logic.web.interfaces.ActivityMessage;

public class ARConfigurationFragmentView extends Fragment implements ActivityMessage {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;

    public ARConfigurationFragmentView() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ARConfigurationFragmentView newInstance(int sectionNumber) {
        ARConfigurationFragmentView fragment = new ARConfigurationFragmentView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_pager_ar_config, container, false);

        boolean mode = Utils.getSavedMode();
        boolean userLogin = Utils.getLogIn().isValidState();

        if (userLogin && !mode) {
            Utils.popProgressDialog(getActivity(), "Loading...");
            AREVIRepository.getInstance().getApiProfile(Utils.getUserId(), this);
        }
        else {
            loadData();
        }

        return rootView;
    }

    private void loadData(){
        Switch toggle = rootView.findViewById(R.id.use_cardboard_switch);
        toggle.setChecked(Utils.getSavedMode());
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppState.getInstance().getProfile().configuration.setUseGoogleCardboard(true);
                Utils.saveMode(true);
            } else {
                AppState.getInstance().getProfile().configuration.setUseGoogleCardboard(false);
                Utils.saveMode(false);
            }
        });
    }

    @Override
    public <T> void onResponse(T response) {
        if (response instanceof Profile && ((Profile) response).configuration != null){
            Utils.saveMode(((Profile) response).configuration.getUseGoogleCardboardBoolean());
            loadData();
        }
    }
}