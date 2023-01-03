let baseUrl = "http://localhost:8080/Back_End/";

//load all items from the database
getAllItems();

//Add Item
$("#btnItem").click(function () {
    let formData = $("#itemForm").serialize();
    $.ajax({
        url: baseUrl + "item",
        method: "post",
        data: formData,
        dataType: "json",
        success: function (res) {
            alert(res.message);
            getAllItems();
        },
        error: function (error) {
            alert(JSON.parse(error.responseText).message);

        }
    });

});

//Delete Item
$("#btnItemDelete").click(function () {
    let code = $("#itemCode").val();
    $.ajax({
        url: baseUrl + "item?code=" + code,
        method: "delete",
        success: function (resp) {
            getAllItems();
            alert(resp.message);
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
});

//Update Item
$("#btnItemUpdate").click(function () {
    let code = $("#itemCode").val();
    let name = $("#itemName").val();
    let qty = $("#itemQty").val();
    let price = $("#itemPrice").val();

    let itemObj = Object.assign({}, item);
    itemObj.code = code;
    itemObj.description = name;
    itemObj.qtyOnHand = qty;
    itemObj.unitPrice = price;

    $.ajax({
        url: baseUrl + "item",
        method: "put",
        contentType: "application/json",
        data: JSON.stringify(itemObj),
        dataType: "json",
        success: function (resp) {
            getAllItems();
            alert(resp.message);
        },
        error: function (error) {
            let message = JSON.parse(error.responseText).message;
            alert(message);
        }
    });
});

//Get All Items
$("#btnGetAll").click(function () {
    getAllItems();
});

//Get all Items Function
function getAllItems() {
    $("#tblItem").empty();
    $.ajax({
        url: baseUrl + "item",
        success: function (res) {
            for (let c of res.data) {
                let code = c.code;
                let description = c.description;
                let qtyOnHand = c.qtyOnHand;
                let unitPrice = c.unitPrice;

                let row = "<tr><td>" + code + "</td><td>" + description + "</td><td>" + qtyOnHand + "</td><td>" + unitPrice + "</td></tr>";
                $("#tblItem").append(row);
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
    $("#tblItem>tr").click(function () {
        let code = $(this).children(":eq(0)").text();
        let name = $(this).children(":eq(1)").text();
        let qty = $(this).children(":eq(2)").text();
        let price = $(this).children(":eq(3)").text();

        //setting table details values to text fields
        setTextFieldValues(code, name, qty, price);
    });
}

//Set text fields values function
function setTextFieldValues(code, name, qty, price) {
    $('#itemCode').val(code);
    $('#itemName').val(name);
    $('#itemQty').val(qty);
    $('#itemPrice').val(price);
}