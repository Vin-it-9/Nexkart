$(document).ready(function() {
    $("#buttonAdd2Cart").on("click", function(evt) {
        addToCart();
    });
});

function addToCart() {
    quantity = $("#quantity" + productId).val();

    url = contextPath + "cart/add/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(response) {
        showModalDialog("Shopping Cart", response);
    }).fail(function() {
        showErrorModal("Error while adding product to shopping cart.");
    });
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
