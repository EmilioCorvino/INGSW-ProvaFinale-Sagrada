package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.ControllerMaster;

public abstract class AGameManager {

    private ControllerMaster controllerMaster;

    public ControllerMaster getControllerMaster() {
        return controllerMaster;
    }

    public void setControllerMaster(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }
}
