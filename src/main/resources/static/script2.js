function getAuthors(){
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'http://localhost:8090/book/allA');

	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4 && xhr.status === 200) {
			$(".resulta").html("<caption>Список авторов</caption><tr><th>ID</th><th>Автор</th><th>");
			var authors = JSON.parse(xhr.responseText);
			for (var i=0; i<=authors.length-1; i++) {
				var idA = String(authors[i].id);
				var authorBook = authors[i].authorName;
				$(".resulta").append("<tr><td>" + idA + "</td><td>" + authorBook + "</td></tr>");
			}
		}
	}

	xhr.send();
}