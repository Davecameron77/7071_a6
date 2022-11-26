import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import jdk.jshell.spi.ExecutionControl;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

import static com.mongodb.client.model.Filters.eq;

public class MongoManager {

    private final String CONNECTION_STRING = "mongodb+srv://7071a6:Manchester@cluster0.rysojag.mongodb.net/?retryWrites=true&w=majority";
    private final String DB_NAME = "7071a6";
    private final Bson projectionFields = Projections.fields(Projections.include("_id", "Name"));
    private MongoClient client;
    private MongoDatabase db;

    public boolean connect() {
        client = MongoClients.create(CONNECTION_STRING);
        db = client.getDatabase(DB_NAME);

        return !db.getName().isEmpty();
    }

    public void disconnect() {
        client.close();
    }

    public String addEmployee(String name, String street, String city, String managerName, String jobTitle, String certifications, Double salary) {

        ObjectId mgrID = null;
        MongoCollection<Document> employeeCollection = db.getCollection(Table.Employee.tableName());

        if (!managerName.isEmpty()) {
            Document manager = employeeCollection.find(eq(Employee.Name.fieldName(), managerName)).projection(projectionFields).first();
            mgrID = manager != null ? manager.getObjectId("_id") : null;
        }

        Document newEmployeeRecord = new Document("_id", new ObjectId())
                .append(Employee.Name.fieldName(), name != null ? name : "")
                .append(Employee.Street.fieldName(), street != null ? street : "")
                .append(Employee.City.fieldName(), city != null ? city : "")
                .append(Employee.Manager.fieldName(), (mgrID != null ? mgrID : ""))
                .append(Employee.JobTitle.fieldName(), jobTitle != null ? jobTitle : "")
                .append(Employee.Certification.fieldName(), certifications != null ? certifications : "")
                .append(Employee.Salary.fieldName(), String.valueOf(salary));

        return employeeCollection.insertOne(newEmployeeRecord).getInsertedId().toString();
    }

    public String addCustomer(String name, String street, String city, Date birthdate, Blob picture, char gender) {

        MongoCollection<Document> customerCollection = db.getCollection(Table.Customer.tableName());

        Document newCustomerRecord = new Document("_id", new ObjectId())
                .append(Customer.Name.fieldName(), name != null ? name : "")
                .append(Customer.Street.fieldName(), street != null ? street : "")
                .append(Customer.City.fieldName(), city != null ? city : "")
                .append(Customer.Birthdate.fieldName(), birthdate != null ? birthdate.toString() : "")
                .append(Customer.Picture.fieldName(), picture != null ? picture.toString() : "")
                .append(Customer.Gender.fieldName(), gender);

        return customerCollection.insertOne(newCustomerRecord).getInsertedId().toString();
    }

    public String addServiceType(String name, String certificationRequirements, Double rate) {

        MongoCollection<Document> serviceTypeCollection = db.getCollection(Table.ServiceType.tableName());

        Document newServiceTypeRecord = new Document("_id", new ObjectId())
                .append(ServiceType.Name.fieldName(), name != null ? name : "")
                .append(ServiceType.CertificationRequirements.fieldName(), certificationRequirements != null ? certificationRequirements : "")
                .append(ServiceType.Rate.fieldName(), String.valueOf(rate));

        return serviceTypeCollection.insertOne(newServiceTypeRecord).getInsertedId().toString();
    }

    public String addCustomerService(String customerName, String serviceName, int expectedDuration) {

        ObjectId customerID = null;
        ObjectId serviceTypeID = null;
        MongoCollection<Document> customerCollection = db.getCollection(Table.Customer.tableName());
        MongoCollection<Document> serviceCollection = db.getCollection(Table.ServiceType.tableName());
        MongoCollection<Document> customerServiceCollection = db.getCollection(Table.CustomerService.tableName());

        if (!customerName.isEmpty()) {
            Document customer = customerCollection.find(eq(Customer.Name.fieldName(), customerName)).projection(projectionFields).first();
            customerID = customer != null ? customer.getObjectId("_id") : null;
        }

        if (!serviceName.isEmpty()) {
            Document service = serviceCollection.find(eq(ServiceType.Name.fieldName(), serviceName)).projection(projectionFields).first();
            serviceTypeID = service != null ? service.getObjectId("_id") : null;
        }

        Document newCustomerServiceRecord = new Document("_id", new ObjectId())
                .append(CustomerService.Customer.fieldName(), customerID)
                .append(CustomerService.ServiceType.fieldName(), serviceTypeID)
                .append(CustomerService.ExpectedDuration.fieldName(), expectedDuration);

        return customerServiceCollection.insertOne(newCustomerServiceRecord).getInsertedId().toString();
    }

    public String addCustomerServiceSchedule(String customerName, String serviceName, String employeeName, Timestamp startDateTime, int actualDuration, char status) {

        ObjectId customerID = null;
        ObjectId serviceTypeID = null;
        ObjectId employeeID = null;
        MongoCollection<Document> customerCollection = db.getCollection(Table.Customer.tableName());
        MongoCollection<Document> serviceCollection = db.getCollection(Table.ServiceType.tableName());
        MongoCollection<Document> employeeCollection = db.getCollection(Table.Employee.tableName());
        MongoCollection<Document> customerServiceScheduleCollection = db.getCollection(Table.CustomerServiceSchedule.tableName());

        if (!customerName.isEmpty()) {
            Document customer = customerCollection.find(eq(Customer.Name.fieldName(), customerName)).projection(projectionFields).first();
            customerID = customer != null ? customer.getObjectId("_id") : null;
        }

        if (!serviceName.isEmpty()) {
            Document service = serviceCollection.find(eq(ServiceType.Name.fieldName(), customerName)).projection(projectionFields).first();
            serviceTypeID = service != null ? service.getObjectId("_id") : null;
        }

        if (!employeeName.isEmpty()) {
            Document employee = employeeCollection.find(eq(Employee.Name.fieldName(), employeeName)).projection(projectionFields).first();
            employeeID = employee != null ? employee.getObjectId("_id") : null;
        }

        Document newCustomerServiceScheduleRecord = new Document("_id", new ObjectId())
                .append(CustomerServiceSchedule.Customer.fieldName(), customerID)
                .append(CustomerServiceSchedule.ServiceType.fieldName(), serviceTypeID)
                .append(CustomerServiceSchedule.Employee.fieldName(), employeeID)
                .append(CustomerServiceSchedule.StartDateTime.fieldName(), startDateTime)
                .append(CustomerServiceSchedule.ActualDuration.fieldName(), actualDuration)
                .append(CustomerServiceSchedule.Status.fieldName(), status);

        return customerServiceScheduleCollection.insertOne(newCustomerServiceScheduleRecord).getInsertedId().toString();
    }

    public String updateEmployeeName(String oldName, String newName) {
        //todo
        MongoCollection<Document> employeeCollection = db.getCollection(Table.Employee.tableName());
        Document employeeToUpdate = employeeCollection.find(eq(Employee.Name.fieldName(), oldName)).projection(projectionFields).first();

        return null;
    }

    public long dropEmployee(String employeeName) {
        MongoCollection<Document> employeeCollection = db.getCollection(Table.Employee.tableName());
        return employeeCollection.deleteOne(eq(Employee.Name.fieldName(), employeeName)).getDeletedCount();
    }

    public long dropCustomer(String customerName) {
        MongoCollection<Document> customerCollection = db.getCollection(Table.Employee.tableName());
        return customerCollection.deleteOne(eq(Customer.Name.fieldName(), customerName)).getDeletedCount();
    }
}
