$(document).ready(function() {

    $("#linkAddTrack").on("click", function(e) {
        e.preventDefault();
        addNewTrackRecord();
    });

    $("#trackList").on("click", ".linkRemoveTrack", function(e) {
        e.preventDefault();
        deleteTrack($(this));
        updateTrackCountNumbers();
    });

    $("#trackList").on("change", ".dropDownStatus", function(e) {
        var dropDownList = $(this);
        var rowNumber = dropDownList.attr("rowNumber");
        var selectedOption = $("option:selected", dropDownList);
        var defaultNote = selectedOption.data("default-description");
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
        element.innerHTML = index + 1;
    });
}

function addNewTrackRecord() {
    var htmlCode = generateTrackCode();
    $("#trackList").append(htmlCode);
}

function generateTrackCode() {
    var nextCount = $(".hiddenTrackId").length + 1;
    var rowId = "rowTrack" + nextCount;
    var emptyLineId = "emptyLine" + nextCount;
    var trackNoteId = "trackNote" + nextCount;
    var currentDateTime = formatCurrentDateTime();
    // Get status options from the hidden template.
    var optionsHtml = $("#trackStatusOptionsTemplate select").html();

    var htmlCode = `
        <div class="flex flex-col md:flex-row items-start border rounded p-4 mb-4" id="${rowId}">
          <input type="hidden" name="trackId" value="0" class="hiddenTrackId" />
          <div class="w-full md:w-1/6 flex flex-col items-center">
            <div class="divCountTrack text-xl font-bold">${nextCount}</div>
            <div class="mt-2">
              <a class="text-red-500 hover:text-red-700 linkRemoveTrack" href="" rowNumber="${nextCount}">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
                     viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6M1 7h22M10 3h4a1 1 0 011 1v1H9V4a1 1 0 011-1z" />
                </svg>
              </a>
            </div>
          </div>
          <div class="flex-1 mt-4 md:mt-0 md:ml-4">
            <div class="mb-4">
              <label class="block text-gray-700 font-medium">Time:</label>
              <input type="datetime-local" name="trackDate" value="${currentDateTime}"
                     class="mt-1 block border p-2 w-full rounded-md border-gray-300 shadow-sm" required/>
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 font-medium">Status:</label>
              <div>
                <select name="trackStatus" class="dropDownStatus mt-1 border p-2  block w-full rounded-md border-gray-300 shadow-sm"
                        rowNumber="${nextCount}" required>
                  ${optionsHtml}
                </select>
              </div>
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 font-medium">Notes:</label>
              <textarea rows="3" name="trackNotes" id="${trackNoteId}"
                        class="mt-1 block w-full border rounded-md border-gray-300 shadow-sm" required></textarea>
            </div>
          </div>
        </div>
        <div id="${emptyLineId}" class="mb-4">&nbsp;</div>
      `;
    return htmlCode;
}

function formatCurrentDateTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    var hour = ("0" + date.getHours()).slice(-2);
    var minute = ("0" + date.getMinutes()).slice(-2);
    var second = ("0" + date.getSeconds()).slice(-2);
    return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
}