package ${package}.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${package}.mapper.${className}Mapper;
import ${package}.domain.${className};
import ${package}.service.I${className}Service;

/**
* ${tableComment} 服务层实现
*
* @author ${author}
* @date ${datetime}
*/
@Service
public class ${className}ServiceImpl extends BaseServiceImpl
<${className}Mapper, ${className}>
implements ${className}Service {

@Autowired
private ${className}Mapper ${classname}Mapper;
}
