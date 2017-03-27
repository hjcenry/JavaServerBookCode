var method;
function pagination(nowPage,onePageCount,totalcount,callmethod,index){
	
	var fun=callmethod+"";
	var funname=(fun).substring(8,fun.indexOf("(")).replace(" ", "");
	if(totalcount == null || totalcount == ''){
		totalcount = 0;
	}
	
	if(funname!="openLevelDown"){
		$("#Pagination_content").empty();
	}else{
		$("#Pagination_content"+index).empty();
	}
	
	//计算总页数
	var totalpage = parseInt((totalcount/onePageCount))+((totalcount%onePageCount)>0?1:0);
	if(nowPage > totalpage){
		nowPage = totalpage;
		if(funname!="openLevelDown"){
			$("#nowPage").val(nowPage);
		}else{
			$("#nowPage"+index).val(nowPage);
		}
		
	}
	method=callmethod;
	//定义分页文本
	var content = "";
	//显示文本        
	if(nowPage==1){
		content +='<li><a href="javascript:void(0)"  class="gray_666">首页</a></li>';
		content += '<li><a href="javascript:void(0)" class="gray_666">前页</a></li>';
	} else {
		if(funname!="openLevelDown"){
			content += "<li><a href='javascript:void(0)' onclick='firstpage("+1+","+onePageCount+")'>首页</a></li>";
			content += "<li><a href='javascript:void(0)' onclick='pervpage("+nowPage+","+onePageCount+")'>前页</a></li> ";
		}else{
			content += "<li><a href='javascript:void(0)' onclick='firstpagea("+1+","+onePageCount+","+index+","+isnew+")'>首页</a></li>";
			content += "<li><a href='javascript:void(0)' onclick='pervpagea("+nowPage+","+onePageCount+","+index+","+isnew+")'>前页</a></li> ";
		}
		
	}
	
	//组织页码
	var startPage = 0;
	var endPage = 0;

	//是否超过7页
	if(totalpage <= 7){
		startPage = 1;
		endPage = totalpage;
	} else {//页码超过7页
		startPage = nowPage-3;
		endPage = startPage+6;
		if(startPage<1){
			startPage=1;
		}
		if(endPage>totalpage){
			endPage=totalpage;
		}
		if((nowPage - 3)>1){
			//frontDot = 1;
			if((totalpage -3)<=nowPage){
				endPage = totalpage;
				startPage = totalpage - 6;
			} else {
				//behindDot = 1;
				endPage = nowPage - 0+3;
				startPage = nowPage -3;
			}
		} else {
			startPage = 1;
			if((totalpage-startPage) <= 6){
				endPage = totalpage;
			} else {
				//behindDot = 1;
				endPage = startPage - 0  + 6;
			}
		}
	}
	for(var i=startPage;i<=endPage;i++){
		if(i==nowPage){
			content += "<li class='active'><a href='javascript:void(0);'>"+i+"</a></li>";
		}else{
			if(funname!="openLevelDown"){
				content += "<li><a href='javascript:void(0);' onclick='currpage("+i+","+onePageCount+")'>"+i+"</a></li> ";
			}else{
				content += "<li><a href='javascript:void(0);' onclick='currpagea("+i+","+onePageCount+","+index+","+isnew+")'>"+i+"</a></li> ";
			}
			
		}
	}
	 
		
	if(nowPage>=totalpage){
		content	+= "<li><a href='javascript:void(0)' class='gray_666'>后页</a></li>";
		content	+= "<li><a href='javascript:void(0)' class='gray_666'>尾页</a></li>";
	}else{
		if(funname!="openLevelDown"){
			content	+= "<li><a href='javascript:void(0)' onclick='nextpage("+nowPage+","+onePageCount+")'>后页</a></li> ";
			content	+= "<li><a href='javascript:void(0)' onclick='lastpage("+totalpage+","+onePageCount+")' >尾页</a></li>";
		}else{
			content	+= "<li><a href='javascript:void(0)' onclick='nextpagea("+nowPage+","+onePageCount+","+index+","+isnew+")'>后页</a></li> ";
			content	+= "<li><a href='javascript:void(0)' onclick='lastpagea("+totalpage+","+onePageCount+","+index+","+isnew+")' >尾页</a></li>";
		}
		
	}
	if(funname!="openLevelDown"){
		$("#Pagination_content").append(content);
	}else{
		$("#Pagination_content"+index).append(content);
	}
	
}

