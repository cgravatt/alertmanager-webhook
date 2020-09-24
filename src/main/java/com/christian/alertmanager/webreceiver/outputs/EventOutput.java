package com.christian.alertmanager.webreceiver.outputs;

import com.google.gson.JsonObject;

public interface EventOutput {

    public void process(JsonObject jsonObject);

}
