package com.windf.plugins.database;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.windf.core.exception.EntityException;
import com.windf.core.util.Page;


public interface GeneralDao<T extends AbstractBean> {
    /**
     * 保存
     *
     * @param transientInstance
     * @return
     */
    public T save(T transientInstance);

    /**
     * 根据id列表 删除
     *
     * @param ids
     */
    public int deleteByIds(final List ids);

    /**
     * 根据id列表删除
     *
     * @param clazz �?��删除的表
     * @param ids
     * @return
     */
    public int deleteByIds(final Class clazz, final List ids);

    /**
     * 删除单个
     *
     * @param persistentInstance
     */
    public void delete(T persistentInstance);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    public T getById(String id);

    public T getById(Class clazz, String id);

    /**
     * 根据样例
     *
     * @param instance
     * @return
     */
    public List getByExample(final T instance);

    /**
     * 根据条件查找
     *
     * @param detachedCriteria
     * @return
     */
    public List getList(final DetachedCriteria detachedCriteria);

    /**
     * 根据条件分页获得
     */
    public Page getByPage(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

    /**
     * 批量更新�?��字段为某??
     *
     * @param ids
     * @param column
     * @param value
     * @return
     */
    public int updateColumnByIds(final List ids, final String column, final String value);

    /**
     * 使用sql查询的接�?
     *
     * @param sql
     * @return
     */
    public List getBySQL(final String sql);

    /**
     * 根据sql分页获得
     *
     * @param sql
     * @param pageSize
     * @param startIndex
     * @return
     */
    public Page getByPageSQL(String sql, int pageSize, int startIndex);

    /**
     * 根据sql分页获得
     *
     * @param countSql   用于统计记录总数的sql
     * @param sql
     * @param pageSize
     * @param startIndex
     * @return
     */
    public Page getByPageSQL(String countSql, String sql, int pageSize, int startIndex);

    public List getByHQL(final String hql);

    public List getByHQL(final String hql, final String[] str);

    public int executeByHQL(final String hql);

    public int executeBySQL(final String sql);

    //public EnumConst getEnumConstByNamespaceCode(String namespace, String code);

    public void setEntityClass(Class entityClass);

    public int deleteByIds(final Class clazz, final List siteIds, final List ids);

    public T getById(final Class entityClass, final List siteIds, final String id) throws EntityException;

    /**
     * 批量更新�?��字段为某??
     *
     * @param ids
     * @param column
     * @param value
     * @return
     */
    public int updateColumnByIds(final Class entityClass, final List ids, final String column, final String value);
}
