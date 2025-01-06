package abeshutt.staracademy.config;

import com.google.gson.annotations.Expose;

public class NPCConfig extends FileConfig {

    @Expose private String partnerNpcName;

    @Override
    public String getPath() {
        return "npc";
    }

    public String getPartnerNPCName() {
        return this.partnerNpcName;
    }

    @Override
    protected void reset() {
        this.partnerNpcName = "Professor";
    }

}
