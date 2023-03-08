-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('${tableComment}', '3', '1', '/${moduleName}/${classname}', 'C', '0', '${moduleName}:${classname}:view', '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '${tableComment}菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('${tableComment}查询', @parentId, '1',  '#',  'F', '0', '${moduleName}:${classname}:list',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('${tableComment}新增', @parentId, '2',  '#',  'F', '0', '${moduleName}:${classname}:add',          '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('${tableComment}修改', @parentId, '3',  '#',  'F', '0', '${moduleName}:${classname}:edit',         '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('${tableComment}删除', @parentId, '4',  '#',  'F', '0', '${moduleName}:${classname}:remove',       '#', 'admin', '2018-03-01', 'ry', '2018-03-01', '');
