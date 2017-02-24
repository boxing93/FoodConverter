var counter = 1;

var limit = 15;

function addInput(divName){

    if (counter == limit)  {

        alert("You have reached the limit of adding " + counter + " inputs");

    }

    else {

        var newdiv = document.createElement('div');

        newdiv.innerHTML =
            "<div class='form-group'>" +
                    "<label class='col-sm-4 control-label' for='product'>Product</label>" +
                "<div class='col-sm-4'>" +
                    "<input type='text' required='required' class='form-control' id='product' name='product' placeholder='Product'/>" +
                "</div>" +
            "</div>" +
            "<div class='form-group'>" +
                    "<label class='col-sm-4 control-label' for='price'>Price</label>" +
                "<div class='col-sm-4'>" +
                    "<input type='number' required='required' step='0.01' class='form-control' id='price' name='price' placeholder='Price for product in kilos'/>" +
                "</div>" +
            "</div>";

        document.getElementById(divName).appendChild(newdiv);

        counter++;

    }

}