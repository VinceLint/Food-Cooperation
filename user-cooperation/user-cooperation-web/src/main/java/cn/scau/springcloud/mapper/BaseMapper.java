package cn.scau.springcloud.mapper;


import cn.scau.springcloud.domain.entity.BaseDO;
import cn.scau.springcloud.domain.query.Query;

import java.util.List;

/**
 * 新表统一使用该mapper基类
 *
 * @param <E> 实体泛型
 * @param <Q> 查询泛型
 * @author vincelin 2019/7/26
 */
public interface BaseMapper<E extends BaseDO, Q extends Query> {

    /**
     * 插入
     *
     * 只写入非空字段，如果字段为null则使用数据库默认值
     *
     * @param e 实体
     * @return 受影响的行数
     */
    Integer insertSelective(E e);

    /**
     * 逻辑删除
     *
     * is_deleted设置为1
     *
     * @param id 主键id
     * @return 受影响的行数
     */
    Integer deleteByPrimaryKey(Long id);

    /**
     * 按主键id查询
     *
     * @param id 主键id
     * @return 实体
     */
    E selectByPrimaryKey(Long id);

    /**
     * 更新
     *
     * 按照主键id更新，只写入非空字段
     * updated_at使用数据库当前时间
     *
     * @param e 实体
     * @return 受影响的行
     */
    Integer updateByPrimaryKeySelective(E e);

    /**
     * 根据查询项查询记录数据
     * @param q 查询条件
     * @return 查询结果
     */
    List<E> query(Q q);

    /**
     * 根据查询项查询记录数据
     * @param q 查询条件
     * @return 查询结果
     */
    E queryOne(Q q);

    /**
     * 根据查询项统计记录条数
     * @param q 查询条件
     * @return 记录总数
     */
    Integer count(Q q);
}
