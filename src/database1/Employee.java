package database1;

class Employee {
    private int empno;
    private String ename;
    private String job;
    private int manager;
    private String hiredate;
    private double sal;
    private double comm;
    private int deptno;

    public Employee(int empno, String ename, String job, int manager, String hiredate, double sal, double comm, int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.manager = manager;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }
}