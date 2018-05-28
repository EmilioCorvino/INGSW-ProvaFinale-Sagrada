package it.polimi.ingsw.controller.simplified_view;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class SimplifiedWindowPatternCard implements Serializable {

    /**
     *
     */
    private InformationUnit informationUnit;

    /**
     *
     */
    private List<InformationUnit> informationUnitList;

    public SimplifiedWindowPatternCard(InformationUnit informationUnit) {
        setInformationUnit(informationUnit);
    }

    public InformationUnit getInformationUnit() {
        return informationUnit;
    }

    public void setInformationUnit(InformationUnit informationUnit) {
        this.informationUnit = informationUnit;
    }
}
