define(function (require, exports, module) {
    require('plugins');
    require('authc');
    require('wow');
    require('autoHidingNavBar');
    require('theiaStickySidebar');
    require('resizeSensor');
    require('lazyLoad');

    //懒加载
    let imagesLazyLoad = function () {
        require.async('lazyLoad', function () {
            $("img").lazyload({
                placeholder: app.base + '/dist/image/spinner.gif',
                effect: 'fadeIn'
            })
        })
    };

    //返回顶部
    let toTop = function () {
        let $window = $(window);
        let $scrollTopLink = $('a.scroll-to-top');
        $window.scroll(function () {
            if ($(this).scrollTop() > 100) {
                $scrollTopLink.fadeIn();
            } else {
                $scrollTopLink.fadeOut();
            }
        });
        $scrollTopLink.on('click', function (e) {
            $('html, body').animate({scrollTop: 0}, 400);
            return false;
        })
    };

    let initWow = function () {
        //wow
        let wow = new WOW({
            boxClass: 'wow',
            animateClass: 'animated',
            offset: 100,
            mobile: true,
            live: true
        });
        wow.init();
    };

    let initNav = function () {
        $("div.top-menu").autoHidingNavbar();
        $('a[nav]').each(function () {
            $this = $(this);
            if ($this.href == String(window.location)) {
                $this.closest('li').addClass('active');
            }
        });
    };

    let initSlider = function () {
        $('.widget-sidebar').theiaStickySidebar({
            // Settings
            additionalMarginTop: 70
        });
    };

    let initScroll = function () {
        let leftWidget = $(".left-widget");
        if ($(window).scrollTop() > $(".main .module").height()) {
            if (leftWidget.css('display') != "block")
                leftWidget.css('display', 'block');
        }
        $(window).scroll(function () {
            if ($(window).scrollTop() < $(".main .module").height()) {
                if (!leftWidget.hasClass("fadeOutDown")) {
                    leftWidget.removeClass('fadeInLeft');
                    leftWidget.addClass('fadeOutDown');
                }
            } else {
                if (leftWidget.css('display') != "block") {
                    leftWidget.css('display', 'block');
                } else if (!leftWidget.hasClass("fadeInLeft")) {
                    leftWidget.removeClass('fadeOutDown');
                    leftWidget.addClass('fadeInLeft');
                }
            }
        });
    };

    let initLogin = function () {
        $('.img__btn').on('click', function () {
            document.querySelector('.cont').classList.toggle('s--signup');
        });
    };

    let bindClickEvent = function () {
        let that = $(this);
        $('a[rel=favor]').click(function () {
            let id = that.data('id');
            if (!authc.isAuthced()) {
                authc.toLogin();
                return false;
            }

            if (parseInt(id) > 0) {
                $.getJSON(app.base + '/user/favor', {'id': id}, function (result) {
                    if (result.code >= 0) {
                        let favors = $("#favors").text();
                        $('#favors').text(parseInt(favors) + 1);
                    } else {
                        layer.msg(result.message, {icon: 5});
                    }
                })
            }
        });

        $('#ajax_login_submit').on('click', function () {
            authc.doPostLogin();
        });

        //搜索
        $('.top-search').on('click', function (e) {
            let container = $(this).closest('.search-wrapper');

            if (!container.hasClass('active')) {
                container.addClass('active');
                e.preventDefault();
            } else if (container.hasClass('active') && $(this).closest('.input-holder').length === 0) {
                container.removeClass('active');
                // clear input
                container.find('.search-input').val('');
                // clear and hide result container when we press close
                container.find('.result-container').fadeOut(100, function () {
                    $(this).empty();
                });
            } else if (container.hasClass('active') && $(this).closest('.input-holder').length !== 0) {
                $("#full-search").submit();
            }
        });
    };

    exports.init = function () {
        imagesLazyLoad();
        toTop();
        bindClickEvent();
        initWow();
        initNav();
        initSlider();
        initScroll();
        initLogin();
        $('[data-toggle="tooltip"]').tooltip();
    }
});

