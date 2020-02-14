function getRequest(){
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'http://localhost:8091/all');

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			$(".result").text(" " + xhr.responseText);
		}
	}

	xhr.send();
}

function clickAdd() {
	var author = $("#author").val();
	var title = $("#title").val();
	$("#author").val("");
	$("#title").val("");
	addBook(author, title);
}

function addBook(author, title) {
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'http://localhost:8091/add?author=' + author + '&title=' + title);

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			$(".result").text(" " + xhr.responseText);
		}
	}

	xhr.send();
}