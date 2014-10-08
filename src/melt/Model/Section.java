/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aote Zhou
 */
public class Section {
    private int section_ID;
    private int exam_ID;
    private String section_Name;
    private Date TimeLimit;

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
     * @return the exam_ID
     */
    public int getExam_ID() {
        return exam_ID;
    }

    /**
     * @param exam_ID the exam_ID to set
     */
    public void setExam_ID(int exam_ID) {
        this.exam_ID = exam_ID;
    }

    /**
     * @return the section_Name
     */
    public String getSection_Name() {
        return section_Name;
    }

    /**
     * @param section_Name the section_Name to set
     */
    public void setSection_Name(String section_Name) {
        this.section_Name = section_Name;
    }

    /**
     * @return the TimeLimit
     */
    public Date getTimeLimit() {
        return TimeLimit;
    }

    /**
     * @param TimeLimit the TimeLimit to set
     */
    public void setTimeLimit(Date TimeLimit) {
        this.TimeLimit = TimeLimit;
    }

    
    
}
