package it.polimi.ingsw.controller.simplified_view;

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
     * The information unit the client has to fill.
     */
    private InformationUnit informationUnit;

    /**
     * The set up information the client has to set in the window pattern cards.
     */
    private List<SetUpInformationUnit> informationUnitList;

    public SimplifiedWindowPatternCard(List<SetUpInformationUnit> informationUnitList) {
        setInformationUnitList(informationUnitList);
    }

    public SimplifiedWindowPatternCard(InformationUnit informationUnit) {
        setInformationUnit(informationUnit);
    }


    public InformationUnit getInformationUnit() {
        return informationUnit;
    }

    public void setInformationUnit(InformationUnit informationUnit) {
        this.informationUnit = informationUnit;
    }

    public List<SetUpInformationUnit> getInformationUnitList() {
        return informationUnitList;
    }

    public void setInformationUnitList(List<SetUpInformationUnit> informationUnitList) {
        this.informationUnitList = informationUnitList;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getIdMap() {
        return idMap;
    }
}