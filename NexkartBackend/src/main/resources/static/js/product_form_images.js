var extraImagesCount = 1;
var defaultImageThumbnailSrc = "/images/image-thumbnail.png";

$(document).ready(function() {

	$("input[name='extraImage']").each(function(index) {
		$(this).change(function() {
			if (!checkFileSize(this)) return;
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
	const htmlExtraImage = `
        <div class="extra-image-section" id="divExtraImage${index}">
            <div class="relative group">
                <div class="w-full h-48 border-2 border-dashed border-gray-300 rounded-lg flex items-center justify-center hover:border-blue-500 transition-colors duration-200 bg-gray-50">
                    <img id="extraThumbnail${index}" alt="Extra image preview" 
                         class="h-full w-full object-contain rounded-lg"
                         src="${defaultImageThumbnailSrc}"/>
                </div>
                <input type="file" name="extraImage"
                       onchange="showExtraImageThumbnail(this, ${index})"
                       accept="image/png, image/jpeg"
                       class="absolute inset-0 w-full h-full opacity-0 cursor-pointer"/>
                <a href="javascript:removeExtraImage(${index})"
                   class="absolute top-1 right-1 text-red-500 hover:text-red-700 bg-white rounded-full p-1 shadow-sm">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                </a>
            </div>
            <div id="extraImageHeader${index}" class="mt-2 text-xs text-gray-600">Extra Image #${index + 1}</div>
        </div>
    `;

	$("#divProductImages").append(htmlExtraImage);
	extraImagesCount++;
}

function removeExtraImage(index) {
	$("#divExtraImage" + index).remove();
	$('.extra-image-section').each(function(i) {
		$(this).find('.text-xs').text(`Extra Image #${i + 1}`);
	});
	extraImagesCount--;
}