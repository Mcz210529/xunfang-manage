package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TaocanleimuEntity;
import com.entity.view.TaocanleimuView;
import com.entity.vo.TaocanleimuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 套餐类目

 */
public interface TaocanleimuDao extends BaseMapper<TaocanleimuEntity> {
	
	List<TaocanleimuVO> selectListVO(@Param("ew") Wrapper<TaocanleimuEntity> wrapper);
	
	TaocanleimuVO selectVO(@Param("ew") Wrapper<TaocanleimuEntity> wrapper);
	
	List<TaocanleimuView> selectListView(@Param("ew") Wrapper<TaocanleimuEntity> wrapper);

	List<TaocanleimuView> selectListView(Pagination page,@Param("ew") Wrapper<TaocanleimuEntity> wrapper);
	
	TaocanleimuView selectView(@Param("ew") Wrapper<TaocanleimuEntity> wrapper);
	
}
