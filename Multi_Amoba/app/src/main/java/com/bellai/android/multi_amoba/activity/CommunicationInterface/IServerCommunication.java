package com.bellai.android.multi_amoba.activity.CommunicationInterface;

import com.bellai.android.multi_amoba.activity.NewServerGameActivity;

import java.util.List;

/**
 * Created by adam.bellai on 2017. 02. 02..
 */

public interface IServerCommunication {
    List<Object> getOutStreams();
    void setNewServerGameActivity(NewServerGameActivity newServerGameActivity);
}
