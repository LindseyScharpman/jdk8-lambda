package lambda;

import java.util.*;

public class OptionalTest {
    public static void main(String[] args) {

        Optional<String> optional = Optional.of( "go..." );

        System.out.println( optional.isPresent() + ":" + optional.get() );
        optional.ifPresent( System.out::println );

        // recommand usage
        optional = Optional.empty();
        System.out.println( optional.orElse( "default" ) );
        System.out.println( optional.orElseGet( () -> "gg" ) );


        Employee e1 = new Employee( "A1" );
        Employee e2 = new Employee( "A2" );
        Employee e3 = new Employee( "B1" );
        Employee e4 = new Employee( "B2" );
        List<Employee> employee = Arrays.asList( e1, e2, e3, e4 );
        //Company company = new Company( nul );
        Company company = new Company( employee );

        Optional<Company> companyOptional = Optional.ofNullable( company );
        System.out.println( companyOptional
                .map( Company::getEmployees )
                .orElse( Collections.emptyList() ) );
    }

    static class Employee {
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Company {
        private List<Employee> employees;

        public List<Employee> getEmployees() {
            return employees;
        }

        public Company(List<Employee> employees) {
            this.employees = employees;
        }
    }
}


