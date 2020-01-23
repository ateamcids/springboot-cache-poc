package com.telecom.ateam.minipoc.core.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telecom.ateam.minipoc.models.DashboardModel;

import java.util.List;

public interface ITestService {
    List<DashboardModel> request() throws JsonProcessingException, InterruptedException;

}
