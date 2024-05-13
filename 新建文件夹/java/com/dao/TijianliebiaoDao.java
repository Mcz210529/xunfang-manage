package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TijianliebiaoEntity;
import com.entity.view.TijianliebiaoView;
import com.entity.vo.TijianliebiaoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 体检列表

 */
public interface TijianliebiaoDao extends BaseMapper<TijianliebiaoEntity> {
	
	List<TijianliebiaoVO> selectListVO(@Param("ew") Wrapper<TijianliebiaoEntity> wrapper);
	
	TijianliebiaoVO selectVO(@Param("ew") Wrapper<TijianliebiaoEntity> wrapper);
	
	List<TijianliebiaoView> selectListView(@Param("ew") Wrapper<TijianliebiaoEntity> wrapper);

	List<TijianliebiaoView> selectListView(Pagination page,@Param("ew") Wrapper<TijianliebiaoEntity> wrapper);
	
	TijianliebiaoView selectView(@Param("ew") Wrapper<TijianliebiaoEntity> wrapper);
	
}