(function ($) {
    $.fn.CascadingSelect = function (options) {
        //默认参数设置
        var settings = {
            url: "/article/category", //请求路径
            data: "0",    //初始值(字符串格式)
            split: ",",    //分割符
            cssName: "form-control category-select",  //样式名称
            val: "id",    //<option value="id">name</option>
            text: "name",   //<option value="id">name</option>
            hiddenName: "categoryId" //隐藏域的name属性的值
        };
        //合并参数
        if (options)
            $.extend(settings, options);
        //链式原则
        return this.each(function () {
            init($(this), settings.data);

            /*
            初始化
            @param container 容器对象
            @param data   初始值
            */
            function init(container, data) {
                //创建隐藏域对象,并赋初始值
                let _input = $("<input type='hidden' name='" + settings.hiddenName + "' />").appendTo(container).val(settings.data);
                let arr = data.split(settings.split);
                for (let i = 0; i < arr.length; i++) {
                    //创建下拉框
                    createSelect(container, arr[i], arr[i + 1] || -1);
                }
            }

            /*
            创建下拉框
            @param container 容器对象
            @param parentid  父ID号
            @param id   自身ID号
            */
            function createSelect(container, parentid, id) {
                //创建select对象，并将select对象放入container内
                let _select = $("<select></select>").appendTo(container).addClass(settings.cssName);
                //如果parentid为空，则_parentid值为0
                let _parentid = parentid || 0;
                //发送AJAX请求,返回的data必须为json格式
                $.getJSON(settings.url, {parentId: _parentid}, function (data) {
                    //添加子节点<option>
                    addOptions(container, _select, data).val(id || -1)

                });
            }

            /*
            为下拉框添加<option>子节点
            @param container 容器对象
            @param select  下拉框对象
            @param data   子节点数据(要求数据为json格式)
            */
            function addOptions(container, select, data) {
                select.append($('<option value="-1">=请选择类别=</option>'));
                for (let i = 0; i < data.length; i++) {
                    select.append($('<option value="' + data[i][settings.val] + '">' + data[i][settings.text] + '</option>'));
                }
                //为select绑定change事件
                select.bind("change", function () {
                    _onchange(container, $(this), $(this).val())
                });
                return select;
            }

            /*
            select的change事件函数
            @param container 容器对象
            @param select  下拉框对象
            @param id   当前下拉框的值
            */
            function _onchange(container, select, id) {
                let nextAll = select.nextAll("select");
                //如果当前select对象的值是空或-1（即：==请选择==），则将其后面的select对象全部移除
                if (!id || id == "-1") {
                    nextAll.remove();
                }
                $.getJSON(settings.url, {parentId: id}, function (data) {
                    if (data.length > 0) {
                        let _html = $("<select class='" + settings.cssName + "'></select>");
                        let _select = addOptions(container, _html, data);
                        //判断当前select对象后面是否跟有select对象
                        if (nextAll.length < 1) {
                            select.after(_select); //没有则直接添加
                        } else {
                            nextAll.remove(); //有则先移除再添加
                            select.after(_select);
                        }
                    } else {
                        nextAll.remove(); //没有子项则后面的select全部移除
                    }
                    saveVal(container); //进行数据保存，此方法必须放在回调函数里面
                });
                //saveVal(container); //如果放在这里，则会出现bug

            }

            /*
            将选择的值保存在隐藏域中，用于表单提交保存
            @param container 容器对象
            */
            function saveVal(container) {
                let arr = new Array();
                arr.push(0); //为数组arr添加元素0，父节点从0开始，所以添加0
                $("select", container).each(function () {
                    if ($(this).val() > 0) {
                        arr.push($(this).val()); //获取container下每个select对象的值，并添加到数组arr
                    }
                });
                //为隐藏域对象赋值
                $("input[name='" + settings.hiddenName + "']", container).val(arr.join(settings.split));
            }

        });
    }
})(jQuery);