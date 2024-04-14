package com.example.enac_project.model;

import com.example.enac_project.utils.PapiState;

public class PapiStateController {

    public static PapiState PapiState;
    private Papi papi;
    private PapiState papiState;

    public PapiStateController(Papi papi) {

        this.papi = papi;
        this.papiState = com.example.enac_project.utils.PapiState.UNKNOWN;
    }



    public void updateLed() {
        int state = papi.getPapiLevel();

        PapiState[] states = {
                com.example.enac_project.utils.PapiState.VERY_LOW,
                com.example.enac_project.utils.PapiState.LOW,
                com.example.enac_project.utils.PapiState.ON_COURSE,
                com.example.enac_project.utils.PapiState.HIGH,
                com.example.enac_project.utils.PapiState.VERY_HIGH
        };
        if (state >= 1 && state <= 5) {
            this.papiState = states[state - 1];
        } else {
            this.papiState = com.example.enac_project.utils.PapiState.UNKNOWN;
        }
    }

    public PapiState getPapiState() {return this.papiState;}
}
