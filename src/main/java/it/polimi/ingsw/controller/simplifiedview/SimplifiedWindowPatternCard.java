package it.polimi.ingsw.controller.simplifiedview;

import java.io.Serializable;
import java.util.List;

/**
 * The
 */
public class SimplifiedWindowPatternCard implements Serializable {

    /**
     * Identification number of the map.
     */
    private int idMap;

    /**
     * Number of favor token associated to this map
     */
    private int difficulty;

    /**
     * The set up information the client has to set in the window pattern cards.
     */
    private List<SetUpInformationUnit> informationUnitList;

    public SimplifiedWindowPatternCard(List<SetUpInformationUnit> informationUnitList) {
        setInformationUnitList(informationUnitList);
    }

    public List<SetUpInformationUnit> getInformationUnitList() {
        return informationUnitList;
    }

    private void setInformationUnitList(List<SetUpInformationUnit> informationUnitList) {
        this.informationUnitList = informationUnitList;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}