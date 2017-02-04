//card list by author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var rid = urlparameter("id");//优惠id

$(document).ready(function(){
	loadCard();
	
	browse_record(code);//记录访问统计
});


function loadCard(){
	currentStart = 0;
	printUtil(".card_list_tab", {"rid":rid, "start":currentStart, "size":size}, mainpath+"/card/listCardByRid.shtml", printCard);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendCard(){
	var beforeStart = currentStart;
	appendUtil(".card_list_tab", {"rid":rid, "start":currentStart, "size":size}, mainpath+"/card/listCardByRid.shtml", printCard);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printCard(response){
	var html = "";
	var list = response.list;
	
	for (i in list){
		currentStart++;
		
		if(i % 2 == 0){
			html += "</tr>";
			html += "<tr>";
		}
		html += "	<td>";
		html += "		<a href=\""+wecharauto2url("card_detail.shtml?id="+list[i].id)+"\">";
		html += "			<p class=\"pt\">";
		html += "				<span class=\"name\">"+list[i].name+"</span><img src=\""+list[i].img+"\" alt=\""+list[i].name+"\">";
		html += "			</p>";
		html += "			<p class=\"wz\">"+list[i].mainreward+"</p>";
		html += "		</a>";
		html += "	</td>";
		
	}
	
	html += "</tr>";
	return html;
}


