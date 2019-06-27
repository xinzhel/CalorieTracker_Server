/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author home
 */
@Entity
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId")
    , @NamedQuery(name = "Report.findByDate", query = "SELECT r FROM Report r WHERE r.date = :date")
    , @NamedQuery(name = "Report.findByCaloriesConsumed", query = "SELECT r FROM Report r WHERE r.caloriesConsumed = :caloriesConsumed")
    , @NamedQuery(name = "Report.findByCaloriesBurned", query = "SELECT r FROM Report r WHERE r.caloriesBurned = :caloriesBurned")
    , @NamedQuery(name = "Report.findBySteps", query = "SELECT r FROM Report r WHERE r.steps = :steps")
    , @NamedQuery(name = "Report.findByCalorieGoal", query = "SELECT r FROM Report r WHERE r.calorieGoal = :calorieGoal")
    , @NamedQuery(name = "Report.findByUserId", query = "SELECT r FROM Report r WHERE r.userId.userId = :userId")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REPORT_ID")
    private Integer reportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIES_CONSUMED")
    private int caloriesConsumed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIES_BURNED")
    private int caloriesBurned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STEPS")
    private int steps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIE_GOAL")
    private int calorieGoal;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private AppUser userId;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Report(Integer reportId, Date date, int caloriesConsumed, int caloriesBurned, int steps, int calorieGoal) {
        this.reportId = reportId;
        this.date = date;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
        this.steps = steps;
        this.calorieGoal = calorieGoal;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public AppUser getUserId() {
        return userId;
    }

    public void setUserId(AppUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Report[ reportId=" + reportId + " ]";
    }
    
}
