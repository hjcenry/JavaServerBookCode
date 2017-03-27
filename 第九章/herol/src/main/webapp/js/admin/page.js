$(document).ready(function(){
	
});

function get(pageindex){
	var url = "../manager/adminlist";
	var rank = $('#rank').val();
	var rankType = $('#rankType').val();
	var key = $('#title').val();
	key = encodeURI(encodeURI(key));
	$("#right").load(url+"?pageindex="+pageindex+"&rank="+rank+"&rankType="+rankType+"&key="+key);
}

function search(){
	var url = "../manager/adminlist";
	var key = $('#title').val();
	key = encodeURI(encodeURI(key));
	$("#right").load(url+"?pageindex=1&key="+key);
}

function rank(type){
	var url = "../manager/adminlist";
	var rankType=type;
	var rank = $('#rank').val();
	if(rank=="asc"){
		rank = "desc";
	}else{
		rank = "asc";
	}
	var key = $('#title').val();
	key = encodeURI(encodeURI(key));
	$("#right").load(url+"?pageindex=1&rank="+rank+"&rankType="+rankType+"&key="+key);
}

function selchk(allchk) {
    var chk = document.getElementsByName("blacklist_checkbox");
    if (allchk.checked == true) {
        for (var i = 0; i < chk.length; i++) {
            chk[i].checked = true;
        }
    } else {
        for (var i = 0; i < chk.length; i++) {
            chk[i].checked = false;
        }
    }
}