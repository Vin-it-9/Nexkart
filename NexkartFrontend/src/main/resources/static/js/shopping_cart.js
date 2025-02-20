$(document).ready(function() {
    $(".linkMinus").on("click", function(evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });

    $(".linkPlus").on("click", function(evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });
});

function decreaseQuantity(link) {
    productId = link.attr("pid");
    quantityInput = $("#quantity" + productId);
    newQuantity = parseInt(quantityInput.val()) - 1;

    if (newQuantity > 0) {
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    } else {
        showWarningModal('Minimum quantity is 1');
    }
}

function increaseQuantity(link) {
    productId = link.attr("pid");
    quantityInput = $("#quantity" + productId);
    newQuantity = parseInt(quantityInput.val()) + 1;

    if (newQuantity <= 5) {
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    } else {
        showWarningModal('Maximum quantity is 5');
    }
}

function updateQuantity(productId, quantity) {
    url = contextPath + "cart/update/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(updatedSubtotal) {
        updateSubtotal(updatedSubtotal, productId);
        updateTotal();
    }).fail(function() {
        showErrorModal("Error while updating product quantity.");
    });
}

function updateSubtotal(updatedSubtotal, productId) {

    formattedSubtotal = $.number(updatedSubtotal, 2);
    $("#subtotal" + productId).text(formattedSubtotal);

}

function updateTotal() {
    total = 0.0;

    $(".subtotal").each(function(index, element) {
        total += parseFloat(element.innerHTML.replaceAll(",", ""));
    });

    formattedTotal = $.number(total, 2);
    $("#total").text(formattedTotal);
}

function showModalDialog(title, content) {
    const modal = document.createElement('div');
    modal.className = 'fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75 z-50';

    modal.innerHTML = `
      <div class="bg-white rounded-lg shadow-lg max-w-md w-full">
        <div class="border-b px-4 py-2">
          <h2 class="text-xl font-semibold">${title}</h2>
        </div>
        <div class="p-4">
          ${content}
        </div>
        <div class="flex justify-end border-t px-4 py-2">
          <button id="modalCloseButton" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Close
          </button>
        </div>
      </div>
    `;

    document.body.appendChild(modal);

    document.getElementById('modalCloseButton').addEventListener('click', function () {
        document.body.removeChild(modal);
    });
}

function showErrorModal(message) {
    showModalDialog("Error", `<p>${message}</p>`);
}

function showWarningModal(message) {
    showModalDialog("Warning", `<p>${message}</p>`);
}

