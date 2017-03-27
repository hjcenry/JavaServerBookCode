$(document).ready(function(e) {
	
    /*左侧多级菜单*/
	$(".page-sidebar-menu>li>a").click(function(e) {
	  $(this).parent().children(".sub-menu").toggle();
	  $(this).children("span.arrow").toggleClass("open");
    });
	
	/*二级菜单点击样式*/
	$(".sub-menu>li").click(function(e) {
		//给二级菜单添加样式
        $(this).addClass("active").siblings().removeClass("active");
		//给一级菜单添加样式
		$(this).parent().parent().addClass("active").children("a").children("span").first().after("<span class='selected'></span>");
		//取消其他一级菜单以及二级菜单样式
		$(this).parent().parent().siblings().removeClass("active").children(".sub-menu").hide().children("li").removeClass("active").parent().parent().children("a").children("span.arrow").removeClass("open");
    });
	
	$(".nav>li").click(function(e) {
		$(this).addClass("active").siblings().removeClass("active");
	});
	
	$(".mix-filter>li").click(function(e) {
        $(this).addClass("active").siblings().removeClass("active");
    });
});