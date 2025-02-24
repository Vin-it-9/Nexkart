var trackRecordCount;

$(document).ready(function() {
    trackRecordCount = $(".hiddenTrackId").length;

    $("#trackList").on("click", ".linkRemoveTrack", function(e) {
        e.preventDefault();
        deleteTrack($(this));
        updateTrackCountNumbers();
    });

    $("#track").on("click", "#linkAddTrack", function(e) {
        e.preventDefault();
        addNewTrackRecord();
    });

    $("#trackList").on("change", ".dropDownStatus", function(e) {
        var dropDownList = $(this);
        var rowNumber = dropDownList.attr("rowNumber");
        var selectedOption = $("option:selected", dropDownList);
        var defaultNote = selectedOption.attr("defaultDescription");
        $("#trackNote" + rowNumber).text(defaultNote);
    });
});

function deleteTrack(link) {
    var rowNumber = link.attr('rowNumber');
    $("#rowTrack" + rowNumber).remove();
    $("#emptyLine" + rowNumber).remove();
}

function updateTrackCountNumbers() {
    $(".divCountTrack").each(function (index, element) {
        element.innerHTML = "" + (index + 1);
    });
}

function addNewTrackRecord() {
    var htmlCode = generateTrackCode();
    $("#trackList").append(htmlCode);
}

function generateTrackCode() {
    var nextCount = trackRecordCount + 1;
    trackRecordCount++;
    var rowId = "rowTrack" + nextCount;
    var emptyLineId = "emptyLine" + nextCount;
    var trackNoteId = "trackNote" + nextCount;
    var currentDateTime = formatCurrentDateTime();

    var htmlCode = `
			<div class="flex flex-col md:flex-row items-start border rounded p-2" id="${rowId}">
				<input type="hidden" name="trackId" value="0" class="hiddenTrackId" />
				<div class="w-full md:w-1/6 flex flex-col items-center">
					<div class="divCountTrack text-lg font-bold">${nextCount}</div>
					<div class="mt-1">
                        <a class="fas fa-trash text-gray-600 hover:text-red-500 linkRemoveTrack" href="" rowNumber="${nextCount}"></a>
                    </div>					
				</div>				
				
				<div class="w-full md:w-5/6">
				  <div class="flex flex-col md:flex-row items-center mb-2">
				    <label class="block text-sm font-medium text-gray-700 mr-2">Time:</label>
				    <div class="flex-grow">
						<input type="datetime-local" name="trackDate" value="${currentDateTime}" class="border rounded px-2 py-1 w-full max-w-xs" required
							style="max-width: 300px"/>						
				    </div>
				  </div>					
				  <div class="flex flex-col md:flex-row items-center mb-2">  
				    <label class="block text-sm font-medium text-gray-700 mr-2">Status:</label>
				    <div class="flex-grow">
						<select name="trackStatus" class="dropDownStatus border rounded px-2 py-1 w-full max-w-xs" required style="max-width: 150px" rowNumber="${nextCount}">
			`;

    htmlCode += $("#trackStatusOptions").clone().html();

    htmlCode += `
				      </select>						
				    </div>
				  </div>
				  <div class="flex flex-col md:flex-row items-start mb-2">
				    <label class="block text-sm font-medium text-gray-700 mr-2">Notes:</label>
				    <div class="flex-grow">
						<textarea rows="2" cols="10" class="border rounded px-2 py-1 w-full max-w-xs" name="trackNotes" id="${trackNoteId}" style="max-width: 300px" required></textarea>
				    </div>
				  </div>
				</div>				
			</div>	
			<div id="${emptyLineId}" class="block">&nbsp;</div>
	`;

    return htmlCode;
}

function formatCurrentDateTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;
    if (hour < 10) hour = "0" + hour;
    if (minute < 10) minute = "0" + minute;
    if (second < 10) second = "0" + second;

    return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
}
