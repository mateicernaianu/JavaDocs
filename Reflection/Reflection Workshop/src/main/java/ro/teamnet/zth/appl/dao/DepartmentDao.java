package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Department;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDao {

    private Department department;
    private EntityManagerImpl ent;

    public DepartmentDao(Department department)
    {
        this.department = department ;
        ent = new EntityManagerImpl();
    }

    public Department findById() {
        return  ent.findById(department.getClass(), Long.valueOf(160));
    }

    public List<Department> findAll() throws NoSuchFieldException, SQLException, ClassNotFoundException {
        return ent.findAll(Department.class);
    }

    public Department update() throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        return ent.update(department);
    }

    public void delete(){
        ent.delete(department);
    }

    public List<Department> findByParams(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("DEPARTMENT_ID",270);
        return ent.findByParams(Department.class, map);
    }

    public Object insert() throws SQLException, ClassNotFoundException {
        department.setDepartmentName("HR");
        department.setId(101);
        department.setLocations(1000);

        EntityManager entityManager=new EntityManagerImpl();
        return ent.insert(department);
    }

}
