import java.sql.Date;

public class driver {
    public static void main(String[] args) {
        MongoManager manager = new MongoManager();
        String resultValue = "";

        if (manager.connect()) {
            // Add some employee
            System.out.println();
            System.out.println("*********************************************");
            System.out.println("Adding customers");
            resultValue = manager.addEmployee("Emp One", "123 Anywhere Street", "Vancouver", "", "Manager", "All", 80.0);
            System.out.println("Added employee record with ID " + resultValue);
            resultValue = manager.addEmployee("Emp Two", "123 Anywhere Street", "Vancouver", "Emp One", "Nurse", "nursing", 40.0);
            System.out.println("Added employee record with ID " + resultValue);
            resultValue = manager.addEmployee("Emp Three", "123 Anywhere Street", "Vancouver", "Emp One", "Custodian", "custodial", 25.0);
            System.out.println("Added employee record with ID " + resultValue);

            // Add some customer
            System.out.println();
            System.out.println("*********************************************");
            System.out.println("Adding customers");

            resultValue = manager.addCustomer("John Smith", "123 Anywhere Street", "Vancouver", new Date(01, 01, 1970), null, 'M');
            System.out.println("Added employee record with ID " + resultValue);
            resultValue = manager.addCustomer("Jane Doe", "123 Anywhere Street", "Vancouver", new Date(01, 01, 1970), null, 'M');
            System.out.println("Added employee record with ID " + resultValue);
            resultValue = manager.addCustomer("Jesse Pinkman", "123 Anywhere Street", "Vancouver", new Date(01, 01, 1970), null, 'M');
            System.out.println("Added employee record with ID " + resultValue);

            // Add some service type
            System.out.println();
            System.out.println("*********************************************");
            System.out.println("Adding Service Types");

            resultValue = manager.addServiceType("Food Prep", "Foodsafe", 22.0);
            System.out.println("Added service food prep record with ID " + resultValue);
            resultValue = manager.addServiceType("Nursing", "LPN", 44.0);
            System.out.println("Added service nursing record with ID " + resultValue);
            resultValue = manager.addServiceType("custodial", "", 15.0);
            System.out.println("Added service custodial record with ID " + resultValue);

            // Add some customer service

            // Update an employee
            System.out.println();
            System.out.println("*********************************************");
            System.out.println("Updating Emp Three");
            double modifiedRecords = manager.updateEmployeeName("Emp Three", "DeleteMe");
            System.out.println("Modified " + modifiedRecords + " records");

            // Drop an employee
            System.out.println();
            System.out.println("*********************************************");
            System.out.println("Dropping Emp Three");
            long deletedRecords = manager.dropEmployee("DeleteMe");
            System.out.println("Deleted " + deletedRecords + " records");

            System.out.println("*********************************************");
            System.out.println("Transactions Complete, disconnecting");
            manager.disconnect();
            System.out.println("*********************************************");
            System.out.println("Disconnected");
        }
    }
}
