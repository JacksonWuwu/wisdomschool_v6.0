<script type="text/javascript" src="/js/common.js"></script>
<script>
    var token = localStorage.getItem("token");
    $(document).click(function(e) { // 在页面任意位置点击而触发此事件
        if($(e.target).is('a')){
            var href= $(e.target).attr("href")
            if(href!=="#"&&href!==""&&href!==undefined) {
                if (href.indexOf("?") === -1) {
                    href = href + "?token=" + token
                } else {
                    href = href + "&token=" + token
                }
            }
            $(e.target).attr("href",href)
        };       // e.target表示被点击的目标
    });
</script>
