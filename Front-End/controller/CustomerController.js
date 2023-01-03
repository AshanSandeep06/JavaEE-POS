let baseUrl = "http://localhost:8080/Back_End/";

//load all customers from the database
getAllCustomers();

//Add Customer
$("#btnCustomer").click(function () {
    let formData = $("#customerForm").serialize();
    $.ajax({
        url: baseUrl + "customer",
        method: "post",
        data: formData,
        success: function (res) {
            alert(res.message);
            getAllCustomers();
        },
        error: function (error) {
            alert(JSON.parse(error.responseText).message);

        }
    });

});

//Delete Customer
$("#btnCusDelete").click(function () {
    let id = $("#txtCustomerID").val();
    $.ajax({
        url: baseUrl + "customer?id=" + id,
        method: "delete",
        success: function (resp) {
            getAllCustomers();
            alert(resp.message);
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
});

//Update Customer
$("#btnUpdate").click(function () {
    let cusId = $("#txtCustomerID").val();
    let cusName = $("#txtCustomerName").val();
    let cusAddress = $("#txtCustomerAddress").val();
    let cusSalary = $("#txtCustomerSalary").val();

    let cusObj = Object.assign({}, customer);
    cusObj['id'] = cusId;
    cusObj["name"] = cusName;
    cusObj['address'] = cusAddress;
    cusObj['salary'] = cusSalary;

    $.ajax({
        url: baseUrl + "customer",
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(cusObj),
        success: function (resp) {
            getAllCustomers();
            alert(resp.message);
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
});

//Get All Customer
$("#btnGetAll").click(function () {
    getAllCustomers();
});

//Get all Customer Function
function getAllCustomers() {
    $("#tblCustomer").empty();
    $.ajax({
        url: baseUrl + "customer",
        success: function (res) {
            for (let c of res.data) {
                let cusID = c.id;
                let cusName = c.name;
                let cusAddress = c.address;
                let cusSalary = c.salary;

                let row = "<tr><td>" + cusID + "</td><td>" + cusName + "</td><td>" + cusAddress + "</td><td>" + cusSalary + "</td></tr>";
                $("#tblCustomer").append(row);
            }
            bindRowClickEvents();
            setTextFieldValues("", "", "", "");
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
}

//Bind events for the table rows function
function bindRowClickEvents() {
    $("#tblCustomer>tr").click(function () {
        let id = $(this).children(":eq(0)").text();
        let name = $(this).children(":eq(1)").text();
        let address = $(this).children(":eq(2)").text();
        let salary = $(this).children(":eq(3)").text();

        //setting table details values to text fields
        setTextFieldValues(id, name, address, salary);
    });
}

//Set text fields values function
function setTextFieldValues(id, name, address, salary) {
    $("#txtCustomerID").val(id);
    $("#txtCustomerName").val(name);
    $("#txtCustomerAddress").val(address);
    $("#txtCustomerSalary").val(salary);
}