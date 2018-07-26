package com.ae.andriod.bakingapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ae.andriod.bakingapp.model.Step;

import java.util.List;

public class StepViewModel extends AndroidViewModel{

    private Step mStep;
    private final MutableLiveData<List<Step>> mLiveStepsData;


    public StepViewModel(@NonNull Application application) {
        super(application);
        mLiveStepsData = new MutableLiveData<>();
    }

    public void setStep(Step step){
        mStep = step;
    }

    public void setLiveStepsData(List<Step> steps){
        mLiveStepsData.setValue(steps);
    }

    public MutableLiveData<List<Step>> getLiveStepsData(){
        return mLiveStepsData;
    }

    public int getId(){
       return mStep.getId();
    }

    public String getShortDescription(){
        return mStep.getShortDescription();
    }

    public String getDescription(){
        return mStep.getDescription();
    }

    public String getVideoUrl(){
        return mStep.getVideoURL();
    }

    public String getThumbnailURL(){
        return mStep.getThumbnailURL();
    }
}
