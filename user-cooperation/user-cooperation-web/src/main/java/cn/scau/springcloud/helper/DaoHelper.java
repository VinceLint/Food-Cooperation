package cn.scau.springcloud.helper;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.BaseDO;
import cn.scau.springcloud.domain.query.Query;
import cn.scau.springcloud.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 主键是Integer; 异常抛不出; 不支持事务
 * 双写不建议使用
 */
@Slf4j
public class DaoHelper {


    /**
     * 默认页码
     */
    private static final int DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认每页最大记录数
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 接口被刷偏移量限制
     */
    private static final int MAX_OFFSET_LIMIT = 20000;

    /**
     * 根据主键查询单条数据
     */
    public static <E extends BaseDO, Q extends Query> Result<E> getById(BaseMapper<E, Q> mapper, Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult();
        }
        try {
            return Result.successResult(mapper.selectByPrimaryKey(Long.parseLong(id.toString())));
        } catch (Exception ex) {
            log.error("get by id '{}' failed: {}.", id.toString(), ex.getMessage(), ex);
            return Result.exceptionResult(ex);
        }
    }

    public static <E extends BaseDO, Q extends Query> Result<E> queryOne(BaseMapper<E, Q> mapper, Q query) {
        if (query == null) {
            return Result.argsErrResult();
        }
        query.setCurPage(1);
        query.setPageSize(1);
        query.setWithCount(false);
        PageResult<E> rst = query(mapper, query);
        if (rst.isSuccess()) {
            return Result.successResult(rst.hasSuccessValue() ? rst.getResults().get(0) : null);
        } else {
            if (rst.isSuccess()) {
                return Result.successResult(null);
            }
            return Result.errResult(rst.getCode(), rst.getMsg());
        }
    }

    /**
     * 根据查询项查询记录数据
     */
    public static <E extends BaseDO, Q extends Query> PageResult<E> query(BaseMapper<E, Q> mapper, Q query) {
        if (query == null) {
            return PageResult.argErrResult();
        }
        if (query.getCurPage() == null) {
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null) {
            query.setPageSize(DEFAULT_PAGE_SIZE);
        }

        try {
            int limitOffset = query.getStart();
            int total = 0;
            if (limitOffset > MAX_OFFSET_LIMIT || (query.getWithCount() != null && query.getWithCount())) {
                total = mapper.count(query);
                int totalPage = (int) Math.ceil((double) total / query.getPageSize());
                query.setCurPage(totalPage < query.getCurPage() ? Integer.valueOf(totalPage) : query.getCurPage());
            }
            List<E> resultList = mapper.query(query);
            if (resultList == null) {
                resultList = new ArrayList<E>();
            }
            return PageResult.successResult(total, resultList);
        } catch (Exception ex) {
            log.error("query data '{}' failed: {}.", query.toString(), ex.getMessage(), ex);
            return PageResult.exceptionResult(ex);
        }
    }


    /**
     * 新增
     */
    public static <E extends BaseDO, Q extends Query> Result<E> insert(BaseMapper<E, Q> mapper, E entity) {
        if (entity == null) {
            return Result.argsErrResult();
        }
        try {
            Integer affectRow = mapper.insertSelective(entity);
            if (affectRow != null && affectRow > 0) {
                return Result.successResult(entity);
            }
        } catch (Exception ex) {
            log.error("insert data '{}' failed: {}.", entity.toString(), ex.getMessage(), ex);
            return Result.exceptionResult(ex);
        }
        return Result.sysErrResult();
    }


    /**
     * 更新
     */
    public static <E extends BaseDO, Q extends Query> Result<Boolean> update(BaseMapper<E, Q> mapper, E entity) {
        if (entity == null) {
            return Result.argsErrResult();
        }
        try {
            Integer result = mapper.updateByPrimaryKeySelective(entity);
            if (result != null && result > 0) {
                return Result.successResult(Boolean.TRUE);
            } else {
                return Result.sysErrResult();
            }
        } catch (Exception ex) {
            log.error("update data '{}' failed: {}.", entity.toString(), ex.getMessage(), ex);
            return Result.exceptionResult(ex);
        }
    }

    /**
     * 删除
     */
    public static <E extends BaseDO, Q extends Query> Result<Boolean> delete(BaseMapper<E, Q> mapper, Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult();
        }
        Result<E> rst = getById(mapper, id);
        if (!rst.hasSuccessValue()) {
            return Result.sysErrResult();
        }
        E e = rst.getResult();
        e.setDeletedAt(new Date());
        return update(mapper, e);
    }
}
