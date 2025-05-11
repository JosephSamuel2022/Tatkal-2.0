package com.tatkal.service.train;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrainDetails {

    public Object trainDetails(String trainId){
        Map<String, String> train = new HashMap<>();
        train.put("TrainName", "SuperFast Express");
        train.put("TrainId", "123");
        return train;
    }
}
