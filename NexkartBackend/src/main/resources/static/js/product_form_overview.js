dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function() {

	//
	$("#shortDescription").richText();
	$("#fullDescription").richText();

	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();
	});

	getCategoriesForNewForm();

});

function getCategoriesForNewForm() {

	catIdField = $("#categoryId");
	editMode = false;

	if (catIdField.length) {
		editMode = true;
	}

	if (!editMode) getCategories();
}

function getCategories() {
	brandId = dropdownBrands.val();
	url = brandModuleURL + "/" + brandId + "/categories";

	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}

function checkUnique(form) {

	let productId = $("#id").val();
	let productName = $("#name").val();
	let csrfValue = $("input[name='_csrf']").val();

	let params = { id: productId, name: productName, _csrf: csrfValue };

	$.post(checkUniqueUrl, params, function(response) {
		if (response === "OK") {
			form.submit();
		} else if (response === "Duplicate") {
			document.getElementById("warningMessage").textContent =
				"There is another product with the name: " + productName;
			document.getElementById("warningModal").classList.remove("hidden");
		} else {
			document.getElementById("warningMessage").textContent =
				"Unknown response from server.";
			document.getElementById("warningModal").classList.remove("hidden");
		}
	}).fail(function() {
		document.getElementById("warningMessage").textContent =
			"Could not connect to the server.";
		document.getElementById("warningModal").classList.remove("hidden");
	});
	return false;
}

function processFormBeforeSubmit() {
	setCountryName();

	removeThousandSeparatorForField(fieldProductCost);
	removeThousandSeparatorForField(fieldSubtotal);
	removeThousandSeparatorForField(fieldShippingCost);
	removeThousandSeparatorForField(fieldTax);
	removeThousandSeparatorForField(fieldTotal);

	$(".cost-input").each(function(e) {
		removeThousandSeparatorForField($(this));
	});

	$(".price-input").each(function(e) {
		removeThousandSeparatorForField($(this));
	});

	$(".subtotal-output").each(function(e) {
		removeThousandSeparatorForField($(this));
	});

	$(".ship-input").each(function(e) {
		removeThousandSeparatorForField($(this));
	});

	return true;
}

function removeThousandSeparatorForField(fieldRef) {
	fieldRef.val(fieldRef.val().replace(",", ""));
}

function setCountryName() {
	selectedCountry = $("#country option:selected");
	countryName = selectedCountry.text();
	$("#countryName").val(countryName);
}