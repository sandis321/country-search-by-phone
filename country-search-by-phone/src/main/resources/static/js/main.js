$(document).ready(function() {
	$("#search-form").submit(function(event) {
		event.preventDefault();
		submit();
	});
});

function submit() {
	var search = {}
	search["phonenumber"] = $("#phonenumber").val();
	$("#btn-search").prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/api/search",
		data : JSON.stringify(search),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Search Result</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			$('#feedback').html(json);
			$("#btn-search").prop("disabled", false);
		},
		error : function(e) {
			var json = "<h4>Search Result</h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);
			$("#btn-search").prop("disabled", false);

		}
	});

}