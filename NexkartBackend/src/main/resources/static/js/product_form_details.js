$(document).ready(function() {
	$("a[name='linkRemoveDetail']").each(function(index) {
		$(this).click(function() {
			removeDetailSectionByIndex(index);
		});
	});

});

function addNextDetailSection() {

	var allDivDetails = $("[id^='divDetail']");
	var divDetailsCount = allDivDetails.length;

	var htmlDetailSection = `
        <div class="flex items-center space-x-4" id="divDetail${divDetailsCount}">
            <input type="hidden" name="detailIDs" value="0" />
            <div class="w-1/4">
                <label class="block text-gray-700 font-medium">Name:</label>
                <input type="text" name="detailNames"
                       class="w-full border border-gray-300 rounded p-2" maxlength="255" />
            </div>
            <div class="w-1/4">
                <label class="block text-gray-700 font-medium">Value:</label>
                <input type="text" name="detailValues"
                       class="w-full border border-gray-300 rounded p-2" maxlength="255" />
            </div>
            <button type="button"
                    class="bg-red-500 hover:bg-red-600 text-white py-1 px-2 rounded"
                    onclick="removeDetailSectionById('divDetail${divDetailsCount}')">
                <i class="fas fa-times-circle"></i>
            </button>
        </div>
    `;

	$("#divProductDetails").append(htmlDetailSection);

	$(`#divDetail${divDetailsCount} input[name='detailNames']`).focus();
}

function removeDetailSectionById(id) {
	$("#" + id).remove();
}

function removeDetailSectionByIndex(index) {
	$("#divDetail" + index).remove();
}