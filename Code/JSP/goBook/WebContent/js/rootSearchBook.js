function updateBookForm(isbn, cid, name, author, publishment, price, addtime, place, total, loan, exist) {
//	alertNormal(isbn + name);
//	alertNormal(cid);
	
//	var isbn=document.getElementById("isbn").innerHTML;
	
	$("#isbn").val(isbn);
	$("#updateIsbn").val(isbn);
	$("#updateCategory").val(cid);
	$("#updateName").val(name);
	$("#updateAuthor").val(author);
	$("#updatePublishment").val(publishment);
	$("#updatePrice").val(price);
	$("#updateAddtime").val(addtime);
	$("#updatePlace").val(place);
	$("#updateTotal").val(total);
	$("#updateLoan").val(loan);
	$("#updateExist").val(exist);

	document.getElementById("updateForm").style.display="block";
}
	
	