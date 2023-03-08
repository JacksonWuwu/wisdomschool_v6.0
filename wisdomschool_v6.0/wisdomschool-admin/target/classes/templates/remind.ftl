<!DOCTYPE html>
<html>
<head>
    <title>提示</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <!-- IE8/9及以后的版本用最新的引擎渲染网页 -->
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <style>
        body{
            font-size: 13px;
            font-family: Georgia,Verdana,sans-serif;
            color: #333;
            padding:  0;
            margin: 0;
        }
        .wrap{
            min-width: 1024px;
            background-color: white;
            position: relative;
        }
        .wrap .title{
            text-align: center;
            margin: 13px 25px;
            font-weight: bold;
            font-size: 19px;
        }
        .wrap .list{
            width: 100%;
            margin-bottom: 20px;
        }
        .wrap .list .item{
            text-align: center;
            padding: 10px;
            width: 25%;
        }

        .wrap .list .item .link{
            padding-bottom: 4px;
            background-position: center top;
            background-repeat: no-repeat;
            display: block;
            text-decoration: none;
        }


        .wrap .list .item .link .name{
            color: #e25600;
            text-align: center;
            text-decoration: underline;
            font-size: 19px;
            font-family: 'Open Sans',sans-serif;
            font-weight: 300;
        }
        .wrap .list .item .link .vendor{
            font-size: 10px;
            color: #aaa;
            text-align: center;
            display: block;
            margin-top: 5px;
            text-decoration: none;
        }
        .wrap .tag {
            text-align: center;
            margin: 13px 25px;
            font-size: 19px;
            font-family: 'Open Sans',sans-serif;
            font-weight: 300;
        }
    </style>
</head>
<body>
<div class="wrap">
    <p class="title">为了更好地体验系统，请您前往谷歌浏览器浏览！
        </p>
    <!-- 用table,为了兼容 ie5 -->
    <table class="list">
        <tr>

            <td class="item">
                <img src="/img/front/chrome.png" style="margin: 0 auto;height: auto;display: block;width: 90px;">
                <a class="link bc" href="http://www.google.cn/chrome/browser/desktop/index.html" target="_blank">
                    <span class="name">Chrome</span>
                    <span class="vendor">Google</span>
                </a>
            </td>

        </tr>
    </table>
    <p class="tag">如果您的谷歌版本过旧，请更新浏览器，Chrome 56.0.2924.87以上即可，带来更多安全、极速和乐趣。</p>
</div>
</body>
</html>

