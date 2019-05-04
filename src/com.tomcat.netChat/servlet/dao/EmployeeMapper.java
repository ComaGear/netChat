package com.tomcat.netChat.servlet.dao;

import com.tomcat.netChat.servlet.javaBeans.Employee;

public interface EmployeeMapper {
    public Employee getEmpById(int i);
    public int addEmp();
    public int deleteEmpbyId();
    public int updateEmp();
}
