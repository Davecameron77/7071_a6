enum Table {
    Customer("Customer"),
    Employee("Employee"),
    CustomerService("CustomerService"),
    CustomerServiceSchedule("CustomerServiceSchedule"),
    ServiceType("ServiceType" );

    private final String tableName;

    Table(String value) {
        this.tableName = value;
    }
    public String tableName() { return tableName; }
}

enum Customer {
    Name("Name"),
    Street("Street"),
    City("City"),
    Birthdate("Birthdate"),
    Picture("Picture"),
    Gender("Gender");

    private final String fieldName;

    Customer(String fieldName) { this.fieldName = fieldName; }
    String fieldName() { return fieldName; }
};

enum Employee {
    Name("Name"),
    Street("Street"),
    City("City"),
    Manager("ManagerId"),
    JobTitle("JobTitle"),
    Certification("Certification"),
    Salary("Salary");

    private final String fieldName;

    Employee(String fieldName) { this.fieldName = fieldName; }
    String fieldName() { return fieldName; }
};

enum ServiceType {
    Name("Name"),
    CertificationRequirements("CertificationRequirements"),
    Rate("Rate");

    private final String fieldName;

    ServiceType(String fieldName) { this.fieldName = fieldName; }
    String fieldName() { return fieldName; }
}

enum CustomerService {
    Customer("CustomerId"),
    ServiceType("ServiceTypeId"),
    ExpectedDuration("ExpectedDuration");

    private final String fieldName;

    CustomerService(String fieldName) { this.fieldName = fieldName; }
    String fieldName() { return fieldName; }
}

enum CustomerServiceSchedule {
    Customer("CustomerId"),
    ServiceType("ServiceTypeId"),
    Employee("EmployeeId"),
    StartDateTime("StartDateTime"),
    ActualDuration("ActualDuration"),
    Status("Status");

    private final String fieldName;

    CustomerServiceSchedule(String fieldName) { this.fieldName = fieldName; }
    String fieldName() { return fieldName; }
}