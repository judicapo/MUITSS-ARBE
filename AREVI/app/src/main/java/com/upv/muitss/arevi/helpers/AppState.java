package com.upv.muitss.arevi.helpers;

import com.upv.muitss.arevi.entities.Assessment;
import com.upv.muitss.arevi.entities.Profile;
import com.upv.muitss.arevi.entities.Round;
import com.upv.muitss.arevi.entities.Task;
import com.upv.muitss.arevi.entities.User;
import com.upv.muitss.arevi.entities.UserInfo;
import com.upv.muitss.arevi.entities.Work;
import com.upv.muitss.arevi.enums.AssessmentType;
import com.upv.muitss.arevi.models.PolyAsset;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AppState {

    private boolean isTracking;
    public boolean getIsTracking() { return this.isTracking; }
    public void setIsTracking(boolean pIsTracking) { this.isTracking = pIsTracking; }

    private boolean isHitting;
    public boolean getIsHitting() { return this.isHitting; }
    public void setIsHitting(boolean pIsHitting) { this.isHitting = pIsHitting; }

    private boolean isLongPress;
    public boolean getIsLongPress() { return this.isLongPress; }
    public void setIsLongPress(boolean pIsFocusing) { this.isLongPress = pIsFocusing; }

    private boolean isCasting;
    public boolean getIsCasting() { return this.isCasting; }
    public void setIsCasting(boolean pIsCasting) { this.isCasting = pIsCasting; }

//    private float nodeAge;
//    public float getNodeAge() { return this.nodeAge; }
//    public void setNodeAge(float pNodeAge) { this.nodeAge = pNodeAge; }

    private User user;
    public User getUser() { return this.user; }
    public void setUser(User pUser) { this.user = pUser; }

    private UserInfo userInfo;
    public UserInfo getUserInfo() { return this.userInfo; }
    public void setUserInfo(UserInfo pUserInfo) { this.userInfo = pUserInfo; }

    private Profile profile;
    public Profile getProfile() { return this.profile; }
    public void setProfile(Profile pProfile) { this.profile = pProfile; }

    private Task task;
    public Task getTask() { return this.task; }
    public void setTask(Task pTask) { this.task = pTask; }
    private Queue<Work> work;
    public boolean workIsEmpty() { return this.work.isEmpty(); }
    public Work pollWork() { return this.work.poll(); }
    public void queueWork(List<Work> pWork) { this.work.addAll(pWork); }
    private Round round;
    public Round getRound() { return this.round; }
    public void setRound(Round pRound) { this.round = pRound; }
    public void addScoreToRound(Work pWork) { this.round.score.add(pWork); }

    private int polyLoadingCount;
    public int getPolyLoadingCount() { return this.polyLoadingCount; }
    public void setPolyLoadingCount(int nCount){ this.polyLoadingCount = nCount; }

    private Queue<PolyAsset> polyQueue;
    public boolean polyQueueIsEmpty() { return this.polyQueue.isEmpty(); }
    public void clearPolyQueue() { this.polyQueue.clear(); }
    public PolyAsset pollPolyAsset() { return this.polyQueue.poll(); }
    public void queuePolyAsset(PolyAsset polyAsset) { this.polyQueue.add(polyAsset); }

    private List<Assessment> assessments;
    public List<Assessment> getAssessments() { return this.assessments; }
    public void setAssessments(List<Assessment> lAssessment) { this.assessments = lAssessment; }
    public void addAssessment(Assessment pAssessment) {
        Assessment ca = getAssessmentByType(pAssessment.type);
        if (ca.type != null && ca.type.equals(pAssessment.type)) {
            ca.id = pAssessment.id;
            ca.name = pAssessment.name;
            ca.type = pAssessment.type;
            ca.content = pAssessment.content;
            ca.completed = pAssessment.completed;
            ca.userId = pAssessment.userId;
            ca.profileId = pAssessment.profileId;
            ca.taskId = pAssessment.taskId;
            ca.roundId = pAssessment.roundId;
        }
        else this.assessments.add(pAssessment);
    }
    public Assessment getAssessmentByType(AssessmentType pAssessmentType) {
        Assessment assessment = this.assessments.stream().filter(a -> a.type.equals(pAssessmentType)).findFirst().orElse(null);
        if (assessment == null) assessment = new Assessment();
        return assessment;
    }

    private boolean fetchingData;
    public boolean isFetchingData() { return fetchingData; }
    public void setFetchingData(boolean fetchingData) { this.fetchingData = fetchingData; }

    private static AppState instance;

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
            instance.initState();
        }
        return instance;
    }

    private void initState(){
        fetchingData = false;
        isTracking = false;
        isHitting = false;
        isLongPress = false;
        user = new User();
        userInfo = new UserInfo();
        profile = new Profile();
        polyQueue = new LinkedList<>();
        task = new Task();
        work = new LinkedList<>(task.work);
        round = new Round();
        polyLoadingCount = 0;
        assessments = new ArrayList<>();
    }

    public void resetAr(){
        polyLoadingCount = 0;
        isTracking = false;
        isHitting = false;
        isLongPress = false;
        polyQueue = new LinkedList<>();
        task = new Task();
        work = new LinkedList<>(task.work);
        round = new Round();
    }

    public void resetAssessment(){
        assessments = new LinkedList<>();
    }
}

