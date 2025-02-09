
$(document).ready(function() {
	$("a[name='linkRemoveDetail']").each(function(index) {
		$(this).click(function() {
			removeDetailSectionByIndex(index);
		});
	});

});

function removeDetailSectionByIndex(index) {
	$("#divDetail" + index).remove();
}

function addNextDetailSection() {
	allDivDetails = $("[id^='divDetail']");
	divDetailsCount = allDivDetails.length;


	htmlDetailSection = `
  <div class="flex items-center space-x-4 mb-5" id="divDetail${divDetailsCount}">
   <input type="hidden" name="detailIDs" value="0" >
    <label class="text-gray-700 font-medium">Name:</label>
    <input
      type="text"
      name="detailNames"
      maxlength="255"
      class="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-1/4"
    />
    <label class="text-gray-700 font-medium">Value:</label>
    <input
      type="text"
      name="detailValues"
      maxlength="255"
      class="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 w-1/4"
    />
  </div>
`;


	$("#divProductDetails").append(htmlDetailSection);

	previousDivDetailSection = allDivDetails.last();
	previousDivDetailID = previousDivDetailSection.attr("id");

	htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark"
			href="javascript:removeDetailSectionById('${previousDivDetailID}')"
			title="Remove this detail"></a>
	`;

	previousDivDetailSection.append(htmlLinkRemove);

	$("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
	$("#" + id).remove();
}