function getRequest(){
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'http://localhost:8090/book/all');

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			$(".result").html("<caption>Список книг</caption><tr><th>ID</th><th>Автор</th><th>Название</th><th></th></tr>");
			var books = JSON.parse(xhr.responseText);
			for (var i=0; i<=books.length-1; i++) {
				var idBook = String(books[i].id);
				var authorBook = books[i].author;
				var titleBook = String(books[i].title);
				$(".result").append("<tr><td>" + idBook + "</td><td>" + authorBook + "</td><td>" + titleBook + "</td><th><button onclick='" + deleteBook.bind(null, idBook) + "'>del</button></tr>");
			}
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

	xhr.open('GET', 'http://localhost:8090/book/add?author=' + author + '&title=' + title);

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			getRequest();
		}
	}

	xhr.send();
}

function deleteBook(idBook) {
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'http://localhost:8090/book/delete?id=' + idBook);

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			getRequest();
		}
	}

	xhr.send();
}