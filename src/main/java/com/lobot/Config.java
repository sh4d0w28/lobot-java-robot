package com.lobot;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.IOException;
import java.util.logging.Level;

@Log(topic = "cmdlog")
public class Config {

    private String targetMac;
    private String targetServiceControlUUIID;
    private String targetCharacteristicControlUUID;

    public Config() {
        Ini ini = new Ini();
        try {
            ini.load(this.getClass().getResourceAsStream("/config.ini"));
        } catch (IOException ex) {
            log.log(Level.ALL, "Error in load INI file", ex);
            return;
        }
        Profile.Section defaultSecttion =  ini.get("bluetooth");
        this.targetMac = defaultSecttion.get("target.mac");
        this.targetServiceControlUUIID = defaultSecttion.get("target.service.uuid");
        this.targetCharacteristicControlUUID = defaultSecttion.get("target.characteristic.uuid");
    }

    public String getTargetMac() {
        return targetMac;
    }

    public String getTargetServiceControlUUIID() {
        return targetServiceControlUUIID;
    }

    public String getTargetCharacteristicControlUUID() {
        return targetCharacteristicControlUUID;
    }
}
