function nowBorrowForm(isbn, name, author, publishment, exist) {
	
	$("#borrowIsbn").val(isbn);
	$("#isbn").val(isbn);
	$("#borrowName").val(name);
	$("#name").val(name);
	$("#borrowAuthor").val(author);
	$("#author").val(author);
	$("#borrowPublishment").val(publishment);
	$("#publishment").val(publishment);
	$("#borrowExist").val(exist);
	$("#exist").val(exist);

	document.getElementById("nowBorrowForm").style.display="block";
}