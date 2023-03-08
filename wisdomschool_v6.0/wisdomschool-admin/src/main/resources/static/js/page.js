/**
 * Created by dws on 2019/02/20.
 */
(function ($) {

    $.fn.page = {
        //分页初始化，填充html
        init: function (options) {

            options = $.extend(pageDefaultOptions.pagination, options);

            /**
             * 初始化内容项
             */
            let pagerObject = {
                pager: {
                    //每页显示条数
                    pageSize: 12,
                    //开始记录数
                    startRecord: 0,
                    //当前页数
                    nowPage: 1,
                    //总记录数
                    recordCount: 0,
                    //总页数
                    pageCount: 0
                },
                option: options,
                //显示数据
                exhibitDatas: null,
                //参数列表
                parameters: null,
                //加载分页数据
                load: function () {
                    let pagerObj = this;
                    //将参数传递后台AJAX获取数据
                    let url = pagerObj.option.loadURL;
                    let pager = {};
                    pager.pageSize = pagerObj.pager.pageSize;
                    pager.startRecord = pagerObj.pager.startRecord;
                    pager.pageNum = pagerObj.pager.nowPage;
                    pager.parameters = pagerObj.parameters ? pagerObj.parameters : {};
                    pager.total = pagerObj.pager.recordCount ? pagerObj.pager.recordCount : -1;
                    pager.pages = pagerObj.pager.pageCount ? pagerObj.pager.pageCount : -1;
                    let params = {};
                    params.pager = JSON.stringify(pager);
                    $.ajax({
                        type: 'post',
                        url: url,
                        data: params,
                       contentType: 'application/x-www-form-urlencoded',
                        beforeSend: function (xhr) {
                            let token = localStorage.getItem("token");
                            if(token && token !=='') {
                                xhr.setRequestHeader("Authorization", token);
                            }
                        },
                        success: function (pager) {
                           // pager = $.parseJSON(pager);
                           // pager = JSON.parse(pager);
                            //处理展示数据
                            pagerObj.exhibitDatas = pager.rows;
                            if (pager.total <= 0) {
                                let pagerContent = $('.pagerContent');
                                let mContent = $(pagerContent).find('#m-content');
                                if (mContent.size() === 0) {
                                    mContent = document.createElement('div');
                                    mContent.id = 'm-content';
                                }
                                $(mContent).empty();
                                $(mContent).append('<h4>没有更多数据！</h4>');
                                pagerContent.append(mContent);
                                return;
                            }
                            //处理分页信息
                            pagerObj.pager.pageSize = pager.pageSize;
                            pagerObj.pager.startRecord = pager.startRecord;
                            pagerObj.pager.nowPage = pager.pageNum;
                            pagerObj.pager.recordCount = pager.total;
                            pagerObj.pager.pageCount = Math.floor((pagerObj.pager.recordCount - 1) / pagerObj.pager.pageSize) + 1;
                            //渲染html
                            pagerObj.render();
                            //$.modal.closeLoading();
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            //$.modal.closeLoading();
                        }
                    });
                },
                //重新加载页面
                reload: function () {
                    let pagerReflectionObj = this;
                    pagerReflectionObj.pager.nowPage = 1;
                    pagerReflectionObj.load();
                },
                //渲染html
                render: function () {

                    let pagerObj = this;
                    let pagerContent = $('.pagerContent');
                    let pagerMainContent = $(pagerContent).find('.pagerMainContent');
                    let $pc = $(pagerMainContent);
                    if (pagerMainContent[0] !== undefined) {
                        pagerMainContent[0].style.display = "block";
                    }

                    let contentUL = document.createElement('div');
                    for (let i = 0; i < pagerObj.exhibitDatas.length; i++) {
                        let contentItem = document.createElement('div');
                        contentItem.className = 'col-lg-4 col-md-3 col-sm-4 pic-list-constant';
                        for (let j = 0; j < options.item.length; j++) {
                            let key = options.item[j].key;
                            let replace = options.item[j].replace;
                            let attr = options.item[j].attr;
                            let $hrefObj = $pc.find('[key="' + key + '"]');
                            let keys = key.split(".");
                            let data;
                            if (Array.isArray(keys)) {
                                data = pagerObj.exhibitDatas[i];
                                for (k of keys) {
                                    data = data[k];
                                }
                            } else {
                                data = pagerObj.exhibitDatas[i][key];
                            }

                            if (replace !== undefined && replace !== '') {
                                if (attr !== undefined && attr !== '') {
                                    $hrefObj.attr(attr, replace.replace("{%}", data));
                                } else {
                                    $hrefObj.attr(attr, data);
                                }
                            } else {
                                $hrefObj.html(data);
                            }
                        }
                        contentItem.setAttribute("data-id", pagerObj.exhibitDatas[i]['id']);
                        contentItem.innerHTML = $pc.html();
                        contentUL.appendChild(contentItem);
                        if (pagerMainContent[0] !== undefined) {
                            pagerMainContent[0].style.display = "none";
                        }
                    }

                    let mContent = $(pagerContent).find('#m-content');
                    if (mContent.size() === 0) {
                        mContent = document.createElement('div');
                        mContent.id = 'm-content';
                    }
                    $(mContent).empty();
                    $(mContent).append(contentUL);
                    pagerContent.append(mContent);

                    //构建分页页码
                    let pagerStatus = document.createElement('span');
                    if (pagerObj.exhibitDatas == null || pagerObj.exhibitDatas.length <= 0) {
                        mContent.html(pageDefaultOptions.pageInfo.nothing);
                    } else {
                        pagerStatus.className = 'text-primary pager-status';
                        let info = pageDefaultOptions.pageInfo.info;
                        info = $.fn.page.tools.replaceAll(info, '{startRecord}', pagerObj.pager.startRecord);
                        info = $.fn.page.tools.replaceAll(info, '{nowPage}', pagerObj.pager.nowPage);
                        info = $.fn.page.tools.replaceAll(info, '{recordCount}', pagerObj.pager.recordCount);
                        info = $.fn.page.tools.replaceAll(info, '{pageCount}', pagerObj.pager.pageCount);
                        pagerStatus.innerHTML = info;
                    }
                    let nowPage = pagerObj.pager.nowPage;
                    let pageCount = pagerObj.pager.pageCount;
                    let mPagination = $(pagerContent).find('#m-pagination');
                    if (mPagination.size() === 0) {
                        mPagination = document.createElement('div');
                        mPagination.id = 'm-pagination';
                    }
                    $(mPagination).empty();
                    let pagination = document.createElement('ul');
                    pagination.className = 'pagination';
                    $(mPagination).append(pagerStatus)
                    $(mPagination).append(pagination)
                    pagerContent.append(mPagination);


                    if (pagerObj.exhibitDatas != null && pagerObj.exhibitDatas.length > 0) {
                        //第一页按钮
                        let goFirstBtn = document.createElement('li');
                        goFirstBtn.title = pageDefaultOptions.pageInfo.firstPageTitle;
                        if (nowPage <= 1) {
                            goFirstBtn.className = 'disabled';
                            goFirstBtn.title = pageDefaultOptions.pageInfo.alreadyFirstPage;
                        }
                        goFirstBtn.innerHTML = '<a href="javascript:void(0);">' + pageDefaultOptions.pageInfo.firstPage + '</a>';
                        pagination.appendChild(goFirstBtn);
                        $(goFirstBtn).click(function () {
                            pagerObj.loadByPage('first');
                        });
                        //上一页按钮
                        let goPrevBtn = document.createElement('li');
                        goPrevBtn.title = pageDefaultOptions.pageInfo.prevPageTitle;
                        if (nowPage <= 1) {
                            goPrevBtn.className = 'disabled';
                            goPrevBtn.title = pageDefaultOptions.pageInfo.alreadyFirstPage;
                        }
                        goPrevBtn.innerHTML = '<a href="javascript:void(0);">' + pageDefaultOptions.pageInfo.prevPage + '</a>';
                        pagination.appendChild(goPrevBtn);
                        $(goPrevBtn).click(function () {
                            pagerObj.loadByPage('prev');
                        });
                        //页面列表
                        if (pageCount <= 8) {
                            for (let i = 1; i <= pageCount; i++) {
                                let goPageBtn = document.createElement('li');
                                goPageBtn.setAttribute('page', i);
                                goPageBtn.title = $.fn.page.tools.replaceAll(pageDefaultOptions.pageInfo.nowPageTitle, '{nowPage}', i);
                                goPageBtn.className = i == nowPage ? 'active' : '';
                                goPageBtn.innerHTML = '<a href="javascript:void(0);">' + $.fn.page.tools.replaceAll(pageDefaultOptions.pageInfo.nowPage, '{nowPage}', i) + '</a>';
                                pagination.appendChild(goPageBtn);
                                if (i != nowPage) {
                                    $(goPageBtn).click(function () {
                                        pagerObj.goPage($(this).attr('page'));
                                    });
                                }
                            }
                        } else {
                            //获取开始、结束号
                            let before = 2;
                            let after = 2;
                            let start = (nowPage - before) < 1 ? 1 : (nowPage - before);
                            let end = (nowPage + after) > pageCount ? pageCount : (nowPage + after);
                            if ((end - start + 1) < (before + after + 1)) {
                                if (start === 1) {
                                    end = end + ((before + after + 1) - (end - start + 1));
                                    end = end > pageCount ? pageCount : end;
                                }
                                if (end == pageCount) {
                                    start = start - ((before + after + 1) - (end - start + 1));
                                    start = start < 1 ? 1 : start;
                                }
                            }
                            for (let i = start; i <= end; i++) {
                                let goPageBtn = document.createElement('li');
                                goPageBtn.setAttribute('page', i);
                                goPageBtn.title = $.fn.page.tools.replaceAll(pageDefaultOptions.pageInfo.nowPageTitle, '{nowPage}', i);
                                goPageBtn.className = i == nowPage ? 'active' : '';
                                goPageBtn.innerHTML = '<a href="javascript:void(0);">' + $.fn.page.tools.replaceAll(pageDefaultOptions.pageInfo.nowPage, '{nowPage}', i) + '</a>';
                                pagination.appendChild(goPageBtn);
                                if (i != nowPage) {
                                    $(goPageBtn).click(function () {
                                        pagerObj.goPage($(this).attr('page'));
                                    });
                                }
                            }

                        }
                        let showPageText = document.createElement('li');
                        showPageText.className = 'active';
                        showPageText.innerHTML = '<a href="javascript:void(0);">' + nowPage + '</a>';
                        //下一页按钮
                        let goNextBtn = document.createElement('li');
                        goNextBtn.title = pageDefaultOptions.pageInfo.nextPageTitle;
                        if (nowPage >= pageCount) {
                            goNextBtn.className = 'disabled';
                            goNextBtn.title = pageDefaultOptions.pageInfo.alreadyLastPage;
                        }
                        goNextBtn.innerHTML = '<a href="javascript:void(0);">' + pageDefaultOptions.pageInfo.nextPage + '</a>';
                        pagination.appendChild(goNextBtn);
                        $(goNextBtn).click(function () {
                            pagerObj.loadByPage('next');
                        });
                        //最后一页按钮
                        let goLastBtn = document.createElement('li');
                        goLastBtn.title = pageDefaultOptions.pageInfo.lastPageTitle;
                        if (nowPage >= pageCount) {
                            goLastBtn.className = 'disabled';
                            goLastBtn.title = pageDefaultOptions.pageInfo.alreadyLastPage;
                        }
                        goLastBtn.innerHTML = '<a href="javascript:void(0);">' + pageDefaultOptions.pageInfo.lastPage + '</a>';
                        pagination.appendChild(goLastBtn);
                        $(goLastBtn).click(function () {
                            pagerObj.loadByPage('last');
                        });
                    }
                },
                loadByPage: function (type) {
                    //定义获取对象映像
                    let pagerReflectionObj = this;
                    type = type ? type : '';
                    if (type == 'first') {
                        let nowPage = pagerReflectionObj.pager.nowPage;
                        if (nowPage > 1) {
                            pagerReflectionObj.pager.startRecord = 0;
                            pagerReflectionObj.pager.nowPage = 1;
                            pagerReflectionObj.load();
                        }
                    } else if (type == 'prev') {
                        let nowPage = pagerReflectionObj.pager.nowPage;
                        let pageSize = pagerReflectionObj.pager.pageSize;
                        if (nowPage > 1) {
                            nowPage--;
                            pagerReflectionObj.pager.nowPage = nowPage;
                            pagerReflectionObj.pager.startRecord = pageSize * (nowPage - 1);
                            pagerReflectionObj.load();
                        }
                    } else if (type == 'next') {
                        let nowPage = pagerReflectionObj.pager.nowPage;
                        let pageSize = pagerReflectionObj.pager.pageSize;
                        let pageCount = pagerReflectionObj.pager.pageCount;
                        if (nowPage < pageCount) {
                            nowPage++;
                            pagerReflectionObj.pager.nowPage = nowPage;
                            pagerReflectionObj.pager.startRecord = pageSize * (nowPage - 1);
                            pagerReflectionObj.load();
                        }
                    } else if (type == 'last') {
                        let nowPage = pagerReflectionObj.pager.nowPage;
                        let pageCount = pagerReflectionObj.pager.pageCount;
                        if (nowPage < pageCount) {
                            let pageSize = pagerReflectionObj.pager.pageSize;
                            let pageCount = pagerReflectionObj.pager.pageCount;
                            pagerReflectionObj.pager.nowPage = pageCount == 0 ? 1 : pageCount;
                            pagerReflectionObj.pager.startRecord = pageSize * (pageCount - 1);
                            if (pagerReflectionObj.pager.startRecord < 0)
                                pagerReflectionObj.pager.startRecord = 0;
                            pagerReflectionObj.load();
                        }
                    } else {
                        pagerReflectionObj.load();
                    }
                },
                //跳转页面
                goPage: function (_page) {
                    let pagerReflectionObj = this;
                    let pageSize = pagerReflectionObj.pager.pageSize;
                    let pageCount = pagerReflectionObj.pager.pageCount;
                    let page = parseFloat(_page);
                    if (!isNaN(page)) {
                        if (0 < page && page <= pageCount) {
                            pagerReflectionObj.pager.nowPage = page;
                            pagerReflectionObj.pager.startRecord = pageSize * (page - 1);
                            pagerReflectionObj.load();
                        }
                        if (page <= 0) {
                            pagerReflectionObj.loadByPage('first');
                        }
                        if (page > pageCount) {
                            pagerReflectionObj.loadByPage('last');
                        }
                    }
                },
            };

            options.onInit();
            return pagerObject;
        },
        //工具
        tools: {
            //字符串全局替换
            replaceAll: function (s, s1, s2) {
                return s.replace(new RegExp(s1, "gm"), s2);
            }
        },

    };

    //开始分页
    $.fn.startPage = function (options) {
        let page = $.fn.page.init(options);
        //加载页面数据
        page.load();
        return page;
    };

    //默认属性
    let pageDefaultOptions = {
        pagination: {
            pageNum: 1,//页码
            pageSize: 10,//页面大小
            onInit: $.noop,//初始化完毕的回调
            onChange: $.noop,//分页改变或分页大小改变时的回调
            prevDisplay: true,//是否显示上一页按钮
            nextDisplay: true,//是否显示下一页按钮
            firstDisplay: false,//是否显示首页按钮
            lastDisplay: false,//是否显示尾页按钮
            loadURL: '',
        },
        item: {
            key: 'field'
        },
        pageInfo: {
            nothing: '无查询记录...',
            info: '总计 {recordCount} 条记录 / {pageCount} 页 | ',
            firstPage: '<i class="fa fa-angle-double-left"></i>',
            prevPage: '<i class="fa fa-angle-left"></i>',
            nextPage: '<i class="fa fa-angle-right"></i>',
            lastPage: '<i class="fa fa-angle-double-right"></i>',
            ellipseText: '...',//中间省略部分的文本
            firstPageTitle: '第一页',
            prevPageTitle: '上一页',
            nextPageTitle: '下一页',
            lastPageTitle: '最后一页',
            alreadyFirstPage: '已经是第一页了',
            alreadyLastPage: '已经是最后一页了',
            nowPage: '{nowPage}',
            nowPageTitle: '第 {nowPage} 页',
            errors: {
                notANumber: '您输入的内容不是数字',
                maxPageSize: '每页显示数量不得超过 {pageSizeLimit} 条，已还原为原设置'
            }
        }
    };

})(jQuery);
