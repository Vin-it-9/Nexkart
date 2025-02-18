var buttonLoad;
var dropDownCountry;
var buttonAddCountry;
var buttonUpdateCountry;
var buttonDeleteCountry;
var labelCountryName;
var fieldCountryName;
var fieldCountryCode;

$(document).ready(function() {
    buttonLoad = $("#buttonLoadCountries");
    dropDownCountry = $("#dropDownCountries");
    buttonAddCountry = $("#buttonAddCountry");
    buttonUpdateCountry = $("#buttonUpdateCountry");
    buttonDeleteCountry = $("#buttonDeleteCountry");
    labelCountryName = $("#labelCountryName");
    fieldCountryName = $("#fieldCountryName");
    fieldCountryCode = $("#fieldCountryCode");

    buttonLoad.click(function() {
        loadCountries();
    });

    dropDownCountry.on("change", function() {
        changeFormStateToSelectedCountry();
    });

    // Use .text() to check the button label instead of .val()
    buttonAddCountry.click(function() {
        if (buttonAddCountry.text().trim() === "Add") {
            addCountry();
        } else {
            changeFormStateToNew();
        }
    });

    buttonUpdateCountry.click(function() {
        updateCountry();
    });

    buttonDeleteCountry.click(function() {
        deleteCountry();
    });
});

function loadCountries() {
    var url = contextPath + "countries/list";
    $.get(url, function(responseJSON) {
        dropDownCountry.empty();
        $.each(responseJSON, function(index, country) {
            var optionValue = country.id + "-" + country.code;
            $("<option>").val(optionValue).text(country.name).appendTo(dropDownCountry);
        });
    }).done(function() {
        buttonLoad.text("Refresh Country List");
        showToastMessage("All countries have been loaded");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function addCountry() {
    var url = contextPath + "countries/save";
    var countryName = fieldCountryName.val();
    var countryCode = fieldCountryCode.val();
    var jsonData = { name: countryName, code: countryCode };

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(countryId) {
        // Call the correct callback with the parameters in the proper order
        selectNewlyAddedCountry(countryId, countryCode, countryName);
        showToastMessage("The new country has been added");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function selectNewlyAddedCountry(countryId, countryCode, countryName) {
    var optionValue = countryId + "-" + countryCode;
    $("<option>")
        .val(optionValue)
        .text(countryName)
        .appendTo(dropDownCountry);
    $("#dropDownCountries option[value='" + optionValue + "']").prop("selected", true);
    fieldCountryCode.val("");
    fieldCountryName.val("").focus();
}

function updateCountry() {
    var url = contextPath + "countries/save";
    var countryName = fieldCountryName.val();
    var countryCode = fieldCountryCode.val();
    var countryId = dropDownCountry.val().split("-")[0];
    var jsonData = { id: countryId, name: countryName, code: countryCode };

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(updatedCountryId) {
        $("#dropDownCountries option:selected").val(updatedCountryId + "-" + countryCode);
        $("#dropDownCountries option:selected").text(countryName);
        showToastMessage("The country has been updated");
        changeFormStateToNew();
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function deleteCountry() {
    var optionValue = dropDownCountry.val();
    var countryId = optionValue.split("-")[0];
    var url = contextPath + "countries/delete/" + countryId;

    $.get(url, function() {
        $("#dropDownCountries option[value='" + optionValue + "']").remove();
        changeFormStateToNew();
    }).done(function() {
        showToastMessage("The country has been deleted");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function changeFormStateToNew() {
    buttonAddCountry.text("Add");  // Update text instead of value
    labelCountryName.text("Country Name:");
    buttonUpdateCountry.prop("disabled", true);
    buttonDeleteCountry.prop("disabled", true);
    fieldCountryCode.val("");
    fieldCountryName.val("").focus();
}

function changeFormStateToSelectedCountry() {
    buttonAddCountry.text("New");  // Update text instead of value
    buttonUpdateCountry.prop("disabled", false);
    buttonDeleteCountry.prop("disabled", false);
    labelCountryName.text("Selected Country:");
    var selectedCountryName = $("#dropDownCountries option:selected").text();
    fieldCountryName.val(selectedCountryName);
    var countryCode = dropDownCountry.val().split("-")[1];
    fieldCountryCode.val(countryCode);
}

function showToastMessage(message) {
    $("#toastMessage").text(message);
    var toast = $(".toast");
    toast.removeClass("hidden");
    setTimeout(function() {
        toast.addClass("hidden");
    }, 3000);
}
