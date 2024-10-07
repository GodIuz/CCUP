package com.droidgeniuslabs.ccup.ui.isologismos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> editTextPagio = new MutableLiveData<>();
    private final MutableLiveData<String> editTextApothema = new MutableLiveData<>();
    private final MutableLiveData<String> editTextApaitiseis = new MutableLiveData<>();
    private final MutableLiveData<String> editTextDiathesima = new MutableLiveData<>();

    private final MutableLiveData<String> editTextKefalaia = new MutableLiveData<>();
    private final MutableLiveData<String> editTextProvlepseis = new MutableLiveData<>();
    private final MutableLiveData<String> editTextM_Ypo = new MutableLiveData<>();
    private final MutableLiveData<String> editTextText4 = new MutableLiveData<>();

    // Getters and setters for each field
    public void setEditTextPagio(String value) { editTextPagio.setValue(value); }
    public LiveData<String> getEditTextPagio() { return editTextPagio; }

    public void setEditTextApothema(String value) { editTextApothema.setValue(value); }
    public LiveData<String> getEditTextApothema() { return editTextApothema; }

    public void setEditTextApaitiseis(String value) { editTextApaitiseis.setValue(value); }
    public LiveData<String> getEditTextApaitiseis() { return editTextApaitiseis; }

    public void setEditTextDiathesima(String value) { editTextDiathesima.setValue(value); }
    public LiveData<String> getEditTextDiathesima() { return editTextDiathesima; }

    public void setEditTextKefalaia(String value) { editTextKefalaia.setValue(value); }
    public LiveData<String> getEditTextKefalaia() { return editTextKefalaia; }

    public void setEditTextProvlepseis(String value) { editTextProvlepseis.setValue(value); }
    public LiveData<String> getEditTextProvlepseis() { return editTextProvlepseis; }

    public void setEditTextM_Ypo(String value) { editTextM_Ypo.setValue(value); }
    public LiveData<String> getEditTextM_Ypo() { return editTextM_Ypo; }

    public void setEditTextB_Ypo(String value) { editTextText4.setValue(value); }
    public LiveData<String> getEditTextB_Ypo() { return editTextText4; }
}