function firstpage(nowPage,onePageCount){
	$("#nowPage").val(nowPage);
	$("#onePageCount").val(onePageCount);
	sel();
}
function firstpagea(nowPage,onePageCount,index,isnew){

	$("#nowPage"+index).val(nowPage);
	$("#onePageCount"+index).val(onePageCount);
	openLevelDown();
}


function lastpage(nowPage,onePageCount){
	$("#nowPage").val(nowPage);
	$("#onePageCount").val(onePageCount);
	sel();
}
function lastpagea(nowPage,onePageCount,index,isnew){

	$("#nowPage"+index).val(nowPage);
	$("#onePageCount"+index).val(onePageCount);
	openLevelDown();
}


function currpage(nowPage,onePageCount){
	$("#nowPage").val(nowPage);
	$("#onePageCount").val(onePageCount);
	sel();
}
function currpagea(nowPage,onePageCount,index,isnew){
	
	$("#nowPage"+index).val(nowPage);
	$("#onePageCount"+index).val(onePageCount);
	openLevelDown();
}


function pervpage(nowPage,onePageCount){
	$("#nowPage").val((parseInt(nowPage)-1));
	$("#onePageCount").val(onePageCount);
	sel();
}
function pervpagea(nowPage,onePageCount,index,isnew){
	$("#nowPage"+index).val((parseInt(nowPage)-1));
	$("#onePageCount"+index).val(onePageCount);
	openLevelDown();
}


function nextpage(nowPage,onePageCount){
	$("#nowPage").val((parseInt(nowPage)+1));
	$("#onePageCount").val(onePageCount);
	sel();
}
function nextpagea(nowPage,onePageCount,index,isnew){
	
	$("#nowPage"+index).val((parseInt(nowPage)+1));
	$("#onePageCount"+index).val(onePageCount);
	openLevelDown();
}

function currcount(obj){
	$("#nowPage").val(1);
	$("#onePageCount").val(obj.options[obj.selectedIndex].value);
}

/*function checkInput(event,totalpage){
	var keyCode = 0;
	if(window.event){
		keyCode = event.keyCode;
	} else {
		keyCode = event.which;
	}
	var returnFlag = 0;
	if(keyCode>=48 && keyCode<=57){//数字
		var num = keyCode - 48;//输入的数字
		if($("#toPage").val()==""){
			if(num <= totalpage && num!=0){
				returnFlag = 1;
			}
		} else {
			if((parseInt($("#toPage").val()) * 10 + num) <= totalpage){
				returnFlag = 1;
			}
		}
	} else if(keyCode==8 || keyCode==0){
		returnFlag = 1;
	}
	if(returnFlag==0){
		if(window.event){
			event.keyCode = 0;
			event.returnValue = false;
		} else {
			event.which = 0;
			event.preventDefault();
		}
	}
}

function changePage(onePageCount){
	if($("#toPage").val()!=""){
		currpage(parseInt($("#toPage").val()),onePageCount);
	}
}*/

/**
 * 重新计算分页
 * @param delCount 要删除条数
 * @param onePageCount 每页显示多少条
 */
function setTotalCount(delCount,onePageCount) {
	var totalcount = $("#totalCount").val()-delCount;
	$("#totalCount").val(totalcount);
	var totalpage = parseInt((totalcount/onePageCount))+((totalcount%onePageCount)>0?1:0);
	var nowPage = $("#nowPage").val();
	if(nowPage > totalpage){
		nowPage = totalpage;
		$("#nowPage").val(nowPage);
	}
}

function setTotalCounta(delCount,onePageCount) {
	var totalcount = $("#totalCounta").val()-delCount;
	$("#totalCounta").val(totalcount);
	var totalpage = parseInt((totalcount/onePageCount))+((totalcount%onePageCount)>0?1:0);
	var nowPage = $("#nowPagea").val();
	if(nowPage > totalpage){
		nowPage = totalpage;
		$("#nowPagea").val(nowPage);
	}
}