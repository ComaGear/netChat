package com.tomcat.netChat.dao;

import com.tomcat.netChat.javaBeans.Employee;

public interface EmployeeMapper {
    public Employee getEmpById(int i);
    public int addEmp();
    public int deleteEmpbyId();
    public int updateEmp();
}
