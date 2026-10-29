package ma.projet.classes.projet;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeTachePK implements Serializable {

    @Column(name = "employe_id")
    private int employeId;

    @Column(name = "tache_id")
    private int tacheId;

    public EmployeTachePK() {
    }

    public EmployeTachePK(int empId, int tId) {
        this.employeId = empId;
        this.tacheId = tId;
    }

    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int empId) {
        this.employeId = empId;
    }

    public int getTacheId() {
        return tacheId;
    }

    public void setTacheId(int tId) {
        this.tacheId = tId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return employeId == that.employeId && tacheId == that.tacheId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeId, tacheId);
    }
}
