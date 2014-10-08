/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.Model;

/**
 *
 * @author Aote Zhou
 */
public class SubSection {
    private int subSection_ID;
    private int section_ID;
    private String subSection_Name;

    /**
     * @return the SubSection_ID
     */
    public int getSubSection_ID() {
        return subSection_ID;
    }

    /**
     * @param SubSection_ID the SubSection_ID to set
     */
    public void setSubSection_ID(int SubSection_ID) {
        this.subSection_ID = SubSection_ID;
    }

    /**
     * @return the section_ID
     */
    public int getSection_ID() {
        return section_ID;
    }

    /**
     * @param section_ID the section_ID to set
     */
    public void setSection_ID(int section_ID) {
        this.section_ID = section_ID;
    }

    /**
     * @return the SubSection_Name
     */
    public String getSubSection_Name() {
        return subSection_Name;
    }

    /**
     * @param SubSection_Name the SubSection_Name to set
     */
    public void setSubSection_Name(String SubSection_Name) {
        this.subSection_Name = SubSection_Name;
    }
}
