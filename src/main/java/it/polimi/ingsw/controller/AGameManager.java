package it.polimi.ingsw.controller;

public abstract class AGameManager implements IGameManager {

    private ControllerMaster controllerMaster;

    public ControllerMaster getControllerMaster() {
        return controllerMaster;
    }

    public void setControllerMaster(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }
}