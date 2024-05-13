package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TijianleixingEntity;
import com.entity.view.TijianleixingView;
import com.entity.vo.TijianleixingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体检类型

 */
public interface TijianleixingDao extends BaseMapper<TijianleixingEntity> {
	
	List<TijianleixingVO> selectListVO(@Param("ew") Wrapper<TijianleixingEntity> wrapper);
	
	TijianleixingVO selectVO(@Param("ew") Wrapper<TijianleixingEntity> wrapper);
	
	List<TijianleixingView> selectListView(@Param("ew") Wrapper<TijianleixingEntity> wrapper);

	List<TijianleixingView> selectListView(Pagination page,@Param("ew") Wrapper<TijianleixingEntity> wrapper);
	
	TijianleixingView selectView(@Param("ew") Wrapper<TijianleixingEntity> wrapper);
	
}
