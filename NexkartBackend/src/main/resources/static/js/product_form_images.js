
var extraImagesCount = 0;

$(document).ready(function() {
	$("input[name='extraImage']").each(function(index) {
		extraImagesCount++;

		$(this).change(function() {
			if (!checkFileSize(this)) {
				return;
			}
			showExtraImageThumbnail(this, index);
		});
	});

	$("a[name='linkRemoveExtraImage']").each(function(index) {
		$(this).click(function() {
			removeExtraImage(index);
		});
	});

});

function showExtraImageThumbnail(fileInput, index) {
	var file = fileInput.files[0];

	fileName = file.name;

	imageNameHiddenField = $("#imageName" + index);
	if (imageNameHiddenField.length) {
		imageNameHiddenField.val(fileName);
	}


	var reader = new FileReader();
	reader.onload = function(e) {
		$("#extraThumbnail" + index).attr("src", e.target.result);
	};

	reader.readAsDataURL(file);

	if (index >= extraImagesCount - 1) {
		addNextExtraImageSection(index + 1);
	}
}

function addNextExtraImageSection(index) {
	htmlExtraImage = `
    <div class="relative group border-2 border-dashed rounded-lg p-4 hover:border-blue-500 transition-colors" id="divExtraImage${index}">
      <div class="flex justify-between items-center mb-4" id="extraImageHeader${index}">
        <label class="font-medium text-gray-700">Extra Image #${index + 1}</label>
      </div>
      <div class="w-full h-64 bg-gray-100 rounded-lg overflow-hidden">
        <img id="extraThumbnail${index}" class="w-full h-full object-cover" 
             src="${defaultImageThumbnailSrc}"
             alt="Extra image #${index + 1} preview"/>
      </div>
      <div class="mt-4">
        <label class="block w-full px-4 py-2 bg-white border rounded-lg cursor-pointer hover:bg-gray-50">
          <span class="text-gray-600">Upload File</span>
          <input type="file" name="extraImage" 
                 class="hidden" 
                 onchange="showExtraImageThumbnail(this, ${index})"
                 accept="image/png, image/jpeg" />
        </label>
      </div>
    </div>
  `;

	htmlLinkRemove = `
    <button class="text-red-500 hover:text-red-700 cursor-pointer"
            onclick="removeExtraImage(${index})"
            title="Remove this image">
      ✕
    </button>
  `;

	$("#divProductImages").append(htmlExtraImage);
	$("#extraImageHeader" + (index - 1)).append(htmlLinkRemove);
	extraImagesCount++;
}

function removeExtraImage(index) {
	$("#divExtraImage" + index).remove();
}
