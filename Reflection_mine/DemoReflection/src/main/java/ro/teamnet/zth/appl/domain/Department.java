package ro.teamnet.zth.appl.domain;

import ro.teamnet.zth.api.annotation.Column;
import ro.teamnet.zth.api.annotation.Id;
import ro.teamnet.zth.api.annotation.Table;

import java.lang.annotation.Target;

/**
 * Created by user on 7/7/2016.
 */
@Table(name="departments")
public class Department {
    @Id(name="DEPARTMENT_ID")
    private long id;
    @Column(name="DEPARTMENT_NAME")
    private String departmentName;
    @Column(name="LOCATION_ID")
    private Location location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
